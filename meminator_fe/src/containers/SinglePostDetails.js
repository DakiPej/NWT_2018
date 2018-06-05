import React, { Component } from 'react';
import axios from 'axios';
import SinglePost from '../components/post/SinglePost';
import * as api from '../globals';
import Comments from '../components/post/Comments';
import {Row,Col} from 'react-materialize';
class SinglePostDetails extends Component{

    state = {
        post:{},
        component: <div></div>
    }

    componentWillMount(){
        this.getPost();
    }

    getPost = () => {     
        axios.get(api.default.url+'/postmodule/posts/'+this.props.match.params.id)
        .then(this.handlePost)
        .catch((err) => {console.log(err);});
    }

    handlePost = (res) =>{
        console.log(res);
        this.setState({post : res.data});
        this.setState({component : <SinglePost post={this.state.post} single={"T"}/>});
    }

    render(){
        return(
            <Row style={{width:"80%", height:"80vh", marginTop:"20px"}}>
              <Col s={6} m={6} l={6}>
                {this.state.component}
              </Col>
              <Col s={6} m={6} l={6}>
                <Comments postId={this.props.match.params.id}/>
              </Col>
            </Row>
        );
    }

}

export default SinglePostDetails;
