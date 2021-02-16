import React from 'react';
import { Route, Switch, useHistory, useLocation } from 'react-router-dom';

import About from 'Pages/About/About';
import Home from 'Pages/Home/Home';
import Profile from 'Pages/Profile/Profile';
import Navbar from 'Components/Navbar/Navbar';
import getUserData from 'Utils/getUserData';

import { useUserDispatch } from './UserContext';
import ProtectedRoute from './ProtectedRoute';
import LogoutPage from './LogoutPage';
import AnalyzerTabs from 'Components/AnalyzerTabs/AnalyzerTabs';


function App() {
  const url = useLocation();
  const history = useHistory();
  const dispatch = useUserDispatch();
  
  const { search } = url || {};
  let ticket;
  if (search) {
    ticket = search.split('ticket=')[1];
    getUserData(ticket, dispatch);
    history.replace({
      search: '',
    })
  }


  return (
    <div className="App">
      <Navbar />
      <AnalyzerTabs />
      <Switch>
        <Route exact path="/" component={Home} />
        <Route exact path="/about" component={About} />
        <ProtectedRoute exact path="/Configurations" Component={Home} />
        <ProtectedRoute exact path="/Reports" Component={Home} />
        <ProtectedRoute exact path="/Profile" Component={Profile} />
        <LogoutPage exact path="/Logout" />
      </Switch>
    </div>
  );
}

export default App;
