import React, { Component } from 'react';
import { Row, Input, Icon, Col, Button } from 'react-materialize';
import '../styles/profiledetails.css';


class InfoProfile extends Component{

  state = {
      data: this.props.data
    }

    render(){

        return(
          <div>
                <br/>
                <div className="label" ><img src={this.state.data.image}className="responsive-image"/></div>
                <div classNam="detail">
                <h4>{this.state.data.firstname} {this.state.data.lastname}</h4>
                </div>
                <hr className="separator"/>
                <div className="detail">
                <Col s={6}>
                  <label>Following:</label>
                <h6>{this.state.data.following}</h6>
                </Col>
                <Col s={6}>
                  <label>Followers:</label>
                <h6>{this.state.data.followers}</h6>
                </Col>
                </div >
                <div className="detail">

                <hr className="separator"/>
                  <label>Info:</label>
                  <h6>{this.state.data.info}</h6>
                </div>
                <hr className="separator"/>
              <div className="detail">
                <label>Username:</label>
                <h6>{this.state.data.username}</h6>
              </div>
              <hr className="separator"/>
              <div className="detail">
                <label>Email:</label>
                <h6>{this.state.data.email}</h6>
              </div>
              <hr className="separator"/>
              <Button small className="blue-grey right" style={{width:"100%", margin:"10px 0"}}><Icon left>visibility</Icon>Follow</Button>
              <Button small className="blue-grey right" style={{width:"100%", margin:"10px 0"}}><Icon left>visibility_off</Icon>Unfollow</Button>
              <hr className="separator"/>
            </div>
            );
      }
}




export default InfoProfile;
