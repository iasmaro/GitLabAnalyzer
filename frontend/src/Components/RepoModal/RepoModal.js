import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import { Redirect } from "react-router-dom";

import mapAliasToMember from 'Utils/mapAliasToMember';
import updateAliasForMembers from 'Utils/updateAliasForMembers';
import updateUser from 'Utils/updateUser';
import { defaultConfig } from 'Mocks/mockConfigs.js';
import { useUserState } from 'UserContext';
import { modal } from 'Constants/constants';
import 'Components/RepoModal/RepoModal.css';

import RepoModalConfig from './RepoModalConfig';
import RepoModalMapAliasTable from './RepoModalMapAliasTable/RepoModalMapAliasTable';
import { allMembersHaveAliases, noMembersHaveAliases } from './RepoModalMapAliasTable/Utils/checkInitialMemberAliasMapping';
import { createMappingContainingPastAliases } from './RepoModalMapAliasTable/Utils/createMappingContainingPastAliases';
import { createInitialAliasIdPairs } from './RepoModalMapAliasTable/Utils/createInitialAliasIdPairs';
import { sameAliasIdPairs } from './RepoModalMapAliasTable/Utils/sameAliasIdPairs';
import RepoModalDateTimePicker from './RepoModalDateTimePicker';

const RepoModal = (props) => {
    const { name, id, members, aliases, configs, databaseMapping, status, toggleModal, start, end } = props || {};    
    const [config, setConfig] = useState("Select a configuration");
    const [aliasIdPairs, setAliasIdPairs] = useState(createInitialAliasIdPairs(aliases, members, databaseMapping)); 
    const databaseAliasIdPairs = createInitialAliasIdPairs(aliases, members, databaseMapping);
    const mapping = createMappingContainingPastAliases(aliases, members, databaseMapping);
    const [redirect, setRedirect] = useState(false);
    const [showError, setShowError] = useState(false);
    const [startDate, setStartDate] = useState(start);
    const [endDate, setEndDate] = useState(end);
    const username = useUserState();

    const createApiMappingFromLocalMapping = (mapping) => {
        for (let aliasIdPair of aliasIdPairs) {
            const memberIndex = aliasIdPair.memberIndex;
            if (memberIndex > -1) {
                mapping[memberIndex].alias.push(aliasIdPair.alias);
            }
        }
    }

    const handleClick = () => {
        if (config !== "Select a configuration") {
            setShowError(false);
            createApiMappingFromLocalMapping(mapping); 
            if (noMembersHaveAliases(databaseMapping)) {
                mapAliasToMember(mapping);
            } else if (allMembersHaveAliases(databaseMapping)) {
                if(!sameAliasIdPairs(aliasIdPairs, databaseAliasIdPairs)) {          
                    updateAliasForMembers(mapping);
                }
            } else {             
                updateAliasForMembers(mapping);                
            }  
            setRedirect(true);
        }
        else {
            setShowError(true)
        }
    }

    if (redirect) {
        const data = {
            configuration: config,
            projectId: id,
        }
        if (startDate !== start || endDate !== end) {
            updateUser(username, '', '', startDate, endDate);
        }
        return(<Redirect to={{pathname: '/Analysis', state: { data }}} />);
    }

    return (
        <Modal
            show={status}
            onHide={toggleModal}
            backdrop="static"
            keyboard={false}
            dialogClassName="custom-modal"
            scrollable={true}
        >
            <Modal.Header closeButton>
                <Modal.Title>{name}</Modal.Title>
            </Modal.Header>

            <Modal.Body className="repo-modal-body">
                <RepoModalConfig defaultConfig={defaultConfig.fileName} configs={configs} config={config} setConfig={setConfig} setShowError={setShowError} />
                {showError && <p className='error-message'>Please select a configuration</p>}
                <RepoModalDateTimePicker 
                    startName={modal.START_DATE} 
                    endName={modal.END_DATE} 
                    startDate={startDate}
                    endDate={endDate}
                    setStartDate={setStartDate} 
                    setEndDate={setEndDate}
                />
                <RepoModalMapAliasTable aliases={aliases} members={members} aliasIdPairs={aliasIdPairs} setAliasIdPairs={setAliasIdPairs}/>
            </Modal.Body>

            <Modal.Footer>
                <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                <Button variant="success" onClick={handleClick}>Analyze</Button>
            </Modal.Footer>

        </Modal>
    );
};

export default RepoModal;