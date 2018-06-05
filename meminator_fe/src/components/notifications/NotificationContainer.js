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
            notifications: [{
                notificationId:"-1", 
                notificationText:"The user neko started following you.", 
                notificationType:"Followed", 
                referencedObjectId:"-1" 

            }, 
            {
                notificationId:"-12", 
                notificationText:"The user neko reposted your post.", 
                notificationType:"Post repost", 
                referencedObjectId:"-13" 

            }],
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

    reRoute = (notificationText, objectId, notificationType) => {
        const authorization = "Bearer " + sessionStorage.getItem("token") ;
        console.log("!!!!!!!!!!!!!!!!!!!!!!!           OBJECT ID JE : " + objectId) ; 
        // console.log("!!!!!!!!!!!!!!!!!!!!!!         TEKST JE : " + notificationText + "         !!!!!!!!!!!") ;
        // console.log("!!!!!!!!!!!!!!!!!!!!!!         OBJECT ID JE : " + objectId + "       !!!!!!!!!!!!!!!!!") ;
        if(notificationType == "Commented")  {
             console.log("COMMENTED ! ") ; 
            axios.get("http://138.68.186.248:8080/interactionmodule/comments/postId/" + objectId, 
            {
                headers: {Authorization: authorization}
            })
            .then(this.handleGetPostId) 
            .catch(this.handleErrorGetPostId) ;
         }
        else if(notificationType == "Post vote")   {
             console.log("POST VOTE ! ") ;
             axios.get("http://138.68.186.248:8080/interactionmodule/postVotes/postId/" + objectId,
            {
                headers: {Authorization: authorization}
            })
            .then(this.handleGetPostId)
            .catch(this.handleErrorGetPostId) ;
        }
        else if(notificationType == "Comment vote")    {
            console.log("COMMENT VOTE !") ; 
            axios.get("http://138.68.186.248:8080/interactionmodule/commentVotes/postId/" + objectId, 
        {
            headers: {Authorization: authorization}
        })
        .then(this.handleGetPostId)
        .catch(this.handleErrorGetPostId) ;
        }
        else if(notificationType == "Followed")    {
            const username = notificationText.replace("The user ", "").replace(" started following you.", "") ; 
            console.log("USERNAME JE : " + username) ; 
            //PREUSMJERITI NA PROFIL ONOGA KO JE ZAPRATIO
            console.log("FOLLOW ! ") ; 
        }
        else if(notificationType == "Post repost")   {
            console.log("REPOST ! ") ;  
            //PREUSMJERITI NA TAJ POST ILI REPOST ?????
        }
    }
    handleGetPostId = (response) => {
        console.log("EVO GA RESPONSE !!!!!!!!!!!!!!!!!!!!!!!") ; 
        console.log("DATA JE : " + response.data) ; 
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

    componentWillUnmount() {
        clearInterval(this.state.intervalId);
        this.setState({ intervalId: undefined })
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
        // if(!this.state.isFetching){
        //     this.setState({isFetching:true})
        //     setTimeout(()=>{
        //         console.log('fetchali smo');
        //         this.setState({isFetching:false})
        //     },2000)
        // }

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
        // console.log("NOVE NOTIFIKACIJE SU: " + notifications) ;

        // console.log("NOTIFIKACIJE U STATEU SU : " + this.state.notifications[0].notificationText) ;
        // console.log(response.data) ;
        // console.log('Proslo');
    }

    catchNewRequests = (error) => {
        this.setState(
            () => ({ isFetching: false })
        )
        console.log(error.data);
    }

    render() {

        const dropDownButton = (
            <div className='dropDownButt'>
                <a className="blue-grey waves-effect waves-light btn right" onClick={() => { }}>
                    <i class="material-icons left">notifications</i>
                    {this.state.notifications.length}
                </a>
            </div>
        );
        const loadMoreButton = (
            <NavItem onClick={() => { }} className="blue-grey darken-2" >
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
                            this.state.notificationCount &&

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
