import React, { Component } from 'react';
import { Card, CardTitle, Row, Col, Icon } from 'react-materialize';
import '../../styles/singlepost.css';

class SinglePost extends Component{

   state = {
            vote:{
                up:false,
                down:false
            }
        };

    onVoteUp=()=>{
        const vote = {
            up:!this.state.vote.up,
            down:false
        };

        this.setState({
            vote
        });
    }

    onVoteDown = () =>{
        const vote = {
            up:false,
            down:!this.state.vote.down
        };
        
        this.setState({
            vote
        });
    }

    render(){
        return(
            <Row>
                <Col m={4} l={4} />
                <Col m={4} l={4}>
                    <div className="post">
                    <div className="post-header">
                        <div className="username"><Icon>person_outline</Icon>cool.username</div>
                        <div className="date">11/11/11</div>
                    </div>
                    <img className="post-image" src="http://i3.kym-cdn.com/photos/images/newsfeed/000/170/791/welcome-to-the-internet-internet-demotivational-poster-1264714433.png.jpg"/>
                    <div className="post-footer">
                        <div className="votes">
                            <span id="up" onClick={this.onVoteUp} style={{color:this.state.vote.up?"green":"grey"}}><Icon>thumb_up</Icon></span>
                            1000
                            <span id="down" onClick={this.onVoteDown} style={{color:this.state.vote.down?"red":"grey"}}><Icon>thumb_down</Icon></span>
                            100
                        </div>
                        <div className="options">
                            <Icon>chat_bubble_outline</Icon>
                            <Icon>repeat</Icon>
                        </div>
                    </div>
                    </div>
                </Col> 
                <Col m={4} l={4} />
            </Row>
        );
    }

}

export default SinglePost;