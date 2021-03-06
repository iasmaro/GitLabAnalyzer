import Button from 'react-bootstrap/Button';
import { IoMdCopy } from 'react-icons/io';

import copyToClipBoard from './utils/copyToClipboard';
import './Scores.css';

const Scores = (props) => {
    const { commitsScore, mrsScore } = props || {};

    const handleClick = () => {
        const message = `Total Commits Score:\t${commitsScore}\n` +
        `Total Merge Requests Score:\t${mrsScore}`;

        copyToClipBoard(message);
    };

    return (
        <>
            <div className="student-scores">
                <div>
                    <div className="commits-score">
                        <span>Total Commits Score: </span>
                        <span>{commitsScore}</span>
                    </div>
                    <div className="mrs-score">
                        <span>Total Merge Requests Score: </span>
                        <span>{mrsScore}</span>
                    </div>
                </div>
            </div>
            <Button onClick={handleClick} className="clip-board-button"><IoMdCopy /></Button>
        </>
    );
}

export default Scores;

