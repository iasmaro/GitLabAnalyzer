import React from 'react';
import ReactPlayer from 'react-player/youtube';
import { Table } from 'react-bootstrap';
import { AiFillGithub, AiFillLinkedin } from 'react-icons/ai';

import './About.css';

function About() {

    return (
        <div className='about-page'>
            <div className='main-header'>
                <h1>We make marking CS projects faster and more pleasant.</h1>
                <p>
                    GitLab Analyzer is an analysis tool for assessing user contributions to a GitLab project.
                    Made to be used as a grading aid for instructors, the tool supports analysis for the work
                    of students between a specified start/end date.  
                </p>
            </div>
            <div className='column'>
                <p>
                    <h8>We are a diverse group of 8 CS students studying at Simon Fraser University and together we are team Haumea!</h8>
                </p>
                <Table striped hover className="team-table">
                    <thead>
                        <tr>
                            <th colSpan='4'><h3>The Team</h3></th>
                        </tr>
                    </thead>
                    <thead>
                        <tr>
                            <th>Members</th>
                            <th>Team</th>
                            <th>About me</th>
                            <th>Connect</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Andrew Ursu</td>
                            <td>Backend</td>
                            <td>Software systems student at SFU who enjoys working on real-world software applications</td>
                            <td className="profile-links">
                                <a href="https://www.linkedin.com/in/andrew-ursu-6424171b7/">
                                    <AiFillLinkedin />
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>Austin Wang</td>
                            <td>Backend</td>
                            <td>3rd year Computing Science student at SFU</td>
                            <td className="profile-links">
                                <a href="https://github.com/AustinjhWang">
                                    <AiFillGithub />
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>Doris Chang</td>
                            <td>Frontend</td>
                            <td>2nd year Computing Science Student at SFU</td>
                            <td className="profile-links">
                                <a href="https://www.linkedin.com/in/doris-chang/">
                                    <AiFillLinkedin />
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>Ivan Asmaro</td>
                            <td>Frontend</td>
                            <td>Computing Science student at SFU graduating in August 2021</td>
                            <td className="profile-links">
                                <a href=" https://github.com/iasmaro">
                                    <AiFillGithub />
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>Minh Bui</td>
                            <td>Backend</td>
                            <td>Computing Science student at SFU</td>
                            <td className="profile-links">
                                <a href="https://www.linkedin.com/in/minh--bui/">
                                    <AiFillLinkedin />
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>Neil Shah</td>
                            <td>Frontend</td>
                            <td>Computer Science and Statistics student at SFU who enjoys to work with data and artificial intelligence applications</td>
                            <td className="profile-links">
                                <a href="https://github.com/nmshah1609">
                                    <AiFillGithub />
                                </a>
                                <a href="www.linkedin.com/in/neilshah16">
                                    <AiFillLinkedin />
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>Sterling Tamboline</td>
                            <td>Frontend</td>
                            <td>Software Systems student at SFU with an expected graduation date of Summer 2022</td>
                            <td className="profile-links">
                                <a href="https://github.com/sterlst ">
                                    <AiFillGithub />
                                </a>
                                <a href="https://www.linkedin.com/in/sterling-tamboline-39507a192">
                                    <AiFillLinkedin />
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>Viet</td>
                            <td>Backend</td>
                            <td>3rd year Computing Science student at SFU, interested in coding and solving mathematical problems</td>
                            <td className="profile-links">
                                <a href="https://github.com/hvtruong">
                                    <AiFillGithub />
                                </a>
                            </td>
                        </tr>
                    </tbody>
                </Table>
            </div>
            <div className='column'>
                <h3>Get Started</h3>
                    <ul>
                        <li>Sign in with your SFU account</li>
                        <li>Add your GitLab token </li>
                        <li>Add an url to the GitLab server</li>
                    </ul>
                <h3>Current Features</h3>
                <div className='column'>
                    <ul>
                        <li>Limit analysis for a user between specified dates</li>
                        <li>Display list of all merge requests</li>
                        <li>Display list of commits within a specific merge request</li>
                        <li>Display list of all commits</li>
                        <li>View past Reports</li>
                        <li>Batch Process Reports</li>
                        <li>Share reports with other users</li>
                        <li>Veiw Graphs by Comments, Merge Requests, Issues</li>
                        
                       
                    </ul>
                </div>
                <div className='column'>
                    <ul>
                        <li>Create scoring configurations</li>
                        <li>Add multiple file types for configurations</li>
                        <li>Map Aliases</li>
                        <li>Veiw and edit score breakdowns</li>
                        <li>Copy total scores easily to any platform</li>
                        <li>Vew Code Changes with sytanx highlighting</li>
                        <li>Veiw comments on a particular Merge Request or an Issue</li>
                        <li>Easily acess GitLab MR or Commit with the links provided</li>
                        <li>Veiw score totals for MRs and commits and aslo number of words in a comment</li>
                    </ul>
                </div>
                <h8>We are not limited to SFU's GitLab server! We support any instance of GitLab or Github servers</h8>
            </div>
            <div className='main-header-demo'>
                <h3>Demo Video</h3>
                <ReactPlayer
                    url="https://www.youtube.com/watch?v=S7kQ9qxSALU"
                    width="1280px"
                    height="720px"
                />
            </div>
            <div className="empty"></div>
        </div>
    )
}

export default About;