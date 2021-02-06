import React from 'react';
import './App.css';
import Home from './Home';
import About from './About';
import Navbar from './Components/Navbar/Navbar';
import {Route} from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <Navbar />
      <Route exact path="/" component={Home}></Route>
      <Route exact path="/about" component={About}></Route>
    </div>
  );
}

export default App;
