import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

import Header from './components/header/Header';
import Landing from './components/Landing';
import SinglePost from './components/post/SinglePost';
import Register from './components/Register';
import ProfileDetails from './components/ProfileDetails';

class App extends Component {
  render() {
    return (
      <div>
      <Header />
      <ProfileDetails />
      </div>
    );
  }
}

export default App;
