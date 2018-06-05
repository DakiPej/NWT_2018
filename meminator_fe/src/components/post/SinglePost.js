import React, { Component } from 'react';
import { Card, CardTitle, Row, Col, Icon } from 'react-materialize';
import '../../styles/singlepost.css';
import Comments from './Comments';

class SinglePost extends Component {

    state = {
        vote: {
            up: false,
            down: false
        }
    };

    onVoteUp = () => {
        const vote = {
            up: !this.state.vote.up,
            down: false
        };

        this.setState({
            vote
        });
    }

    onVoteDown = () => {
        const vote = {
            up: false,
            down: !this.state.vote.down
        };

        this.setState({
            vote
        });
    }

    render() {
        return (
                    <div className="post" style={this.props.single==="T"?{width:"100%", margin:"0"}:null}>
                        <div className="post-header">
                            <div className="username"><Icon>person_outline</Icon>{this.props.post.user.username}</div>
                            <div className="date">11/11/11</div>
                        </div>
                        <img className="post-image" src={this.props.post.imageURL} />
                        <div className="post-footer">
                            <div className="votes">
                                <span id="up" onClick={this.onVoteUp} style={{ color: this.state.vote.up ? "green" : "grey" }}><Icon>thumb_up</Icon></span>
                                {this.props.post.upVote}
                            <span id="down" onClick={this.onVoteDown} style={{ color: this.state.vote.down ? "red" : "grey" }}><Icon>thumb_down</Icon></span>
                                {this.props.post.downVote}
                        </div>
                            <div className="options">
                                <Icon>chat_bubble_outline</Icon>
                                <Icon>repeat</Icon>
                            </div>
                        </div>
                    </div>
        );
    }

}

export default SinglePost;
