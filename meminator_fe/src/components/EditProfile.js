import React, { Component } from 'react';
import { Row, Input, Icon, Col, Button,Modal } from 'react-materialize';
import '../styles/profiledetails.css';
import $ from 'jquery'



class EditProfile extends Component{
    state = {
      password: '',
      password2: '',
      passwordOriginal:'',
      passwordChange:'',
      image: '',
      username: this.props.data.username,
      email: this.props.data.email,
      firstname: this.props.data.firstname,
      lastname: this.props.data.lastname,
      info: this.props.data.info,
      error: {
        username: '',
        firstname: '',
        lastname: '',
        password: '',
        password2: '',
        email: '',
        passwordOriginal:'',
        image:'file-path',
        passwordChange:''
      },
      errorMessage:{
        passwordChange:'',
        username:'',
        passwordOriginal:'',
        firstname:'',
        lastname: '',
        password:'',
        password2:'',
        email:'',
        image:'',
      },
    }

  onPasswordChange = (event) => {
    event.preventDefault();
    if(this.validatePassword()){
    }
  }
  fileChangedHandler = (event) => {
  this.setState({image: event.target.files[0]})
  var file=event.target.files[0];
  if (file!==undefined){
  var t = file.type.split('/').pop().toLowerCase();
   if (t != "jpeg" && t != "jpg" && t != "png" && t != "bmp" && t != "gif") {
     this.setState({error:{...this.state.error, image: "file-path invalid"},
                   errorMessage:{...this.state.errorMessage, image:"Image format is wrong!"}});
   }
   else {
     if (file.size > 2048000) {
       this.setState({error:{...this.state.error, image: "file-path invalid"},
                     errorMessage:{...this.state.errorMessage, image:"Max Upload size is 2MB only!"}});
     }
     else
     this.setState({error:{...this.state.error, image: "file-path valid"},
                   errorMessage:{...this.state.errorMessage, image:""}});
   }
   if (file.size > 2048000) {
     this.setState({error:{...this.state.error, image: "file-path invalid"},
                   errorMessage:{...this.state.errorMessage, image:"Max Upload size is 2MB only!"}});
   }
  }
}
  /*
  const formData = new FormData()
  formData.append('myFile', this.state.selectedFile, this.state.selectedFile.name)
  axios.post('my-domain.com/file-upload', formData)*/
  onValidate = () => {
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
    if(this.state.passwordOriginal.length ===0){
      validate=false;
      error.passwordOriginal= "invalid";
      errorMessage.passwordOriginal="Password cannot be empty!";
  }
      this.setState({error: error,errorMessage:errorMessage});
      return validate;
  }
  validatePassword = () =>  {
    var validate=true;
    var error=this.state.error;
    var errorMessage = this.state.errorMessage;
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
     errorMessage.password2="Password field is wrong!";
   }
    if(this.state.passwordChange.length ===0){
     validate=false;
     error.passwordChange= "invalid";
     errorMessage.passwordChange="Password cannot be empty!";
   }
     this.setState({error: error,errorMessage:errorMessage});
     return validate;

  }
  onProfileSubmit = (event) => {


    event.preventDefault();
    if (this.onValidate()){
      //axios
    }
  }
  handleSuccess = (response)  => {

    }

    handleError = (error) => {
        alert("Registration failed")
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
    case('passwordOriginal'):
    if(e.target.value.length ===0){
    this.setState({error:{...this.state.error, passwordOriginal: "invalid"},
                  errorMessage:{...this.state.errorMessage, passwordOriginal:"Password cannot be empty!"}});
  }
    else {
      this.setState({error:{...this.state.error, passwordOriginal: "valid"},
                    errorMessage:{...this.state.errorMessage, passwordOriginal:""}});
    }
    break;
    case('passwordChange'):
    if(e.target.value.length ===0){
    this.setState({error:{...this.state.error, passwordChange: "invalid"},
                  errorMessage:{...this.state.errorMessage, passwordChange:"Password cannot be empty!"}});
  }
    else {
      this.setState({error:{...this.state.error, passwordChange: "valid"},
                    errorMessage:{...this.state.errorMessage, passwordChange:""}});
    }
    break;

  }


}
    render(){
        return(
            <div>
              <Row  style={{margin:"0"}}>
              <br/>
                <Col s={6} style={{padding:"0"}} >
                  <Input type="text" name="firstname"   placeholder={this.state.firstname} className={this.state.error.firstname}  onChange={this.onChange} label="First name"/>
                  {this.state.error.firstname?<div className="error">{this.state.errorMessage.firstname}</div>:""}
                </Col>
                <Col s={6} style={{padding:"0"}}>
                  <Input type="text"  name="lastname"  placeholder={this.state.lastname} className={this.state.error.lastname}  onChange={this.onChange} label="Last name"/>
                  {this.state.error.lastname?<div className="error">{this.state.errorMessage.lastname}</div>:""}
                </Col>
              </Row>
              <Input type="text" className="white-text" name="username" placeholder={this.state.username} className={this.state.error.username} onChange={this.onChange} s={12} label="Username"  />
              {this.state.error.username?<div className="error">{this.state.errorMessage.username}</div>:""}
              <Input type="email" className="white-text"  placeholder={this.state.email}   className={this.state.error.email} name="email" onChange={this.onChange} label="Email" s={12}   />
              {this.state.error.email?<div className="error">{this.state.errorMessage.email}</div>:""}
              <Input type="text"  className="white-text"  placeholder={this.state.info} name="info"  onChange={this.onChange} label="Info" s={12} />
              <div className="file-field input-field inline">
                  <div className="btn blue-grey">
                     <span>Browse</span>
                     <input type="file" onChange={this.fileChangedHandler} ref = "file"/>
                  </div>
                  <div className="file-path-wrapper">
                     <input className={this.state.error.image} type="text"  placeholder="Upload profile picture" />
                  </div>
               </div>
               {this.state.error.image?<div className="error">{this.state.errorMessage.image}</div>:""}
               <Input type="password" name="password2"  id="modal-input" className={this.state.error.passwordOriginal}  onChange={this.onChange} label="Password" s={12} />
               {this.state.error.passwordOriginal?<div className="error">{this.state.errorMessage.passwordOriginal}</div>:""}
               <Button small onClick={this.onProfileSubmit} className="blue-grey right " style={{width:"100%",margin:"10px 0"}}><Icon left>send</Icon>Submit</Button>
               <hr className="separator"/>
               <Modal name="Modal"

                header="Password change" style={{color:"black"}}
                actions={
                    <div>
                    <Button onClick={this.onPasswordChange} className="blue-gray">Change password</Button>
                    <Button flat modal="close">Dismiss</Button>
                    </div>
                    }
                trigger={<Button small className="blue-grey left" style={{width:"100%",margin:"10px 0"}}>Change password</Button>}>
                      <Input type="password" style={{color:"black"}} id="modal-input"  className={this.state.error.password} name="password"  onChange={this.onChange} label="Password" s={12} />
                      {this.state.error.password?<div className="error">{this.state.errorMessage.password}</div>:""}
                      <Input type="password" style={{color:"black"}} name="password2"  id="modal-input" className={this.state.error.password2}  onChange={this.onChange} label="Repeat password" s={12} />
                      {this.state.error.password2?<div className="error">{this.state.errorMessage.password2}</div>:""}
                      <Input type="password" style={{color:"black"}} name="passwordChange"  id="modal-input" className={this.state.error.passwordChange}  onChange={this.onChange} label="Current password" s={12} />
                      {this.state.error.passwordChange?<div className="error">{this.state.errorMessage.passwordChange}</div>:""}
                      </Modal>

              <hr className="separator"/>

            </div>
        );
    }
}

export default EditProfile;
