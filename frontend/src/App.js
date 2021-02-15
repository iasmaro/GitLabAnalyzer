import React from 'react';
import { Route, Switch, useHistory, useLocation } from 'react-router-dom';
import { parseString, processors } from 'react-native-xml2js';

import About from './Pages/About/About';
import Home from './Pages/Home/Home';
import Dashboard from './Pages/Dashboard/Dashboard';
import Navbar from './Components/Navbar/Navbar';

import { useUserState, useUserDispatch } from './UserContext';
import ProtectedRoute from './ProtectedRoute';
import ProtectedPage from './ProtectedPage';

const getData = async (ticket, dispatch) => {
  const response = await fetch(`https://cas.sfu.ca/cas/serviceValidate?service=http://localhost:3000/&ticket=${ticket}`);
  const data = await response.text();
  let authenticationSuccess;
  parseString(data, { tagNameProcessors: [processors.stripPrefix] }, function (err, result) {
      authenticationSuccess = result.serviceResponse?.authenticationSuccess;
  });
  if (authenticationSuccess && authenticationSuccess.length && authenticationSuccess[0].user && authenticationSuccess[0].user.length) {
    dispatch(authenticationSuccess[0].user[0]);
  }
}


function App() {
  const url = useLocation();
  const { search } = url || {};
  const ticket = search ? search.split('ticket=')[1] : null;
  const dispatch = useUserDispatch();
  if (ticket) getData(ticket, dispatch);
  const location = useLocation();
  const history = useHistory();
  const queryParams = new URLSearchParams(location.search);
  if (queryParams.has('ticket')) {
    queryParams.delete('ticket')
    history.replace({
      search: queryParams.toString(),
    })
  }
  return (
    <div className="App">
      <Navbar />
      <Switch>
        <ProtectedPage exact path="/" Component={Home} Other={Dashboard} />
        <Route exact path="/about" component={About} />
        <ProtectedRoute exact path="/dashboard" Component={Dashboard}/>
      </Switch>
    </div>
  );
}

export default App;
