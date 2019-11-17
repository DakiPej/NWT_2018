import React, { Component } from 'react';
import { Card, CardTitle, Row, Col, Icon,Button,Input,Collection,CollectionItem } from 'react-materialize';
import '../../styles/postgrid.css';
import axios from 'axios';



class PostGrid extends Component {

    state = {
    posts:[]
    };

    componentDidMount(){
      this.getPosts();
    }

    getPosts=()=>{
      axios.get('http://138.68.186.248:8080/postmodule/posts/users/'+this.props.username)
      .then((response)=>{this.setState({posts:response.data})})
      .catch((response)=>{alert("Data retrieval failed")});
    }



    render() {

          const secondColumnStart = Math.ceil(this.state.posts.length / 2);

        return (
            <div style={{marginTop:"1vh",height:"100%", width:"100%", justifyContent:"center", flexDirection:"column",alignItems:"center"}}>
                      <Row style= {{width:"100%"}}>
                      <Col className="col-md-12" style= {{width:"50%"}}>

                          {this.state.posts.slice(0,secondColumnStart).map(item =>
                            <Card className=' blue-grey darken-4 white-text'
                              header={<CardTitle image={item.imageURL} ></CardTitle>}
                              actions={[
                              <Button small onClick={(event)=>{window.location='/post/'+item.id}} className="blue-grey" style={{width:"100%", margin:"10px 0"}}><Icon left>send</Icon>See post</Button>]}>
                              {item.info}
                              </Card>)}
                        </Col>
                        <Col className="col-md-12"  style= {{width:"50%"}}>
                            {this.state.posts.slice(secondColumnStart).map(item =>
                               <Card className=' blue-grey darken-4 white-text'
                                header={<CardTitle image={item.imageURL} ></CardTitle>}
                                actions={[
                                <Button small onClick={(event)=>{window.location='/post/'+item.id}} className="blue-grey" style={{width:"100%", margin:"10px 0"}}><Icon left>send</Icon>See post</Button>]}>
                                {item.info}
                                </Card>)}
                        </Col>

                      </Row>
                    </div>
        );
    }

}

export default PostGrid;
