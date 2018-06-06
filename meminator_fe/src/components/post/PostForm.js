import React, { Component } from 'react';
import axios from 'axios';
import * as api from '../../globals';
import { Button, Modal, Row, Input, Tag, Col } from 'react-materialize';

import '../../styles/postForm.css';

class PostForm extends Component {

    state = {
        info: "",
        form: 1,
        imgID: -1,
        tags: [],
        newTag: "",
        tagElem: <div></div>,
        image: '',
        file: null
    }

    /* ------------------------------> Component producer functions <----------------------------*/

    componentWillUnmount() {
        if (this.state.imgID !== -1)
            axios.delete(api.default.url + "/imagemodule/images/meme/" + this.state.imgID);
    }

    addTag = () => {
        var tags = this.state.tags;
        tags.push(this.state.newTag);
        var tagElem = this.mapTags(tags);
        this.setState({
            tags,
            tagElem,
            newTag: ""
        });
    }

    deleteTag = (e) => {
        var tags = this.state.tags;
        var i = 0;
        for (i = 0; i < tags.length; i++) {
            if (e.target.value = tags[i]) break;
        }
        tags.splice(1, i);
        var tagElem = tags.length === 0 ? <div></div> : this.mapTags(tags);
        this.setState({
            tags,
            tagElem
        });
    }

    mapTags = (tags) => {
        var t = tags.map((t) => (
            <div className="chip" key={t}>
                {t}
                <i onClick={this.deleteTag} className="close material-icons">close</i>
            </div>));
        console.log(t);
        return (
            t
        );
    }


    /* ------------------------------------------------------------------------------------------*/


    /* ------------------------------> Request handling functions <------------------------------*/


    handleChange = (e) => {
        console.log(this.state);
        this.setState({ [e.target.name]: e.target.value });
    }

    uploadImg = () => {

        var formData = new FormData();
        formData.append('request', this.state.file, this.state.file.name)
        var config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        };

        axios(
            {
                url: api.default.url + "/imagemodule/images/upload/meme",
                method: "post",
                headers: {
                    'Authorization': 'Bearer ' + sessionStorage.getItem("token"),
                },
                data: formData
            })
            .then(this.handelUploadImg)
            .catch((error) => { console.log(error); alert("Img upload failed!") });
    }

    handelUploadImg = (resp) => {
        this.setState({
            imgID: resp.data,
            form: 0
        });
    }

    handlePost = () => {
        axios({
            url: api.default.url + "/postmodule/posts",
            method: "post",
            data: {
                "id": 0,
                "imageID": this.state.imgID,
                "imageURL": api.default.url + "/imagemodule/images/" + this.state.imgID,
                "info": this.state.info,
                "poster": "string",
                "repostID": 0,
                "tags": this.state.tags
            },
            headers: {
                "Authorization": "Bearer " + sessionStorage.getItem("token")
            }
        }
        ).then(this.handelPost)
            .catch(this.handleError);
    }

    handelPost = (resp) => {
        console.log(resp);
        this.setState({
            imgID: -1
        })
        window.location = "/post/" + resp.data.id;
    }

    handleError = (err) => {
        console.log(err);
    }

    onChange = (e) => {
        this.setState({ file: e.target.files[0] })
    }


    render() {

        var trigger = <Button onClick={() => {
            document.getElementById('new').modal('open')
        }}
            floating right large className='blue-grey darken-4' waves='light' icon='add'
            style={{ bottom: "50vh", right: "45px", float: "right", width: "100px", height: "100px", fontSize: "40px", overflow: "hidden" }}
        />;

        console.log(this.state.form);

        var up = <div className="file-field input-field" style={{ width: "100%" }}>
            <div className="btn blue-grey">
                <span>Browse</span>
                <input type="file" id="file" onChange={this.onChange} />
            </div>
            <div className="file-path-wrapper">
                <input type="text" placeholder="Upload post picture" />
            </div>
            <Button className="blue-grey" onClick={this.uploadImg} style={{ width: "100%", marginTop: "10px" }} >Next</Button>
        </div>;

        var po = <div>
            <Input type='textarea' placeholder="Description" s={12} m={12} l={12} name="info" onChange={this.handleChange} value={this.state.info} />

            {this.state.tagElem}

            <Row>
                <Input placeholder="Tag" s={9} m={9} l={9} name="newTag" onChange={this.handleChange} value={this.state.newTag} />
                <Col s={3} m={3} l={3}><Button className="blue-grey" style={{ width: "100%" }} inline onClick={this.addTag}>Add</Button></Col>
            </Row>

            <Button className="blue-grey" onClick={this.handlePost} style={{ width: "100%", marginTop: "10px" }} >Post</Button>

        </div>;

        return (
            <div>
                <Modal
                    id='new'
                    header='New post'
                    trigger={trigger}>
                    <Row s={12} m={12} l={12}>
                        {this.state.form === 1 ? up : po}
                    </Row>
                </Modal>
            </div >
        );
    }

}

export default PostForm;
