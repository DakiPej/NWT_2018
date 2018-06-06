import React, { Component } from 'react';
import { Row, Col } from 'react-materialize';
import axios from 'axios';
import Divider from 'react-materialize/lib/Divider';
import '../../styles/notifications.css';
import './notifications.css';
import {
    Button,
    Icon,
    NavItem,
    Dropdown,
    Chip,
    Collection,
    CollectionItem
} from 'react-materialize';
import notifications from "./dummyNotifications";



function NotificationListItem({ notificationText, referencedObjectId, notificationType, reRoute }) {
    return (
        // <NavItem onClick={(notificationText, referencedObjectId) => {
        //     console.log("Tekst je --------- " + notificationText)
        // }}className="blue-grey darken-2" >
        <div className='notification'>
            <div className='blue-grey darken-2 blue-grey-text text-lighten-5' onClick={() => reRoute(notificationText, referencedObjectId, notificationType)}>
                {notificationText}
            </div>
        </div>
        //</NavItem>

    )
}


class Notification extends Component {

    constructor(props) {
        super(props);

        var token = sessionStorage.getItem("token");
        var username = sessionStorage.getItem("username");

        this.state = {
            notificationPageNumber: 0,
            notifications: [],
                intervalId: undefined,
                isFetching: false,
                lastTimeChecked: 0,
                notificationCount: 0
            };
        }
        
        componentDidMount() {
            
            // fetch notifications
            
            this.getLastTimeChecked();
            setTimeout(() => {
                this.initialNotifications();
            }, 100);
            //this.initialNotifications() ;
            const intervalId = setInterval(this.fetchAsync, 60000);
            this.setState(() => ({/*notifications, */intervalId }));
            
        }
        componentWillUnmount() {
            this.updateLastTimeChecked() ; 
            clearInterval(this.state.intervalId);
            this.setState({ intervalId: undefined }) ; 
        }
        
    reRoute = (notificationText, objectId, notificationType) => {
        const authorization = "Bearer " + sessionStorage.getItem("token") ;
        if(notificationType == "Commented")  {
            axios.get("http://138.68.186.248:8080/interactionmodule/comments/postId/" + objectId, 
            {
                headers: {Authorization: authorization}
            })
            .then(this.handleGetPostId) 
            .catch(this.handleErrorGetPostId) ;
         }
        else if(notificationType == "Post vote")   {
             axios.get("http://138.68.186.248:8080/interactionmodule/postVotes/postId/" + objectId,
            {
                headers: {Authorization: authorization}
            })
            .then(this.handleGetPostId)
            .catch(this.handleErrorGetPostId) ;
        }
        else if(notificationType == "Comment vote")    { 
            axios.get("http://138.68.186.248:8080/interactionmodule/commentVotes/postId/" + objectId, 
        {
            headers: {Authorization: authorization}
        })
        .then(this.handleGetPostId)
        .catch(this.handleErrorGetPostId) ;
        }
        else if(notificationType == "Followed")    {
            const username = notificationText.replace("The user ", "").replace(" started following you.", "") ; 
            window.location='/profile/' + username ; 
        }
        else if(notificationType == "Post repost")   {
            
        }
    }
    handleGetPostId = (response) => {
        window.location='/post/' + response.data
    }
    handleErrorGetPostId = (error) => {
        console.log("EVO GA ERROR !!!!!!!!!!!!!!!!!!!!!!!!!!") ; 
        console.log(error) ; 
    }



    initialNotifications = () => {
        //console.log(sessionStorage.getItem("token")) ;
        const pageNumber = this.state.notificationPageNumber;
        const lastTimeChecked = this.state.lastTimeChecked;
        const authorization = "Bearer " + sessionStorage.getItem("token");
        axios.get("http://138.68.186.248:8080/interactionmodule/notifications/" + pageNumber + "/" + lastTimeChecked
            , { headers: { Authorization: authorization } })
            .then(this.handleNewRequests)
            .catch(this.catchNewRequests);
    }

    handleInitialNotifications = (response) => {

    }

    catchInitialNotifications = (error) => {

    }

    getLastTimeChecked = () => {
        //console.log(sessionStorage.getItem("token")) ;
        const authorization = "Bearer " + sessionStorage.getItem("token");
        axios.get("http://138.68.186.248:8080/interactionmodule/notifications/lastTimeChecked/"
            , { headers: { Authorization: authorization } })
            .then(this.handleGetLastTimeChecked.bind(this))
            .catch(function (err) {
                console.log(err);
                const isFetching = false;
//                this.setState(() => ({ isFetching }));
            });

        const isFetching = true;
        this.setState(() => { isFetching });
    }

    handleGetLastTimeChecked = (response) => {

        //console.log(response.data) ;
        const lastTimeChecked = response.data;
        const isFetching = false;

        this.setState(() => ({ lastTimeChecked, isFetching }));
        //console.log("ZADNJI PUT -----    " + lastTimeChecked) ;
    }
    
    updateNotificationCount = () => {
        console.log("UPDATE SE DESAVA...") ; 
        var notificationCount  = 0 ; 
        const lastTimeChecked = Date.now() ; 
        this.setState({notificationCount:0, lastTimeChecked: Date.now()}) ;
    }

    updateLastTimeChecked = () => {
        const authorization = 'Bearer ' + sessionStorage.getItem("token") ;
        const lastTimeCheckedValue = Date.now() ; 
        const body = {lastTimeChecked: Date.now()} ;
        const notificationCount = 0 ; 
        console.log(body) ; 
        console.log(notificationCount) ; 
        
        axios({
            url: 'http://138.68.186.248:8080/interactionmodule/users/lastTimeChecked',
            method: 'put', 
            headers: {
                Authorization: authorization, 
                'Content-Type': 'application/json'
            }, 
            data: body
            }).then(this.handleUpdateLastTimeChecked).catch(this.handleErrorUpdateLastTimeChecked) ; 
            this.setState(() => {notificationCount})
    }
    handleUpdateLastTimeChecked = (response) => {
        console.log("USPJEH !") ; 
        console.log(response.data) ; 
    }

    handleErrorUpdateLastTimeChecked = (error) => {
        console.log("NEUSPJEH") ; 
        console.log(error) ; 
    }

    fetchAsync = () => {
        //console.log(sessionStorage.getItem("token")) ;
        const pageNumber = this.state.notificationPageNumber;
        const lastTimeChecked = this.state.lastTimeChecked;
        const authorization = "Bearer " + sessionStorage.getItem("token");

        if (!this.state.isFetching)
            axios.get('http://138.68.186.248:8080/interactionmodule/notifications/' + pageNumber + "/" + Date.now()
                , { headers: { Authorization: authorization } })
                .then(this.handleNewRequests.bind(this))
                .catch(this.catchNewRequests.bind(this));

    }

    handleNewRequests = (response) => {
        const isFetching = false;
        const notifications = response.data.notifications;
        console.log(response);
        const notificationCount = response.data.notificationCount;
        
        this.setState(
            (prevState) => (    
                {
                    notifications: [...notifications, ...prevState.notifications],
                    notificationCount: notificationCount + prevState.notificationCount
                }
            )
        )
    }
    catchNewRequests = (error) => {
        this.setState(
            () => ({ isFetching: false })
        )
        console.log(error.data);
    }
    
    loadMoreNotifications = () => {
        const notificationPageNumber = this.state.notificationPageNumber + 1 ;
        const lastTimeChecked = this.state.lastTimeChecked ;
        const authorization = 'Bearer ' + sessionStorage.getItem("token") ; 
        axios.get("http://138.68.186.248:8080/interactionmodule/notifications/" + notificationPageNumber + "/" + lastTimeChecked
        , {headers : {Authorization: authorization}})
        .then(this.handleMoreRequests)
        .catch(this.catchMoreRequests) ;
        this.setState(()=>{notificationPageNumber}) ;  
    }
    handleMoreRequests = (response) => {
        console.log("DOBAVIO SAM TI JOS JEBEM TI SVE ") ; 
        const notifications = response.data.notifications ; 
        this.setState(
            (prevState) => ({notifications: [...prevState.notifications, ...notifications]}) 
        ) ; 
    }
    catchMoreRequests = (error) => {
        console.log("NE RADI !!!!!!!!!!!!!!!!!!!!") ; 
        console.log(error) ; 
    }
    
    render() {
        
        const dropDownButton = (
            <div className='dropDownButt'>
                <a className="blue-grey waves-effect waves-light btn right" onClick={this.updateNotificationCount}>
                    <i class="material-icons left">notifications</i>
                    {this.state.notificationCount}
                </a>
            </div>
        );
        const loadMoreButton = (
            <NavItem onClick={this.loadMoreNotifications} className="blue-grey darken-2" >
                <div className='blue-grey-text text-lighten-5'>
                    <i className='material-icons center'>add</i>
                </div>
            </NavItem>
        );

        return (
            <NavItem onClick={() => { }}>
                <div className='dropDownDiv'>
                    <Dropdown trigger={dropDownButton} >
                        {
                            this.state.notifications.length &&

                            this.state.notifications.map((notif, index) => (
                                <NotificationListItem {...notif} reRoute={this.reRoute} />
                            ))
                        }
                        {loadMoreButton}
                    </Dropdown>
                </div>
            </NavItem>
        );
    }

}

export default Notification;
