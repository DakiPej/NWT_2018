import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

import Header from './components/header/Header';
import Landing from './components/Landing';
import SinglePost from './components/post/SinglePost';
import Feed from './components/post/Feed';

class App extends Component {
  render() {
    return (
      <div>
      <Header />
      <Feed />
      </div>
    );
  }
}

export default App;