import React, { Component } from 'react';
import { Card, CardTitle, Row, Col, Icon, Chip } from 'react-materialize';
import '../../styles/singlepost.css';
import Comments from './Comments';
import * as api from '../../globals';
import axios from 'axios';

class SinglePost extends Component {

    state = {
        vote: {
            up: false,
            down: false
        },
        upVote: this.props.post.upVote,
        downVote: this.props.post.downVote
    };

    componentDidMount() {
        this.setVotes();
    }

    handleOnVoteUp = () => {
        var i = 1;
        var downVote = this.state.downVote;
        if (this.state.vote.up === true) { i = -1; var downVote = this.state.downVote - i; }
        var upVote = this.state.upVote + i;
        const vote = {
            up: !this.state.vote.up,
            down: false
        };

        this.setState({
            vote,
            upVote,
            downVote
        });
    }

    setVotes = () => {
        axios({
            url: api.default.url + "/interactionmodule/postVotes/hasLIked/" + this.props.post.id,
            method: "get",
            headers: {
                "Authorization": "Bearer " + sessionStorage.getItem("token")
            }
        })
            .then(this.handleSetVotes)
            .catch((e) => { console.log(e) });
    }

    handleSetVotes = (resp) => {
        console.log(resp);
        if (resp.data === true) {
            this.setState({
                vote: {
                    up: true,
                    down: false
                }
            });
        } else {
            this.setState({
                vote: {
                    up: false,
                    down: true
                }
            });
        }
    }

    onVoteDown = () => {
        if (this.state.vote.down === false) {
            axios({
                url: api.default.url + "/interactionmodule/postVotes",
                method: "post",
                data: {
                    "post": this.props.post.id,
                    "upVote": false
                },
                headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("token")
                }
            }
            ).then(this.handelOnVoteDown);
        } else {
            axios({
                url: api.default.url + "/interactionmodule/postVotes",
                method: "delete",
                data: {
                    "postId": this.props.post.id,
                },
                headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("token")
                }
            }
            ).then(this.handelOnVoteDown);
        }

    }

    onVoteUp = () => {
        if (this.state.vote.up === false) {
            axios({
                url: api.default.url + "/interactionmodule/postVotes",
                method: "post",
                data: {
                    "post": this.props.post.id,
                    "upVote": true
                },
                headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("token")
                }
            }
            ).then(this.handleOnVoteUp);
        } else {
            axios({
                url: api.default.url + "/interactionmodule/postVotes",
                method: "delete",
                data: {
                    "postId": this.props.post.id,
                },
                headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("token")
                }
            }
            ).then(this.handleOnVoteUp);
        }
    }

    handelOnVoteDown = () => {
        var i = 1;
        var downVote = this.state.downVote;
        if (this.state.vote.down === true) { i = -1;  downVote = this.state.downVote + i; }
        var upVote = this.state.upVote - i;
        const vote = {
            up: false,
            down: !this.state.vote.down
        };

        this.setState({
            vote,
            downVote,
            upVote
        });
    }

    parseDate = (timeStamp) => {
        let date = new Date(timeStamp);
        return (date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear());
    }

    render() {

        var tags = <div />
        if (this.props.post.tags)
            tags = this.props.post.tags.map((tag) => <Chip ><a href={"/feed/" + tag.id}>{tag.name}</a></Chip>);

        return (
            <div className="post" style={this.props.single === "T" ? { width: "100%", margin: "0" } : null}>
                <div className="post-header">
                    <div className="username" onClick={() => (window.location = "/profile/" + this.props.post.user.username)}><Icon>person_outline</Icon>{this.props.post.user.username}</div>
                    <div className="date">{this.parseDate(this.props.post.timeStamp)}</div>
                </div>
                <img className="post-image" src={this.props.post.imageURL} />
                <div className="post-footer">
                    <div className="votes">
                        <span id="up" onClick={this.onVoteUp} style={{ color: this.state.vote.up ? "green" : "grey" }}><Icon>thumb_up</Icon></span>
                        {this.state.upVote}
                        <span id="down" onClick={this.onVoteDown} style={{ color: this.state.vote.down ? "red" : "grey" }}><Icon>thumb_down</Icon></span>
                        {this.state.downVote}
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
