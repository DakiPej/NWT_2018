import React, { Component } from 'react';
import { Row, Input, Icon, Col, Button } from 'react-materialize';
import '../styles/profiledetails.css';


class InfoProfile extends Component{

  state = {
      data: this.props.data,
      image: this.props.image,
      placeholder: 'http://i0.kym-cdn.com/entries/icons/original/000/003/619/ForeverAlone.jpg'
    }

    render(){
      console.log(this.state.data);
      console.log(this.state.image)
        return(
          <div>
                <br/>
                <div className="label" ><img src={this.state.image?this.state.image:this.state.placeholder} className="responsive-image"/></div>
                <div classNam="detail">
                <h4>{this.state.data.firstName} {this.state.data.lastName}</h4>
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
