import React, { Component } from 'react';
import axios from 'axios';
import ProfileDetails from '../components/profile/ProfileDetails';
import PostGrid from '../components/profile/PostGrid';
import {Row,Col} from 'react-materialize';
class Profile extends Component{

    state = {
        post:{},
        component: <div></div>
    }



    render(){
        return(
            <Row style={{width:"80%", height:"80vh", marginTop:"20px"}}>
              <Col s={4} m={4} l={4}>
                <ProfileDetails username={this.props.match.params.username}/>
              </Col>
              <Col s={8} m={8} l={8}>
                <PostGrid username={this.props.match.params.username}/>
              </Col>
            </Row>
        );
    }

}

export default Profile;
