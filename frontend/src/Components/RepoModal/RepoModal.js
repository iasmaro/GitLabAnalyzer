import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import { Redirect } from "react-router-dom";

import mapAliasToMember from 'Utils/mapAliasToMember';
import updateAliasForMembers from 'Utils/updateAliasForMembers';

import RepoModalConfig from './RepoModalConfig';
import RepoModalMapAliasTable from './RepoModalMapAliasTable/RepoModalMapAliasTable';
import { allMembersHaveAliases, noMembersHaveAliases } from './RepoModalMapAliasTable/Utils/checkInitialMemberAliasMapping';
import { createMappingContainingPastAliases } from './RepoModalMapAliasTable/Utils/createMappingContainingPastAliases';
import { createInitialAliasIdPairs } from './RepoModalMapAliasTable/Utils/createInitialAliasIdPairs';
import { sameAliasIdPairs } from './RepoModalMapAliasTable/Utils/sameAliasIdPairs';
import 'Components/RepoModal/RepoModal.css';

const RepoModal = (props) => {
    const { name, id, members, aliases, databaseMapping, status, toggleModal } = props || {};    
    const [config, setConfig] = useState("Select a configuration");
    const [aliasIdPairs, setAliasIdPairs] = useState(createInitialAliasIdPairs(aliases, members, databaseMapping)); 
    const databaseAliasIdPairs = createInitialAliasIdPairs(aliases, members, databaseMapping);
    const mapping = createMappingContainingPastAliases(aliases, members, databaseMapping);

    // TODO: SET THESE DATES BASED ON THE CONFIG FILE SELECTED
    const [redirect, setRedirect] = useState(false);

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
    }

    if (redirect) {
        const data = {
            projectId: id,
            configuration: config,
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
                <RepoModalConfig config={config} setConfig={setConfig} />

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