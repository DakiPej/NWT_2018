import React, { Component } from 'react';
import {
    Navbar,
    NavItem,
    Icon
} from 'react-materialize';
import Notification from '../notifications/NotificationContainer' 

class Header extends Component{
    render(){
        return(
            <Navbar brand='meminator' className="blue-grey darken-4" style={{padding:"0 0 20px 20px"}} right fixed>
                <NavItem href='get-started.html'><Icon>home</Icon></NavItem>
                <NavItem href='get-started.html'><Icon>explore</Icon></NavItem>
                <NavItem href='get-started.html'><Icon>input</Icon></NavItem>
                {sessionStorage.getItem("username") &&
                <Notification />
            }
            </Navbar>
        );
    }
}

export default Header;