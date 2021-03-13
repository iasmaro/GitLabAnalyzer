import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';
import { Redirect } from "react-router-dom";

import RepoModalConfig from './Components/RepoModalConfig';
import RepoModalMatchAlias from './Components/RepoModalMatchAlias';
import mapAliasToMember from 'Utils/mapAliasToMember';
import { createInitialAliasIdPairs } from './Utils/createInitialAliasIdPairs';
import { sameAliasIdPairs } from './Utils/sameAliasIdPairs';

import 'Components/RepoModal/RepoModal.css';

const RepoModal = (props) => {
    const {name, id, members, aliases, databaseMapping, status, toggleModal} = props || {};
    const [config, setConfig] = useState("Select a configuration");
    const [aliasIdPairs, setAliasIdPairs] = useState(createInitialAliasIdPairs(aliases, members, databaseMapping)); 
    const databaseAliasIdPairs = createInitialAliasIdPairs(aliases, members, databaseMapping);

    // MOCK VALUES:
    // members = ['anne', 'billy', 'chris', 'dan', 'emily', 'fred'];
    // aliases = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'];
    // databaseMapping = [ {alias:['a', 'm'], memberId:'anne'}, {alias:['f', 'l'], memberId:'fred'} ];
    // AliasIdPairs = [{alias:'a', memberIndex:0}, 
    //                     {alias:'b', memberIndex:-1}, 
    //                     {alias:'c', memberIndex:-1}, 
    //                     {alias:'d', memberIndex:-1}, 
    //                     {alias:'e', memberIndex:-1}, 
    //                     {alias:'f', memberIndex:5}, 
    //                     {alias:'g', memberIndex:-1},
    //                     {alias:'h', memberIndex:-1}, 
    //                     {alias:'i', memberIndex:-1},
    //                     {alias:'j', memberIndex:-1},
    //                     {alias:'k', memberIndex:-1},
    //                     {alias:'l', memberIndex:5},
    //                     {alias:'m', memberIndex:0}];

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
        // -- FOR TESTING MOCK DATA
        console.log('aliasIdPairs === databaseAliasIdPairs :' + sameAliasIdPairs(aliasIdPairs, databaseAliasIdPairs));
        const mapping = members.map((member) => ({alias:[], memberId:member}));
        createApiMappingFromLocalMapping(mapping);
        console.log(mapping);
        // -----

        if (config !== "Select a configuration") {
            const mapping = members.map((member) => ({alias:[], memberId:member}));
            if (databaseMapping?.length === 0) {
                mapAliasToMember(mapping);
            } else if (databaseMapping?.length === members?.length) {
                if(!sameAliasIdPairs(aliasIdPairs, databaseAliasIdPairs)) {
                    // INSERT PUT REQUEST HERE
                }
            } else {
                if(!sameAliasIdPairs(aliasIdPairs, databaseAliasIdPairs)) {
                    mapAliasToMember(mapping);
                }
            }
            createApiMappingFromLocalMapping(mapping);   
            setRedirect(true);
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

                <RepoModalMatchAlias aliases={aliases} members={members} aliasIdPairs={aliasIdPairs} setAliasIdPairs={setAliasIdPairs}/>
            </Modal.Body>

            <Modal.Footer>
                <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                <Button variant="success" onClick={handleClick}>Analyze</Button>
            </Modal.Footer>

        </Modal>
    );
};

export default RepoModal;