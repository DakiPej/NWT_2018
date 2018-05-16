import React, { Component } from 'react';
import { Row, Col } from 'react-materialize';
import axios from 'axios';
import SinglePost from './SinglePost';

class Feed extends Component{

    state = {
        posts : []
    };

    componentDidMount(){
        this.getPosts();
    }

    getPosts = () => {
        axios.get("http://localhost:8082/posts")
        .then(this.handlePosts.bind(this))
        .catch(function(err){console.log(err)});
    }

    handlePosts = (response) => {
        console.log(response.data);
        this.setState({
            posts:response.data
        })

    }

    render(){
        
        var posts = this.state.posts.map((post) => (<SinglePost key={post.id} post={post}/>));
        
        return(
            <Row>
                <Col m={4} l={4} />
                <Col m={4} l={4}>
                    {posts}
                </Col>
                <Col m={4} l={4} />
            </Row>
        );
    }

}

export default Feed;