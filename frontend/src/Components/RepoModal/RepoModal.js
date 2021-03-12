import React, { useState, useEffect  } from 'react';
import { Modal, Button } from 'react-bootstrap';
import { Redirect } from "react-router-dom";

import RepoModalConfig from './RepoModalConfig';
import RepoModalMatchAlias from './RepoModalMatchAlias';
import './RepoModal.css';
import mapAliasToMember from 'Utils/mapAliasToMember';

const RepoModal = (props) => {

    const {name, id, members, aliases, status, toggleModal} = props || {};
    const [isLoading, setIsLoading] = useState(false);
    const [config, setConfig] = useState("Select a configuration");
    const [student, setStudent] = useState("Select a student");
    const [mappedAliases, setMappedAliases] = useState(aliases.map((alias) => ({alias:alias, memberIndex: -1}))); 

    // Temporary for testing:
    // const {name, id, status, toggleModal} = props || {};
    // const aliases = ['Batman', 'Superman', 'Catwoman', 'Hulk', 'Ironman', 'Aquaman' ];
    // const members = ['brucewayne', 'tonystark', 'clarkkent', 'selinakyle', 'brucebanner'];

    // TODO: SET THESE DATES BASED ON THE CONFIG FILE SELECTED
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [redirect, setRedirect] = useState(false);

    const createApiMappingFromLocalMapping = (mapping) => {
        for (var i = 0; i < mappedAliases.length; i ++) {
            var memberIndex = mappedAliases[i].memberIndex;
            if (memberIndex > -1) {
                mapping[memberIndex]?.alias?.push(mappedAliases[i].alias);
            }
        }
        for (var i = mapping.length - 1; i >= 0; --i) {
            if (!mapping[i].alias.length) {
                mapping.splice(i, 1);
            }
        }
    }

    const handleClick = () => {
        if (config !== "Select a configuration") {
            const mapping = members.map((member) => ({alias:[], memberId:member}));
            createApiMappingFromLocalMapping(mapping);   
            mapAliasToMember(mapping);
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
            size="xl"
            className="custom-modal"
            scrollable={true}
        >
            <Modal.Header closeButton>
                <Modal.Title>{name}</Modal.Title>
            </Modal.Header>

            <Modal.Body className="repo-modal-body">
                <RepoModalConfig config={config} setConfig={setConfig} />

                <RepoModalMatchAlias aliases={aliases} memberIds={members} mappedAliases={mappedAliases} setMappedAliases={setMappedAliases}/>
            </Modal.Body>

            <Modal.Footer>
                <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                <Button variant="success" onClick={handleClick}>Analyze</Button>
            </Modal.Footer>

        </Modal>
    );
};

export default RepoModal;