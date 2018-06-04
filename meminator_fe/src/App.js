import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import {Route, Switch} from 'react-router-dom';

import Header from './components/header/Header';
import Landing from './components/Landing';
import SinglePost from './components/post/SinglePost';
import Register from './components/Register';
import ProfileDetails from './components/ProfileDetails';
import Feed from './components/post/Feed';

class App extends Component {
  render() {
    return (
      <div>
      <Header />
      <Switch>
        <Route exact path="/" component={Landing}/>
        <Route exact path="/login" component={Landing}/>
        <Route exact path="/register" component={Register}/>
        <Route exact path="/feed" component={Feed}/>
      </Switch>
      </div>
    );
  }
}

export default App;
