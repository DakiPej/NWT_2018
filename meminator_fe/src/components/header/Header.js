import React, { Component } from 'react';
import {
    Navbar,
    NavItem,
    Icon
} from 'react-materialize';
import Notification from '../notifications/NotificationContainer';
import axios from 'axios';


class Header extends Component{

    updateLastTimeChecked = () => {
        const authorization = 'Bearer ' + sessionStorage.getItem("token");
        const lastTimeCheckedValue = localStorage.getItem("lastTime");
        const body = { lastTimeChecked: lastTimeCheckedValue };
        localStorage.removeItem("lastTime");
        const notificationCount = 0;
        console.log(body);
        console.log(notificationCount);

        axios({
            url: 'http://138.68.186.248:8080/interactionmodule/users/lastTimeChecked',
            method: 'put',
            headers: {
                Authorization: authorization,
                'Content-Type': 'application/json'
            },
            data: body
        }).then(this.handleUpdateLastTimeChecked).catch(this.handleErrorUpdateLastTimeChecked);
        this.setState(() => { notificationCount })
    }
    handleUpdateLastTimeChecked = (response) => {
        console.log("USPJEH !");
        console.log(response.data);
        sessionStorage.clear(); window.location = "/"
    }

    handleErrorUpdateLastTimeChecked = (error) => {
        console.log("NEUSPJEH");
        console.log(error);
        sessionStorage.clear(); window.location = "/"
    }


    render(){
        return(
            <Navbar brand='meminator' className="blue-grey darken-4" style={{padding:"0 0 20px 20px"}} right fixed>
                {sessionStorage.getItem("username") && <Notification />}
                <NavItem href='get-started.html'><Icon>home</Icon></NavItem>
                <NavItem href='get-started.html'><Icon>explore</Icon></NavItem>
                {sessionStorage.getItem("token")===null?
                    <NavItem href='/login'><Icon>lock</Icon></NavItem>
                    : <NavItem onClick={this.updateLastTimeChecked}><Icon>input</Icon></NavItem>}
            </Navbar>
        );
    }
}

export default Header;