import React from 'react';
import { Table, Card } from 'react-bootstrap';

import './codeDiffBreakdown.css';

const CodeDiffScoreBreakdown = (prop) => {
    const{ extension, linesMoved, addLine, syntaxLinesAdded, deleteLine, configInfo, spaceLinesAdded, meaningfulLinesAdded } = prop || {}

    const codeDiffTotalScore = (parseFloat((configInfo.fileFactor[extension] || 1).toFixed(1)) * 
        (parseFloat(((configInfo.editFactor.spaceChange || 0) * spaceLinesAdded).toFixed(1)) + 
        parseFloat(((configInfo.editFactor.addLine) * (meaningfulLinesAdded)).toFixed(1)) +
        parseFloat((configInfo.editFactor.deleteLine*deleteLine).toFixed(1)) + 
        parseFloat((configInfo.editFactor.moveLine*linesMoved).toFixed(1)) + 
        parseFloat((configInfo.editFactor.syntaxLine*syntaxLinesAdded).toFixed(1))))

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
                                <td>{configInfo.editFactor.addLine}</td>
                                <td>{configInfo.editFactor.deleteLine}</td>
                                <td>{configInfo.editFactor.moveLine}</td>
                                <td>{configInfo.editFactor.syntaxLine}</td>
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
                                <th>spaceLinesAdded</th>
                                <th>meaningfulLinesAdded</th>
                                <th>linesDeleted</th>
                                <th>linesMoved</th>
                                <th>syntaxLinesAdded</th>
                                
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{addLine}</td>
                                <td>{spaceLinesAdded}</td>
                                <td>{meaningfulLinesAdded}</td>
                                <td>{deleteLine}</td>
                                <td>{linesMoved}</td>
                                <td>{syntaxLinesAdded}</td>
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
                                <td className='part-calc'>{(configInfo.fileFactor[extension] || 1).toFixed(1)}</td>
                            </tr>
                            <tr>
                                <th>spaceLinesAdded</th>
                                <td>{configInfo.editFactor.spaceChange || 0} * {spaceLinesAdded}</td>
                                <td className='part-calc'>{((configInfo.editFactor.spaceChange || 0)*spaceLinesAdded).toFixed(1)}</td>

                            </tr>
                            <tr>
                                <th>meaningfulLinesAdded</th>
                                <td>{configInfo.editFactor.addLine} * {meaningfulLinesAdded}</td>
                                <td className='part-calc'>{((configInfo.editFactor.addLine)*(meaningfulLinesAdded)).toFixed(1)}</td>

                            </tr>
                            <tr>
                                <th>linesDeleted</th>
                                <td>{configInfo.editFactor.deleteLine} * {deleteLine}</td>
                                <td className='part-calc'>{(configInfo.editFactor.deleteLine*deleteLine).toFixed(1)}</td>
                            </tr>
                            <tr>
                                <th>linesMoved</th>
                                <td>{configInfo.editFactor.moveLine} * {linesMoved}</td>
                                <td className='part-calc'>{(configInfo.editFactor.moveLine*linesMoved).toFixed(1)}</td>
                            </tr>
                            <tr>
                                <th>syntaxLinesAdded</th>
                                <td>{configInfo.editFactor.syntaxLine} * {syntaxLinesAdded}</td>
                                <td className='part-calc'>{(configInfo.editFactor.syntaxLine*syntaxLinesAdded).toFixed(1)}</td>
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
                                    {(configInfo.fileFactor[extension] || 1).toFixed(1)} * (
                                    {((configInfo.editFactor.spaceChange || 0) * spaceLinesAdded).toFixed(1)}+ 
                                    {((configInfo.editFactor.addLine) * (meaningfulLinesAdded)).toFixed(1)}+ 
                                    {(configInfo.editFactor.deleteLine*deleteLine).toFixed(1)}+
                                    {(configInfo.editFactor.moveLine*linesMoved).toFixed(1)}+ 
                                    {(configInfo.editFactor.syntaxLine*syntaxLinesAdded).toFixed(1)})
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