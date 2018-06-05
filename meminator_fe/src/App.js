import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import {Route, Switch} from 'react-router-dom';

import Header from './components/header/Header';
import Landing from './components/Landing';
import SinglePost from './components/post/SinglePost';
import Register from './components/Register';
import Profile from './containers/Profile';
import PostGrid from './components/profile/PostGrid';
import Feed from './components/post/Feed';
import SinglePostDetails from './containers/SinglePostDetails';
import PostForm from './components/post/PostForm';

class App extends Component {
  render() {
    return (
      <div>
      <Header />
      <Switch>
        <Route exact path="/" component={Landing}/>
        <Route exact path="/login" component={Landing}/>
        <Route exact path="/register" component={Register}/>
        <Route exact path="/feed" component={Feed} />
        <Route path="/post/:id" component={SinglePostDetails} />
        <Route path="/profile/:username" component={Profile} />
        <Route path="/feed/:tag" component={Feed} />

      </Switch>
      {sessionStorage.getItem("username") && <PostForm />}
      </div>
    );
  }
}

export default App;
