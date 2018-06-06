import React, { Component } from 'react';
import {
    Navbar,
    NavItem,
    Icon,
    Input,
    Button
} from 'react-materialize';
import Notification from '../notifications/NotificationContainer'
import axios from 'axios';
class Header extends Component {

    state = {
        search: '',
    }
    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }
   
    handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            window.location="/search/"+this.state.search;
          }
    }

    render() {
        return (
            <Navbar brand='meminator' className="blue-grey darken-4" style={{ padding: "0 0 20px 20px" }} right fixed>
                <li>
                    <NavItem onClick={()=>{}}>
                        <Input onKeyPress={this.handleKeyPress} type="text" className="white-text" name="search" onChange={this.onChange} icon="search" label="Search users" s={2} />
                    </NavItem>
                </li>
                {sessionStorage.getItem("username") && <Notification />}

                <NavItem href='/feed'><Icon>home</Icon></NavItem>
                {sessionStorage.getItem("username") ? <NavItem href={'/profile/' + sessionStorage.getItem("username")}><Icon>account_circle</Icon></NavItem> : null}
                {sessionStorage.getItem("token") === null ?
                    <NavItem href='/login'><Icon>lock</Icon></NavItem>
                    : <NavItem onClick={() => { sessionStorage.clear(); window.location = "/" }}><Icon>input</Icon></NavItem>}
            </Navbar>
        );
    }
}

export default Header;