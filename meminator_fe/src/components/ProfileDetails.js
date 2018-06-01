import React, { Component } from 'react';
import { Row, Input, Icon, Col, Button } from 'react-materialize';
import '../styles/profiledetails.css';

import InfoProfile from './InfoProfile';
import EditProfile from './EditProfile';

class ProfileDetails extends Component{
  constructor(props){
    super(props);
    this.state = {
      data:{
        firstname: 'Default',
        lastname: 'Default',
        username: 'Default',
        email: 'Default@default.com',
        info: 'Dead inside obviouslys',
        followers: 3,
        following: 2,
        image: 'http://i0.kym-cdn.com/entries/icons/original/000/003/619/ForeverAlone.jpg'
      },
      details: true

    };
    this.getDetails=this.getDetails.bind(this);
  }
  componentDidMount(){
    this.getDetails();
  }
  getDetails(){

  }
  handleSuccess(response) {

    }

    handleError(error) {
      console.log("Data not found");
    }




    render(){

        return(
            <div style={{marginTop:"1vh",height:"60vh", width:"50%", justifyContent:"center", flexDirection:"column",alignItems:"center", paddingBottom:"1vh"}}>
            <Row className="forma">
            <Col  className="main" s={12} m={8} offset="s1 m8">
            {this.state.details?   <div> <InfoProfile data={this.state.data} />
            <Button small  className="blue-grey btn-floating right" onClick={() => this.setState({ details: false })}><Icon>edit</Icon></Button>
            </div>:
            <div>
            <EditProfile data={this.state.data}/>
            <Button small  className="blue-grey btn-floating right" onClick={() => this.setState({ details: true })} ><Icon>done_all</Icon></Button>
            </div>
            }
              <br/>
              <div>
              <br/>
              </div>
              </Col>
              </Row>
            </div>
            );
      }
}




export default ProfileDetails;
