import React, { Component } from 'react';
import { Row, Input, Icon, Col, Button } from 'react-materialize';
import '../styles/profiledetails.css';

import InfoProfile from './InfoProfile';
import EditProfile from './EditProfile';
import axios from 'axios';

class ProfileDetails extends Component{

    state = {
      data:{
        firstName: 'Default',
        lastName: 'Default',
        username: 'dakipej',
        email: 'Default@default.com',
        info: 'Dead inside obviouslys',
        followers: 3,
        following: 2,

      },
      image: 'http://138.68.186.248:8080/imagemodule/images/meme/1',
      details: true

    }


  componentDidMount(){
    this.getDetails();
  }
  getDetails = () => {
    axios.get("http://138.68.186.248:8080/usermodule/users/dakipej").then(this.handleSuccess)
  .catch(this.handleError);

  }
  handleSuccess=(response)=> {
    console.log(response.data);
    this.setState({data:response.data,image:'http://138.68.186.248:8080/imagemodule/images/meme/1'})
    }

    handleError=(error)=> {
      console.log("Data not found");
    }




    render(){

        return(
            <div style={{marginTop:"1vh",height:"60vh", width:"50%", justifyContent:"center", flexDirection:"column",alignItems:"center", paddingBottom:"1vh"}}>
            <Row className="forma">
            <Col  className="main" s={12} m={8} offset="s1 m8">
            {this.state.details?   <div> <InfoProfile data={this.state.data} image={this.state.image}/>
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
