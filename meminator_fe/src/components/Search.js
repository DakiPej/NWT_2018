import React, { Component } from 'react';
import { Collection, CollectionItem } from 'react-materialize';
import axios from 'axios';

class Search extends Component {

    state = {
        users: []
    }

    componentDidMount() {
        this.searchUser();
    }

    searchUser = () => {
        axios.get('http://138.68.186.248:8080/usermodule/users/search/' + this.props.match.params.input)
            .then(this.handleSearch)
            .catch((response) => { alert("User not found"); window.location="/feed"; });
    }

    handleSearch = (resp) => {
        this.setState({
            users:resp.data
        });
    }


    render() {

    var items = this.state.users.map((user) =>  <CollectionItem><a href={"/profile/"+user}>{user}</a></CollectionItem>);
        return (
            <Collection style={{width:"80%", marginLeft:"20%", background:"rgba(38, 50, 56,0.8)", color:"white"}}>
                {items}
            </Collection>
        );
    }

}

export default Search;