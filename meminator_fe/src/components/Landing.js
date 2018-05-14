import React, { Component } from 'react';
import { Row, Input, Icon, Col, Button } from 'react-materialize';
import '../styles/landing.css';

class Landing extends Component{

    render(){
        return(
            <div style={{marginTop:"20px",height:"80vh", display:"flex", justifyContent:"center", flexDirection:"column",alignItems:"center"}}>
            <Row>
                <Col  className="login" s={10} m={4} offset="s1 m4">
                    <div className="label" ><img src="https://orig00.deviantart.net/95a1/f/2012/151/5/1/meme_mother_of_god_png_by_agustifran-d51rx2a.png" className="responsive-image"/></div>
                    <Input icon="person" s={12} placeholder="username"/>
                    <Input icon="lock" s={12} type="password" placeholder="password"/>
                    <Button small className="blue-grey" style={{width:"100%", marginBottom:"10px"}}><Icon left>input</Icon>Login</Button>
                    <div className="register">Not registered?</div>
                    <Button small className="blue-grey" style={{width:"100%", margin:"10px 0"}}><Icon left>person_add</Icon>Register</Button>
                </Col>
            </Row>
            </div>
        );
    }

}

export default Landing;