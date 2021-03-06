import React from 'react';
import { Route, Switch, useHistory, useLocation } from 'react-router-dom';

import Home from 'Pages/Home/Home';
import Configuration from 'Pages/Configuration/Configuration';
import Profile from 'Pages/Profile/Profile';
import LogoutPage from 'Pages/Logout/LogoutPage';
import Analysis from 'Pages/Analysis/Analysis';
import PastReports from 'Pages/PastReports/PastReports';
import Navbar from 'Components/Navbar/Navbar';
import getUserData from 'Utils/getUserData';

import { useUserDispatch } from './UserContext';
import ProtectedRoute from './ProtectedRoute';



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
      <Switch>
        <Route exact path="/" component={Home} />
        <Route exact path="/Analysis" component={Analysis} />
        <ProtectedRoute exact path="/Configurations" Component={Configuration} />
        <ProtectedRoute exact path="/Reports" Component={PastReports} />
        <ProtectedRoute exact path="/Profile" Component={Profile} />
        <LogoutPage exact path="/Logout" />
      </Switch>
    </div>
  );
}

export default App;
