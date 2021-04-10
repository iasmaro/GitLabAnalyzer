import React from 'react';
import { Table, Card } from 'react-bootstrap';

import './codeDiffBreakdown.css';

const CodeDiffScoreBreakdown = (prop) => {
    const { 
        extension, 
        linesMoved, 
        addLine, 
        syntaxLinesAdded, 
        deleteLine, 
        configInfo, 
        spaceLinesAdded, 
        meaningfulLinesAdded, 
        commentsLinesAdded,
        linesRemoved } = prop || {};

    if (!configInfo || !configInfo.fileFactor || !configInfo.editFactor) {
        return null;
    };

    const FileExtension = (configInfo.fileFactor[extension] || 1).toFixed(1);
    const SpaceChange = ((configInfo.editFactor.spaceChange || 0) *(spaceLinesAdded)).toFixed(1);
    const MeaningfulLine = ((configInfo.editFactor.addLine || 1)*(meaningfulLinesAdded)).toFixed(1);
    const DeleteLine = ((configInfo.editFactor.deleteLine || 0.2)*(deleteLine)).toFixed(1);
    const MoveLine = ((configInfo.editFactor.moveLine || 0.5)*(linesMoved)).toFixed(1);
    const SyntaxLine = ((configInfo.editFactor.syntaxLine || 0.2)*(syntaxLinesAdded)).toFixed(1);
    
    const codeDiffTotalScore = (
        parseFloat(FileExtension) * 
        (parseFloat(SpaceChange) + 
        parseFloat(MeaningfulLine) +
        parseFloat(DeleteLine) + 
        parseFloat(MoveLine) + 
        parseFloat(SyntaxLine)));

    return(
        <>
            <Card className="score-weight">
                <Card.Header>Score Weights</Card.Header>
                <Card.Body>
                    <Table className='table-breakdown'>
                        <thead>
                            <tr>
                                <th>{extension}</th>
                                <th>addLine</th>
                                <th>deleteLine</th>
                                <th>moveLine</th>
                                <th>syntaxLine</th>
                                <th>spaceLine</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{configInfo.fileFactor[extension] || 1}</td>
                                <td>{configInfo.editFactor.addLine || 1}</td>
                                <td>{configInfo.editFactor.deleteLine || 0.2}</td>
                                <td>{configInfo.editFactor.moveLine || 0.5}</td>
                                <td>{configInfo.editFactor.syntaxLine || 0.2}</td>
                                <td>{configInfo.editFactor.spaceChange || 0}</td>
                            </tr>
                        </tbody>
                    </Table>
                </Card.Body>
            </Card>
            <Card className="code-diff-summary">
                <Card.Header>Code Diff Summary</Card.Header>
                <Card.Body>
                    <Table className='table-breakdown'>
                        <thead>
                            <tr>
                                <th>totalLinesAdded</th>
                                <th>totalLinesRemoved</th>
                                <th>meaningfulLinesAdded</th>
                                <th>meaningfulLinesRemoved</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{addLine}</td>
                                <td>{linesRemoved}</td>
                                <td>{meaningfulLinesAdded}</td>
                                <td>{deleteLine}</td>
                            </tr>
                        </tbody>
                        <thead>
                            <tr>
                                <th>linesMoved</th>
                                <th>syntaxLinesAdded</th>
                                <th>commentsLinesAdded</th>
                                <th>spaceLinesAdded</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{linesMoved}</td>
                                <td>{syntaxLinesAdded}</td>
                                <td>{commentsLinesAdded}</td>
                                <td>{spaceLinesAdded}</td>
                            </tr>
                        </tbody>
                    </Table>
                </Card.Body>
            </Card>
            <Card className="code-diff-calculation">
                <Card.Header>Score Calculations</Card.Header>
                <Card.Body>
                    <Table className='table-breakdown'>
                        <tbody>
                            <tr>
                                <th>fileType: {extension}</th>
                                <td>{configInfo.fileFactor[extension] || 1}</td>
                                <td className='part-calc'>{FileExtension}</td>
                            </tr>
                            <tr>
                                <th>meaningfulLinesAdded</th>
                                <td>{configInfo.editFactor.addLine || 1} * {meaningfulLinesAdded}</td>
                                <td className='part-calc'>{MeaningfulLine}</td>

                            </tr>
                            <tr>
                                <th>meaningfulLinesRemoved</th>
                                <td>{configInfo.editFactor.deleteLine || 0.2} * {deleteLine}</td>
                                <td className='part-calc'>{DeleteLine}</td>
                            </tr>
                            <tr>
                                <th>linesMoved</th>
                                <td>{configInfo.editFactor.moveLine || 0.5} * {linesMoved}</td>
                                <td className='part-calc'>{MoveLine}</td>
                            </tr>
                            <tr>
                                <th>syntaxLinesAdded</th>
                                <td>{configInfo.editFactor.syntaxLine || 0.2} * {syntaxLinesAdded}</td>
                                <td className='part-calc'>{SyntaxLine}</td>
                            </tr>
                            <tr>
                                <th>commentsLinesAdded</th>
                                <td>0 * {commentsLinesAdded}</td>
                                <td className='part-calc'>0.0</td>
                            </tr>
                            <tr>
                                <th>spaceLinesAdded</th>
                                <td>{configInfo.editFactor.spaceChange || 0} * {spaceLinesAdded}</td>
                                <td className='part-calc'>{SpaceChange}</td>
                            </tr>
                        </tbody>
                    </Table>
                </Card.Body>
            </Card>
            <Card className="code-diff-calculation-total">
                <Card.Header>Total Code Diff Score</Card.Header>
                <Card.Body>
                    <Table className='table-breakdown'>
                        <tbody>
                            <tr>
                                <th>Total: </th>
                                <td>
                                    {FileExtension} * ({MeaningfulLine} + {DeleteLine} + {MoveLine} + {SyntaxLine} + {SpaceChange} + 0.0)
                                </td>
                                <td className='part-calc'>
                                    {codeDiffTotalScore}
                                </td>
                            </tr>
                        </tbody>
                    </Table>
                </Card.Body>
            </Card>
        </>
    );
};

export default CodeDiffScoreBreakdown