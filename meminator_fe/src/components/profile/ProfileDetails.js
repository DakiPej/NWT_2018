import React, { Component } from 'react';
import { Row, Input, Icon, Col, Button } from 'react-materialize';
import '../../styles/profiledetails.css';

import InfoProfile from './InfoProfile';
import EditProfile from './EditProfile';


class ProfileDetails extends Component{

    state = {
      username: this.props.username,
      details: true,
      userProfile: true

    }


    componentDidMount(){
      if (this.props.username!=sessionStorage.getItem("username"))
          this.setState({userProfile: false});
      else
          this.setState({userProfile: true});
    }

    render(){

        var profil=<div></div>
        if (this.state.userProfile)
        {
          profil= <div> {this.state.details? <div> <InfoProfile username={this.state.username} userProfile={this.state.userProfile}/>
            <Button small  className="blue-grey btn-floating right" onClick={() => this.setState({ details: false })}><Icon>edit</Icon></Button>
            </div>:
            <div>
            <EditProfile  username={this.state.username} />
            <Button small  className="blue-grey btn-floating right" onClick={() => this.setState({ details: true })} ><Icon>done_all</Icon></Button>
            </div>
          }
          </div>;
        }
        else {
          profil=<div><InfoProfile  username={this.state.username} userProfile={this.state.userProfile}/></div>
        }

        return(
            <div style={{marginTop:"1vh",height:"100vh", width:"100%", justifyContent:"center", flexDirection:"column",alignItems:"center", paddingBottom:"1vh"}}>
              <Row className="forma" style= {{width:"100%"}}>
                <Col  className="main"  style= {{width:"100%"}} >
                  {profil}
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
