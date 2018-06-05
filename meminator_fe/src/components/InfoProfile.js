import React, { Component } from 'react';
import { Row, Input, Icon, Col, Button } from 'react-materialize';
import '../styles/profiledetails.css';
import axios from 'axios';

class InfoProfile extends Component{

  state = {
      data: {},
      image: '',
      placeholder: 'http://i0.kym-cdn.com/entries/icons/original/000/003/619/ForeverAlone.jpg',
      following: false
    }

    componentDidMount(){
    this.getDetails();
    }
/*--------------------------Detalji o korisniku-------------------*/
    getDetails = () => {
      axios.get("http://138.68.186.248:8080/usermodule/users/"+this.props.username).then(this.handleSuccess)
    .catch(this.handleError);
    }

    handleSuccess=(response)=> {
      console.log(response.data);
      this.setState({data:response.data,
        image:'http://138.68.186.248:8080/imagemodule/images/profile/'+response.data.username});
      if (sessionStorage.getItem("username"))
      {
        axios.get("http://138.68.186.248:8080/usermodule/areFriends/"+this.props.username, {
              headers: {Authorization: "Bearer " + sessionStorage.getItem("token")}
          }).then((response)=>{this.setState({following:response.data})})
      .catch((error)=>{console.log(error.data)});
      }
      }

      handleError=(error)=> {
        console.log("Data not found");
      }

/*---------------(Un)Follow------------------*/

    followAction=(event)=>{
      event.preventDefault();
      axios({
        url: 'http://138.68.186.248:8080/usermodule/follow/'+this.props.username,
        method: 'put',
         headers: {
           'Authorization': 'Bearer ' + sessionStorage.getItem("token")
      }
      })
      .then((response)=>{this.getDetails();})
  .catch((response)=>{alert("Follow failed!");});
    }



      unfollowAction=(event)=>{
        event.preventDefault();
        axios({
          url: 'http://138.68.186.248:8080/usermodule/unfollow/'+this.props.username,
          method: 'delete',
           headers: {
             'Authorization': 'Bearer ' + sessionStorage.getItem("token")
        }
        })
        .then((response)=>{this.getDetails();})
    .catch((response)=>{alert("Unfollow failed!");});
      }


/*---------------Default slika-----------*/
    addDefaultSrc=(ev)=>{
      ev.target.src = this.state.placeholder;
    }
    render(){
      var follow=<div></div>
      if (!this.props.userProfile)
      follow = <div>{this.state.following?<div>  <Button small onClick={this.unfollowAction} className="blue-grey right" style={{width:"100%", margin:"10px 0"}}>
      <Icon left>visibility_off</Icon>Unfollow</Button>
        <hr className="separator"/></div>:
      <div> <Button small className="blue-grey right"  onClick={this.followAction} style={{width:"100%", margin:"10px 0"}}><Icon left>visibility</Icon>Follow</Button>
      <hr className="separator"/></div>}</div>
      ;

        return(
          <div>
                <br/>
                <div className="label" ><img src={this.state.image} onError={this.addDefaultSrc} className="responsive-image"/></div>
                <div classNam="detail">
                <h4>{this.state.data.firstName} {this.state.data.lastName}</h4>
                </div>
                <hr className="separator"/>
                <div className="detail">
                <Col s={6}>
                  <label>Following:</label>
                <h6>{this.state.data.followingCount}</h6>
                </Col>
                <Col s={6}>
                  <label>Followers:</label>
                <h6>{this.state.data.followedByCount}</h6>
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
               {follow}
            </div>
            );
      }
}




export default InfoProfile;
