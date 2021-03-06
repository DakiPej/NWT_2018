import React, { Component } from 'react';
import { Row, Input, Icon, Col, Button } from 'react-materialize';
import '../styles/landing.css';
import axios from 'axios';

class Landing extends Component {

    state = {
        username: "",
        password: ""
    }

    login = () => {
        var session_url = 'http://138.68.186.248:8080/usermodule/oauth/token';
        var username_b = 'devglan-client';
        var password_b = 'devglan-secret';
        var credentials = btoa(username_b + ':' + password_b);
        var basicAuth = 'Basic ' + credentials;
        var login_cred =
            'username=' + this.state.username + '&password=' + this.state.password + '&grant_type=' + 'password';
        axios({
            url: session_url,
            method: "post",
            headers: {
                Authorization: "Basic " + credentials,
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: login_cred
        }
        )
            .then(this.handleLogin)
            .catch(this.handleError);
    }

    componentDidMount() {
        if (sessionStorage.getItem("token") !== null) window.location = "/feed";
    }

    handleLogin = (response) => {
        console.log(response);
        sessionStorage.setItem("token", response.data.access_token);
        sessionStorage.setItem("username", this.state.username);
        window.location = "/feed";
    }

    handleError = (error) => {
        console.log(error);
        alert("Wrong username or password");
    }

    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }

    render() {
        return (
            <div>
                {sessionStorage.getItem("username") ? null:<div style={{ marginTop: "20px", height: "80vh", display: "flex", justifyContent: "center", flexDirection: "column", alignItems: "center" }}>
                    <Row style={{ width: "80%" }}>
                        <Col className="login" s={10} m={4} l={4} offset="s1 m4 l4">
                            <div className="label" ><img src="https://orig00.deviantart.net/95a1/f/2012/151/5/1/meme_mother_of_god_png_by_agustifran-d51rx2a.png" className="responsive-image" /></div>
                            <Input icon="person" s={12} onChange={this.onChange} name="username" value={this.state.username} placeholder="username" />
                            <Input icon="lock" s={12} onChange={this.onChange} name="password" value={this.state.password} type="password" placeholder="password" />
                            <Button small onClick={this.login} className="blue-grey" style={{ width: "100%", marginBottom: "10px" }}><Icon left>input</Icon>Login</Button>
                            <div className="register">Not registered?</div>
                            <Button onClick={() => { window.location = "/register" }} small className="blue-grey" style={{ width: "100%", margin: "10px 0" }}><Icon left>person_add</Icon>Register</Button>
                        </Col>
                    </Row>
                </div>}
            </div>
        );
    }

}

export default Landing;
