import React, { Component } from 'react';
import axios from 'axios';
import SinglePost from '../components/post/SinglePost';
import * as api from '../globals';
import Comments from '../components/post/Comments';
import { Row, Col } from 'react-materialize';

class SinglePostDetails extends Component {

    state = {
        post: {},
        component: <div></div>
    }

    componentWillMount() {
        this.getPost();
    }

    getPost = () => {
        axios.get(api.default.url + '/postmodule/posts/' + this.props.match.params.id)
            .then(this.handlePost)
            .catch((err) => { console.log(err); });
    }

    handlePost = (res) => {
        console.log(res);
        this.setState({ post: res.data });
        this.setState({ component: <SinglePost post={this.state.post} single={"T"} /> });
    }

    render() {
        return (
            <div className="post-details">
                <Row style={{ width: "80%", height: "auto", padding:"20px", marginTop: "20px", background: "rgba(38, 50, 56, 0.8)" }}>
                    <Col s={6} m={6} l={6}>
                        {this.state.component}
                    </Col>
                    <Col s={6} m={6} l={6}>
                        <Comments />
                    </Col>
                </Row>
            </div>
        );
    }

}

export default SinglePostDetails;
