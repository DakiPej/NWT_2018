import React, { Component } from 'react';
import { Row, Col } from 'react-materialize';
import axios from 'axios';
import SinglePost from './SinglePost';
import '../../styles/feed.css';


class Feed extends Component{

    state = {
        posts : []
    };

    componentDidMount(){
        this.getPosts();
    }

    getPosts = () => {
        if(sessionStorage.getItem("username")){
            axios.get("http://138.68.186.248:8080/postmodule/posts",
            {headers:{Authorization : "Beared " + sessionStorage.getItem("token")}}
        )
        .then(this.handlePosts)
        .catch(this.handleError);
        }
        else{
        axios.get("http://138.68.186.248:8080/postmodule/posts")
        .then(this.handlePosts)
        .catch(this.handleError);
    }
    }

    handlePosts = (response) => {
        console.log(response.data);
        this.setState({
            posts:response.data
        })

    }

    handleError = (error) => {
        console.log(error);
        var posts = [
            {
                id:1,
                user:{username:'cool.username'},
                date:'11/11/11',
                imageURL:'https://www.w3schools.com/w3css/img_lights.jpg',
                upVote:110,
                downVote:10
            },
            {
                id:2,
                user:{username:'cool.username'},
                date:'11/11/11',
                imageURL:'https://www.w3schools.com/w3css/img_lights.jpg',
                upVote:110,
                downVote:10
            },
            {
                id:3,
                user:{username:'cool.username'},
                date:'11/11/11',
                imageURL:'https://www.w3schools.com/w3css/img_lights.jpg',
                upVote:110,
                downVote:10
            },
            {
                id:4,
                user:{username:'cool.username'},
                date:'11/11/11',
                imageURL:'https://www.w3schools.com/w3css/img_lights.jpg',
                upVote:110,
                downVote:10
            }
        ];
        this.setState({posts});
    }

    render(){
        
        var posts = this.state.posts.map((post) => (<SinglePost key={post.id} post={post}/>));
        
        return(
            <Row>
                <Col m={2} l={2} />
                <Col m={8} l={8}>
                <div className="feed-container">
                    {posts}
                </div>
                </Col>
                <Col m={2} l={2} />
            </Row>
        );
    }

}

export default Feed;