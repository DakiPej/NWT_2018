import React, { Component } from 'react';
import axios from 'axios';
import ProfileDetails from '../components/profile/ProfileDetails';
import PostGrid from '../components/profile/PostGrid';
import {Row,Col} from 'react-materialize';
class Profile extends Component{

    state = {
        post:{},
        component: <div></div>,
        width:""
    }

    componentDidMount(){
        this.setState({
            width:document.getElementById("parent").width
        });
    }


    render(){
        return(
            <Row style={{position:"relative",width:"70%", height:"80vh", marginTop:"20px"}}>
              <Col id="parent" s={4} m={4} l={4}>
                <div style={{position:"fixed", width:"23%"}}>
                <ProfileDetails  username={this.props.match.params.username}/>
                </div>
              </Col>
              <Col s={8} m={8} l={8}>
                <PostGrid username={this.props.match.params.username}/>
              </Col>
            </Row>
        );
    }

}

export default Profile;
