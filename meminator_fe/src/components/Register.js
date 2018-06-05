import React, { Component } from 'react';
import { Row, Input, Icon, Col, Button } from 'react-materialize';
import '../styles/register.css';

import axios from 'axios';

class Register extends Component{
  state = {
      username: '',
      password: '',
      password2: '',
      firstname: '',
      lastname: '',
      email: '',
      error: {
        username: '',
        firstname: '',
        lastname: '',
        password: '',
        password2: '',
        email: '',
      },
      errorMessage:{
        username:'',
        firstname:'',
        lastname: '',
        password:'',
        password2:'',
        email:''
      }
    }

    onRegister=(event)=>{


      event.preventDefault();
      if (this.onValidate()){
        axios.post("http://138.68.186.248:8080/usermodule/users/register",
      {
        username: this.state.username,
        firstName: this.state.firstname,
        lastName: this.state.lastname,
        email: this.state.email,
        password: this.state.password

      }).then(this.handleSuccess)
    .catch(this.handleError);
      }
    }
    handleSuccess=(response)=> {
      alert("Registration success");
      window.location='/';
      }

      handleError=(error)=> {
          alert("Registration failed: " + error.data);
      }




  onValidate=() =>{
    var validate=true;
    var error=this.state.error;
    var errorMessage = this.state.errorMessage;
    if (this.state.firstname.length===0)
  {
    validate=false;
    error.firstname= "invalid";
    errorMessage.firstname="First name cannot be empty!";
  }
  else {

      error.firstname= "valid";
  }
  if (this.state.lastname.length===0)
  {
  validate=false;
  error.lastname= "invalid";
  errorMessage.lastname="Last name cannot be empty!";
  }
  else{
    error.lastname= "valid";

  }
  if (this.state.username.length===0)
  {
  validate=false;
  error.username= "invalid";
  errorMessage.username="Username cannot be empty!";
   }
   if (this.state.email.length===0)
   {
     validate=false;
     error.email= "invalid";
     errorMessage.email="Email cannot be empty!";
    }

    if (this.state.password.length===0)
    {
      validate=false;
      error.password= "invalid";
      errorMessage.password="Password cannot be empty!";
     }
     if (this.state.password2.length===0 || this.state.password2!==this.state.password)
     {
     validate=false;
     error.password2= "invalid";
     errorMessage.password2="Password  field is wrong!";
      }
      this.setState({error: error,errorMessage:errorMessage});
      return validate;
  }


  onChange=(e)=> {
  this.setState({[e.target.name]:e.target.value});

  switch(e.target.name){
    case('username'):
    if(e.target.value.length<3){
    this.setState({error:{...this.state.error, username: "invalid"},
                  errorMessage:{...this.state.errorMessage, username:"Username is too short!"}});
  }
    else {
      this.setState({error:{...this.state.error, username: "valid"},
                    errorMessage:{...this.state.errorMessage, username:""}});
    }
    break;
    case('email'):
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(!re.test(String(e.target.value).toLowerCase()) || e.target.value.length<3 ){
    this.setState({error:{...this.state.error, email: "invalid"},
                  errorMessage:{...this.state.errorMessage, email:"E-mail is wrong!"}});
  }
    else {
      this.setState({error:{...this.state.error, email: "valid"},
                    errorMessage:{...this.state.errorMessage, email:""}});
    }
    break;
    case('password'):
    if(e.target.value.length<8){
    this.setState({error:{...this.state.error, password: "invalid"},
                  errorMessage:{...this.state.errorMessage, password:"Password is too short!"}});
  }
    else {
      this.setState({error:{...this.state.error, password: "valid"},
                    errorMessage:{...this.state.errorMessage, password:""}});
    }
    break;
    case('password2'):
    if(e.target.value!==this.state.password){
    this.setState({error:{...this.state.error, password2: "invalid"},
                  errorMessage:{...this.state.errorMessage, password2:"Passwords should match!"}});
  }
    else {
      this.setState({error:{...this.state.error, password2: "valid"},
                    errorMessage:{...this.state.errorMessage, password2:""}});
    }
    break;

  }


}
    render(){
        return(
            <div style={{marginTop:"1vh",height:"60vh", justifyContent:"center", flexDirection:"column",alignItems:"center"}}>
            <Row className="forma">
                <Col  className="register" s={10} m={4} offset="s1 m4">
                    <br/>
                    <div className="label" ><img src="http://i0.kym-cdn.com/entries/icons/original/000/002/252/NoMeGusta.jpg" className="responsive-image"/></div>
                    <br/>
                    <Row  style={{margin:"0"}}>
                      <Col s={6} style={{padding:"0"}} >
                        <Input type="text" name="firstname" className={this.state.error.firstname}  onChange={this.onChange} label="First name"/>
                        {this.state.error.firstname?<div className="error">{this.state.errorMessage.firstname}</div>:""}
                      </Col>
                      <Col s={6} style={{padding:"0"}}>
                        <Input type="text"  name="lastname" className={this.state.error.lastname}  onChange={this.onChange} label="Last name"/>
                        {this.state.error.lastname?<div className="error">{this.state.errorMessage.lastname}</div>:""}
                      </Col>
                    </Row>
                    <Input type="text" className="white-text" name="username" className={this.state.error.username} onChange={this.onChange} s={12} label="Username"  />
                    {this.state.error.username?<div className="error">{this.state.errorMessage.username}</div>:""}
                    <Input type="email" className="white-text"   className={this.state.error.email} name="email" onChange={this.onChange} label="Email" s={12}   />
                    {this.state.error.email?<div className="error">{this.state.errorMessage.email}</div>:""}
                    <Input type="password"  className="white-text"  className={this.state.error.password} name="password"  onChange={this.onChange} label="Password" s={12} />
                    {this.state.error.password?<div className="error">{this.state.errorMessage.password}</div>:""}
                    <Input type="password" name="password2"   className={this.state.error.password2}  onChange={this.onChange} label="Repeat password" s={12} />
                    {this.state.error.password2?<div className="error">{this.state.errorMessage.password2}</div>:""}
                    <Button small onClick={this.onRegister} className="blue-grey" style={{width:"100%", margin:"10px 0"}}><Icon left>person_add</Icon>Register</Button>
                </Col>
            </Row>
            </div>
        );
    }

}

export default Register;
