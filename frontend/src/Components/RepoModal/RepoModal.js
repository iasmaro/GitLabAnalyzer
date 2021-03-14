import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import { Redirect } from "react-router-dom";

import RepoModalConfig from './Components/RepoModalConfig';
import RepoModalMapAliasTable from './Components/RepoModalMapAliasTable';
import mapAliasToMember from 'Utils/mapAliasToMember';
import updateAliasForMembers from 'Utils/updateAliasForMembers';
import { allMembersHaveAliases, noMembersHaveAliases } from './Utils/checkInitialMemberAliasMapping';
import { createMappingContainingPastAliases } from './Utils/createMappingContainingPastAliases';
import { createInitialAliasIdPairs } from './Utils/createInitialAliasIdPairs';
import { sameAliasIdPairs } from './Utils/sameAliasIdPairs';

import 'Components/RepoModal/RepoModal.css';

const RepoModal = (props) => {
    const {name, id, members, aliases, databaseMapping, status, toggleModal} = props || {};
    const [config, setConfig] = useState("Select a configuration");
    const [aliasIdPairs, setAliasIdPairs] = useState(createInitialAliasIdPairs(aliases, members, databaseMapping)); 
    const databaseAliasIdPairs = createInitialAliasIdPairs(aliases, members, databaseMapping);

    console.log("DATA FROM API CALLS");
    console.log(aliases);
    console.log(members);
    console.log(databaseMapping);

    // TODO: SET THESE DATES BASED ON THE CONFIG FILE SELECTED
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [redirect, setRedirect] = useState(false);

    const createApiMappingFromLocalMapping = (mapping) => {
        for (var i = 0; i < aliasIdPairs.length; i ++) {
            var memberIndex = aliasIdPairs[i].memberIndex;
            if (memberIndex > -1) {
                mapping[memberIndex]?.alias?.push(aliasIdPairs[i].alias);
            }
        }
    }

    const handleClick = () => {

        if (config !== "Select a configuration") {

            const mapping = createMappingContainingPastAliases(aliases, databaseMapping);
            createApiMappingFromLocalMapping(mapping); 
            console.log('DATA IN MAP/POST REQUEST');
            console.log(mapping);
            if (noMembersHaveAliases(databaseMapping)) {
                console.log('no members have aliases');
                //mapAliasToMember(mapping);
            } else if (allMembersHaveAliases(databaseMapping)) {
                console.log('all members have aliases');
                if(!sameAliasIdPairs(aliasIdPairs, databaseAliasIdPairs)) {
                    console.log('submit put request');
                    //updateAliasForMembers(mapping);
                }
            } else {
                console.log('some members have aliases, some dont');
                if(!sameAliasIdPairs(aliasIdPairs, databaseAliasIdPairs)) {
                    //mapAliasToMember(mapping);
                }
            }  
            //setRedirect(true);
        }
    }

    if (redirect) {
        const data = {
            start: startDate.toISOString(),
            end: endDate.toISOString(),
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