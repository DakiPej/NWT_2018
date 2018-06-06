import React ,{Component} from 'react';
import axios from 'axios';
import * as api from "../../globals";
import { Card, CardTitle, Row, Col, Icon, Button, Input, Collection, CollectionItem } from 'react-materialize';


class Comment extends Component {

    state = {
        vote: {
            up: false,
            down: false
        },
        upVote: this.props.comment.upVoteCount,
        downVote: this.props.comment.downVoteCount,
        oldComment: ""
    }

    componentDidMount() {
        this.setVotes();
    }

    handleOnVoteUp = () => {
        var i = 1;
        if (this.state.vote.up === true) {i = -1;var upVote = this.state.upVote + i;}   
        
        var downVote = this.state.downVote - i;
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
            url: api.default.url + "/interactionmodule/commentVotes/hasLiked/" + this.props.comment.commentId,
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
                url: api.default.url + "/interactionmodule/commentVotes",
                method: "post",
                data: {
                    "commentId": this.props.comment.commentId,
                    "upVote": false
                },
                headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("token")
                }
            }
            ).then(this.handelOnVoteDown);
        } else {
            axios({
                url: api.default.url + "/interactionmodule/commentVotes",
                method: "delete",
                data: {
                    "commentId": this.props.comment.commentId,
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
                url: api.default.url + "/interactionmodule/commentVotes",
                method: "post",
                data: {
                    "commentId": this.props.comment.commentId,
                    "upVote": true
                },
                headers: {
                    "Authorization": "Bearer " + sessionStorage.getItem("token")
                }
            }
            ).then(this.handleOnVoteUp);
        } else {
            axios({
                url: api.default.url + "/interactionmodule/commentVotes",
                method: "delete",
                data: {
                    "commentId": this.props.comment.commentId,
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
        if (this.state.vote.down === true) {i = -1; var upVote = this.state.upVote - i;}
        var downVote = this.state.downVote + i;
        
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

    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
        console.log(e.target.name + " " + e.target.value);
        switch (e.target.name) {
          case ('nComment'):
            if (e.target.value.length === 0) {
              this.setState({
                error: { ...this.state.error, nComment: "invalid" },
                errorMessage: { ...this.state.errorMessage, nComment: "Comment cannot be empty!" }
              });
            }
            else this.setState({
              error: { ...this.state.error, nComment: "valid" },
              errorMessage: { ...this.state.errorMessage, nComment: "" }
            })
            break;
          case ('oldComment'):
            if (e.target.value.length === 0) {
              this.setState({
                error: { ...this.state.error, oldComment: "invalid" },
                errorMessage: { ...this.state.errorMessage, oldComment: "Comment cannot be empty!" }
              });
            }
            else this.setState({
              error: { ...this.state.error, oldComment: "valid" },
              errorMessage: { ...this.state.errorMessage, oldComment: "" }
            })
            break;
        }
      }

    render() {
        return (
            <CollectionItem><Card className='small' style={{ height: "200px" }} title={this.state.edit ? "Edit comment" : this.props.comment.commentText}>
                {this.state.edit ?
                    <div>
                        <Input type="text" className="black-text" placeholder={this.props.comment.commentText} className={this.state.error.oldComment} s={12} name="oldComment" onChange={this.onChange} />
                    </div> :
                    <div>
                        <a href={'/profile/' + this.props.comment.commenter}>{this.props.comment.commenter}</a>
                    </div>
                }
                <div className="card-action">
                    {this.state.edit ?
                        <Button small className="blue-grey right" style={{ margin: "1.5px" }} onClick={() => this.setState({ edit: true })}><Icon>done_all</Icon></Button>
                        :
                        <div>
                            <Button small className="blue-grey left" style={{ margin: "1.5px", color: this.state.vote.up ? "green" : "white" }} onClick={this.onVoteUp}><Icon left>thumb_up</Icon>{this.state.upVote}</Button>
                            <Button small className="blue-grey  left" style={{ margin: "1.5px", color: this.state.vote.down ? "red" : "white" }} onClick={this.onVoteDown}><Icon left>thumb_down</Icon>{this.state.downVote}</Button>
                            {sessionStorage.getItem("username") === this.props.comment.commenter ?
                                <div>
                                    <Button small className="blue-grey right" style={{ margin: "1.5px" }} onClick={() => this.setState({ edit: true })}><Icon>edit</Icon></Button>
                                    <Button small className="blue-grey right" style={{ margin: "1.5px" }} onClick={() => this.setState({ details: false })}><Icon>delete</Icon></Button>
                                </div>
                                : null}
                        </div>}
                </div>

            </Card>
            </CollectionItem>
        );
    }


}

export default Comment;