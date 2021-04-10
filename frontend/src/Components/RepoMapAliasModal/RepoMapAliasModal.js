import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';

import mapAliasToMember from 'Utils/mapAliasToMember';
import updateAliasForMembers from 'Utils/updateAliasForMembers';

import MapAliasTable from './MapAliasTable/MapAliasTable';
import { allMembersHaveAliases, noMembersHaveAliases } from './MapAliasTable/Utils/checkInitialMemberAliasMapping';
import { createMappingContainingPastAliases } from './MapAliasTable/Utils/createMappingContainingPastAliases';
import { createInitialAliasIdPairs } from './MapAliasTable/Utils/createInitialAliasIdPairs';
import { sameAliasIdPairs } from './MapAliasTable/Utils/sameAliasIdPairs';
import './RepoMapAliasModal.css';

const RepoMapAliasModal = (props) => {
    const { name, members, aliases, databaseMapping, status, toggleModal } = props || {};    
    const [aliasIdPairs, setAliasIdPairs] = useState(createInitialAliasIdPairs(aliases, members, databaseMapping)); 
    const databaseAliasIdPairs = createInitialAliasIdPairs(aliases, members, databaseMapping);
    const mapping = createMappingContainingPastAliases(aliases, members, databaseMapping);

    const createApiMappingFromLocalMapping = (mapping) => {
        for (let aliasIdPair of aliasIdPairs) {
            const memberIndex = aliasIdPair.memberIndex;
            if (memberIndex > -1) {
                mapping[memberIndex].alias.push(aliasIdPair.alias);
            }
        }
    }

    const handleClick = () => {
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
        toggleModal();
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
                <MapAliasTable aliases={aliases} members={members} aliasIdPairs={aliasIdPairs} setAliasIdPairs={setAliasIdPairs}/>
            </Modal.Body>

            <Modal.Footer>
                <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                <Button variant="success" onClick={handleClick}>Save</Button>
            </Modal.Footer>

        </Modal>
    );
};

export default RepoMapAliasModal;