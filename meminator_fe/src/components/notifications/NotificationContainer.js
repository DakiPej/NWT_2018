import React, { Component } from 'react' ; 
import {Row, Col} from 'react-materialize' ; 
import axios from 'axios' ; 
import Divider from 'react-materialize/lib/Divider';
import '../../styles/notifications.css' ; 
import './notifications.css' ; 
import {
    Button,
    Icon,
    NavItem, 
    Dropdown,
    Chip,
    Collection,
    CollectionItem
}   from 'react-materialize' ; 
import notifications from "./dummyNotifications";



function NotificationListItem ({notificationText}) {
    return  (
        <NavItem onClick={() => {}}className="blue-grey darken-2" >
        <div className='blue-grey-text text-lighten-5'>
            {notificationText} 
         </div>
         </NavItem>
    )
}


class Notification extends Component   {
    
    constructor(props)  {
        super(props) ;
        
        this.state = {
            notificationPageNumber: 0, 
            notifications: [],
            intervalId:undefined,
            isFetching:false, 
            lastTimeChecked : 0, 
            notificationCount : 0
        }; 
    }

    componentDidMount(){

        // fetch notifications 

        this.getLastTimeChecked() ; 
        setTimeout(() => {
            this.initialNotifications() ; 
        }, 100);
        //this.initialNotifications() ; 
        const intervalId = setInterval(this.fetchAsync,5000);
        this.setState(()=>({/*notifications, */intervalId}));

    }

    initialNotifications = () => {
        const pageNumber = this.state.notificationPageNumber ; 
        const lastTimeChecked = this.state.lastTimeChecked ;  
        axios.get("http://localhost:8081/notifications/dakipej/" + pageNumber + "/" + lastTimeChecked)
        .then(this.handleNewRequests)
        .catch(this.catchNewRequests) ; 
    }

    handleInitialNotifications = (response) => {

    }

    catchInitialNotifications = (error) => {
        
    }

    getLastTimeChecked = () => {
        axios.get("http://localhost:8081/users/lastTimeChecked/dakipej")
        .then(this.handleGetLastTimeChecked.bind(this))
        .catch(function(err)    {
            console.log(err);
            const isFetching = false ; 
            this.setState( () => ({isFetching})) ;  
        }) ; 

        const isFetching = true ;
        this.setState( () => {isFetching}) ;  
    }

    handleGetLastTimeChecked = (response) => {
        const lastTimeChecked = response.data ; 
        const isFetching = false ; 

        this.setState(() => ({lastTimeChecked, isFetching})) ; 
        //console.log("ZADNJI PUT -----    " + lastTimeChecked) ; 
    }

    componentWillUnmount(){
        clearInterval(this.state.intervalId);
        this.setState({intervalId:undefined})
    }

    fetchAsync = () => {
        const pageNumber = this.state.notificationPageNumber ;  
        const lastTimeChecked = this.state.lastTimeChecked ; 

        if(!this.state.isFetching)
            axios.get('http://localhost:8081/notifications/dakipej/' + pageNumber + "/" + Date.now())
            .then(this.handleNewRequests.bind(this))
            .catch(this.catchNewRequests.bind(this)) ; 
        // if(!this.state.isFetching){
        //     this.setState({isFetching:true})
        //     setTimeout(()=>{
        //         console.log('fetchali smo');
        //         this.setState({isFetching:false})
        //     },2000)
        // }
        
    }

    handleNewRequests = (response)  =>     {
        const isFetching = false ; 
        const notifications = response.data.notifications ; 
        console.log("RESPONSE JE ----- " + response) ; 
        const notificationCount = response.data.notificationCount; 

        this.setState(
            (prevState) => (
                    {
                        notifications: [...notifications, ...prevState.notifications],
                        notificationCount: notificationCount + prevState.notificationCount
                    }
                )
            )  
        console.log("NOVE NOTIFIKACIJE SU: " + notifications) ; 

        console.log("NOTIFIKACIJE U STATEU SU : " + this.state.notifications[0].notificationText) ; 
        //console.log(response.data) ;        
        //console.log('Proslo'); 
    }

    catchNewRequests = (error)  => {
        this.setState(
            () => ({isFetching:false})
        )
        //console.log("--------------GRESKA--------------") ;
        console.log(error.data) ; 
    }

    render() {

        const dropDownButton =  (
            <div class='notificationDiv'>
            <NavItem>
                <a className="blue-grey waves-effect waves-light btn" onClick={() => {}}>
                    <i class="material-icons left">notifications</i>
                    {this.state.notifications.length}
                </a>
            </NavItem>
            </div>
        );
        const loadMoreButton = (
            <NavItem onClick={() => {}}className="blue-grey darken-2" >
                <div className='blue-grey-text text-lighten-5'>
                    <i className='material-icons center'>add</i> 
                </div>
             </NavItem>
        );
        
        return (
            <div className='dropDownDiv'>
                <Dropdown trigger={dropDownButton} >
                        {
                        this.state.notificationCount &&

                        this.state.notifications.map((notif,index)=>(
                            <NotificationListItem {...notif} />
                        ))
                    }
                    {loadMoreButton}
                </Dropdown>
            </div>
            );
    }

}

export default Notification ; 