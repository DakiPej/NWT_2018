import React, { Component } from 'react';
import { Collection, CollectionItem } from 'react-materialize';
import axios from 'axios';
import Row from 'react-materialize/lib/Row';
import Col from 'react-materialize/lib/Col';
import '../styles/search.css';

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
            .catch((response) => { alert("User not found"); window.location = "/feed"; });
    }

    handleSearch = (resp) => {
        this.setState({
            users: resp.data
        });
    }


    render() {

        var items = this.state.users.map((user) => <CollectionItem><a href={"/profile/" + user}>{user}</a></CollectionItem>);
        return (
            <Row style={{marginTop:"20px"}}>
                <Col s={4} m={4} l={4}/>
                <Col s={4} m={4} l={4}>
                    <Collection className="blue-grey darken">
                        {items}
                    </Collection>
                </Col>
            </Row>
        );
    }

}

export default Search;