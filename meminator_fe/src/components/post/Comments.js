import React, { Component } from 'react';
import { Card, CardTitle, Row, Col, Icon,Button,Input,Collection,CollectionItem } from 'react-materialize';
import '../../styles/comments.css';
import axios from 'axios';

class Comments extends Component {

    state = {
      comments: [],
      nComment: '',
      oldComment: '',
      edit: false,
      vote: {
      up: false,
      down: false,},
      error: {
        nComment: '',
        oldComment: '',
      },
      errorMessage: {
        nComment: '',
        oldComment:''
      }
    };

    addComment=(event)=>{

    }

    onChange=(e)=> {
    this.setState({[e.target.name]:e.target.value});

    switch(e.target.name){
      case('nComment'):
      if( e.target.value.length===0 ){
      this.setState({error:{...this.state.error, nComment: "invalid"},
                    errorMessage:{...this.state.errorMessage, nComment:"Comment cannot be empty!"}});
    }
    else this.setState({error:{...this.state.error, nComment: "valid"},
                  errorMessage:{...this.state.errorMessage, nComment:""}})
    break;
    case('oldComment'):
    if( e.target.value.length===0 ){
    this.setState({error:{...this.state.error, oldComment: "invalid"},
                  errorMessage:{...this.state.errorMessage, oldComment:"Comment cannot be empty!"}});
  }
  else this.setState({error:{...this.state.error, oldComment: "valid"},
                errorMessage:{...this.state.errorMessage, oldComment:""}})
  break;
  }
}



    render() {

      var primjer=[
        {username:"sbecirovic",komentar:"tekst može biti puno duži ali eto da provjerim",upvotes:3,downwotes:4},{username:"sbecirovic",komentar:"tekst",upvotes:3,downwotes:4}
      ];
      var listItems=primjer.map((poruke)=>
        <CollectionItem><Card className='small' style={{height:"200px"}} title={this.state.edit?"Edit comment":poruke.komentar}>
        {this.state.edit?
          <div>
          <Input type="text" className="black-text"  placeholder={poruke.komentar} className={this.state.error.oldComment} s={12} name="oldComment" onChange={this.onChange}  />
          </div>:
          <div>
        <a href={'/profile/'+poruke.username}>{poruke.username}</a>
        </div>
        }
        <div className="card-action">
        {this.state.edit?
          <Button small  className="blue-grey right" style={{margin:"1.5px"}} onClick={() => this.setState({ edit: true })}><Icon>done_all</Icon></Button>
          :
          <div>
          <Button small  className="blue-grey left" style={{margin:"1.5px"}}  onClick={() => this.setState({ details: false })}><Icon>thumb_up</Icon>{poruke.upvotes}</Button>
          <Button small  className="blue-grey  left" style={{margin:"1.5px"}} onClick={() => this.setState({ details: false })}><Icon>thumb_down</Icon>{poruke.downwotes}</Button>
          {"sbecirovic"===poruke.username?
          <div>
            <Button small  className="blue-grey right" style={{margin:"1.5px"}} onClick={() => this.setState({ edit: true })}><Icon>edit</Icon></Button>
            <Button small  className="blue-grey right" style={{margin:"1.5px"}} onClick={() => this.setState({ details: false })}><Icon>delete</Icon></Button>
          </div>
          : null}
          </div>}
        </div>

      </Card>
      </CollectionItem>);
        return (



            <div style={{marginTop:"1vh",height:"100%", width:"100%", justifyContent:"center", flexDirection:"column",alignItems:"center"}}>
                      <Row style= {{width:"100%"}}>
                      <Col  className="comments" >
                      <Collection>
                      {listItems}
                      </Collection>
                        <Card className='small' style={{height:"250px"}} title="New comment"
                        actions={[<Button small onClick={this.addComment} className="blue-grey" style={{width:"100%", margin:"10px 0"}}><Icon left>add</Icon>Add comment</Button>]}>
                        <div>
                        <Input type="text" className="black-text"  placeholder="New comment"  className={this.state.error.nComment} s={12}name="nComment" onChange={this.onChange} label="Comment"   />
                        {this.state.error.nComment?<div className="error">{this.state.errorMessage.nComment}</div>:""}
                        </div>
                      </Card>

                      </Col>
                      </Row>
                    </div>
        );
    }

}

export default Comments;
