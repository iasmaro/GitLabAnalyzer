import React from 'react';
import './App.css';
import Home from './Pages/Home/Home';
import About from './Pages/About/About';
import Profile from './Pages/Profile/Profile';
import Navbar from './Components/Navbar/Navbar';
import {Route} from 'react-router-dom';

function App() {
  return (
    <div className="App">
      
      <Navbar />
      <Route exact path="/" component={Home}></Route>
      <Route exact path="/about" component={About}></Route>
      <Route exact path="/profile" component={Profile}></Route>
    </div>
  );
}

export default App;
