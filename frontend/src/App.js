import React from 'react';
import Home from './Pages/Home/Home';
import About from './Pages/About/About';
import Navbar from './Components/Navbar/Navbar';
import {Route} from 'react-router-dom';
import Login from './Pages/Login/Login';



function App() {
  return (
    <div className="App">
     
      <Navbar />
      <Login />
      <Route exact path="/" component={Home}></Route>
      <Route exact path="/about" component={About}></Route>
      
    </div>
  );
}

export default App;
