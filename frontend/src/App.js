import React from 'react';
import {Route} from 'react-router-dom';
import Login from './Pages/Login/Login';
import About from './Pages/About/About';
import Profile from './Pages/Profile/Profile';
import Dashboard from './Pages/Dashboard/Dashboard';
import Navbar from './Components/Navbar/Navbar';

function App() {
  return (
    <div className="App">

      <Navbar />
      <Route exact path="/" component={Login}></Route>
      <Route exact path="/about" component={About}></Route>
      <Route exact path="/profile" component={Profile}></Route>
      <Route exact path="/dashboard" component={Dashboard}/>

    </div>
  );
}

export default App;
