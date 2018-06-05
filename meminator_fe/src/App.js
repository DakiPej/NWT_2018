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
import SinglePostDetails from './containers/SinglePostDetails';
import PostForm from './components/post/PostForm';
import Comments from './components/post/Comments';

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
        <Route exact path="/profile" component={ProfileDetails} />
        <Route path="/profile/:username" component={ProfileDetails} />
        <Route exact path="/comments" component={Comments} />
        <Route path="/feed/:tag" component={Feed} />

      </Switch>
      {sessionStorage.getItem("username") && <PostForm />}
      </div>
    );
  }
}

export default App;
