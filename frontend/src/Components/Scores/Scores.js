import Button from 'react-bootstrap/Button';

import copyToClipBoard from './utils/copyToClipboard';
import './Scores.css';

const Scores = (props) => {
    const { commitsScore, mrsScore, totalCommits, totalMRs } = props || {};

    const handleClick = () => {
        const message = `Total Commits:\t${totalCommits}\n` +
        `Total Merge Requests:\t${totalMRs}\n` +
        `Total Commits Score:\t${commitsScore}\n` +
        `Total Merge Requests Score:\t${mrsScore}`;

        copyToClipBoard(message);
    };

    return (
        <>
            <div className="student-scores">
                <div>
                    <div className="commits-total">
                        <span>Total Commits: </span>
                        <span>{totalCommits}</span>
                    </div>
                    <div className="mrs-total">
                        <span>Total Merge Requests: </span>
                        <span>{totalMRs}</span>
                    </div>
                </div>
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
            <Button variant="success" onClick={handleClick} className="clip-board-button">Copy to Clipboard</Button>
        </>
    );
}

export default Scores;