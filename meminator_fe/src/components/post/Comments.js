import React, { Component } from 'react';
import { Card, CardTitle, Row, Col, Icon, Button, Input, Collection, CollectionItem } from 'react-materialize';
import '../../styles/comments.css';
import axios from 'axios';
import * as api from '../../globals';
import Comment from './Comment';

class Comments extends Component {

  state = {
    comments: [],
    nComment: '',
    oldComment: '',
    edit: false,
    vote: {
      up: false,
      down: false,
    },
    error: {
      nComment: '',
      oldComment: '',
    },
    errorMessage: {
      nComment: '',
      oldComment: ''
    },
    commentText:""
  };

  componentDidMount(){
    this.getComments();
  }

  getComments = () =>{
    axios.get(api.default.url + "/interactionmodule/comments/"+this.props.postId+"/0")
    .then(this.handleGetComments)
    .catch(this.handleError);
  }

  handleError = (err) => {
    console.log(err);
  }

  handleGetComments = (resp) => {
    this.setState({
      comments:resp.data
    });
  }

  addComment = (event) => {

    axios({
      url: api.default.url + "/interactionmodule/comments",
      method:"post",
      headers:{
        "Authorization" : "Bearer " + sessionStorage.getItem("token")
      },
      data:{
        "postId": this.props.postId,
        "commentText": this.state.nComment
      }
    }
    )
    .then(this.handleNewComment)
    .catch(this.handleError);

  }

  handleNewComment = () => {
    this.getComments();
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

    var primjer = [
      { username: "sbecirovic", komentar: "tekst može biti puno duži ali eto da provjerim", upvotes: 3, downwotes: 4 }, { username: "sbecirovic", komentar: "tekst", upvotes: 3, downwotes: 4 }
    ];
    var listItems = this.state.comments.map((poruke) => <Comment comment={poruke} />
      );
    return (



      <div style={{ height: "100%", width: "100%", justifyContent: "center", flexDirection: "column", alignItems: "center" }}>
        <Row style={{ width: "100%" }}>
          <Col className="comments" style={{width:"100%"}} >
            <Collection>
              {listItems}
            </Collection>
            {sessionStorage.getItem("username") && 
            <Card className='small' style={{ height: "250px" }} title="New comment"
              actions={[<Button small onClick={this.addComment} className="blue-grey" style={{ width: "100%", margin: "10px 0" }}><Icon left>add</Icon>Add comment</Button>]}>
              <div>
                <Input type="text" className="text-black" placeholder="New comment" className={this.state.error.nComment} s={12} name="nComment" onChange={this.onChange} label="Comment" />
                {this.state.error.nComment ? <div className="error">{this.state.errorMessage.nComment}</div> : ""}
              </div>
            </Card>}

          </Col>
        </Row>
      </div>
    );
  }

}

export default Comments;
