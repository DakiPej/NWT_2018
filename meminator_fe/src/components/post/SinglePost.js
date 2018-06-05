import React, { Component } from 'react';
import { Card, CardTitle, Row, Col, Icon, Chip } from 'react-materialize';
import '../../styles/singlepost.css';

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

    parseDate = (timeStamp) => {
        let date = new Date(timeStamp);
        return (date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear());
    }

    render() {

        var tags = <div/>
        if(this.props.post.tags)
            tags = this.props.post.tags.map((tag) => <Chip ><a href={"/feed/"+tag.name}>tag.name</a></Chip>);

        return (
            <div className="post">
                <div className="post-header">
                    <div className="username"><Icon>person_outline</Icon>{this.props.post.user.username}</div>
                    <div className="date">{this.parseDate(this.props.post.timeStamp)}</div>
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
                <div className="tags">
                    {tags}
                </div>
            </div>
        );
    }

}

export default SinglePost;