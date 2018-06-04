import React, { Component } from 'react';
import axios from 'axios';
import SinglePost from '../components/post/SinglePost';
import * as api from '../globals';

class SinglePostDetails extends Component{

    state = {
        post:{},
        component: <div></div>
    }

    componentWillMount(){
        console.log("--------------------------------------------------tu------------------------------");
        this.getPost();
    }

    getPost = () => {
        axios.get(api.default.url+'postmodule/posts/'+this.props.match.params.id)
        .then(this.handlePost)
        .catch((err) => {console.log(err);});
    }

    handlePost = (res) =>{
        this.setState({post : res.data});
        this.setState({component : <SinglePost post={this.state.post} />});
    }

    render(){
        return(
            <div>
            {this.state.component}
            </div>
        );
    }

}

export default SinglePostDetails;