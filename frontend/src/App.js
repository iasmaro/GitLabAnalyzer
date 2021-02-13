import React from 'react';
import {Route} from 'react-router-dom';
import About from './Pages/About/About';
import Profile from './Pages/Profile/Profile';
import Navbar from './Components/Navbar/Navbar';
import Login from './Pages/Login/Login';


function App() {
  return (
    <div className="App">

      <Navbar />
      <Route exact path="/" component={Login}></Route>
      <Route exact path="/about" component={About}></Route>
      <Route exact path="/profile" component={Profile}></Route>
    </div>
  );
}

export default App;
