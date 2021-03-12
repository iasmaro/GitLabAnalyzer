import React, { useState, useEffect  } from 'react';
import { Modal, Button } from 'react-bootstrap';
import { Redirect } from "react-router-dom";
import Spinner from 'react-bootstrap/Spinner';

import RepoModalStudent from './RepoModalStudent';
import RepoModalConfig from './RepoModalConfig';
import RepoModalMatchAlias from './RepoModalMatchAlias';
import getMembersAndAliases from 'Utils/getMembersAndAliases';
import { useUserState } from 'UserContext';
import './RepoModal.css';
import mapAliasToMember from 'Utils/mapAliasToMember';

const RepoModal = (props) => {

    const {name, id, status, toggleModal} = props || {};
    const [isLoading, setIsLoading] = useState(true);
    const [config, setConfig] = useState("Select a configuration");
    const [student, setStudent] = useState("Select a student");
    const [members, setMembers] = useState([]);
    const [aliases, setAliases] = useState([]);
    const username = useUserState();
    const [mappedAliases, setMappedAliases] = useState([]);

    // Temporary for testing:
    // const {name, id, status, toggleModal} = props || {};
    // const aliases = ['Batman', 'Superman', 'Catwoman', 'Hulk', 'Ironman', 'Aquaman' ];
    // const members = ['brucewayne', 'tonystark', 'clarkkent', 'selinakyle', 'brucebanner'];

    /*Default times are both at the current date and time*/
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [redirect, setRedirect] = useState(false);

    useEffect(() => {
        getMembersAndAliases(username, id).then((data) => {
            setMembers(data.members);
            setAliases(data.aliases);
            setIsLoading(false);
        });
    }, []);

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
            console.log(mappedAliases);
            var mapping = members.map((member) => ({alias:[], memberId:member}));
            createApiMappingFromLocalMapping(mapping);        
            console.log(mapping);
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

                {isLoading ? <span><Spinner animation="border" className="spinner"/></span> 
                : <RepoModalMatchAlias aliases={aliases} memberIds={members} mappedAliases={aliases.map((alias) => ({alias:alias, memberIndex: -1}))} setMappedAliases={setMappedAliases}/>}
            </Modal.Body>

            <Modal.Footer>
                <Button onClick={toggleModal} variant="secondary">Cancel</Button>
                <Button variant="success" onClick={handleClick}>Analyze</Button>
            </Modal.Footer>

        </Modal>
    );
};

export default RepoModal;