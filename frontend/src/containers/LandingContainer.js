import React, { Component } from 'react';
import axios from "axios";
import LandingComponent from "../components/LandingComponent";

export default class LandingContainer extends Component {
    constructor(props) {
        super(props);

        this.uploadCV = this.uploadCV.bind(this);
        this.getCVs = this.getCVs.bind(this);
        this.deleteCV = this.deleteCV.bind(this);
        this.login = this.login.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.createAccount = this.createAccount.bind(this);

        this.state =  {
            name: 'test',
            data: []
        };
    }

    uploadCV(e) {
        e.preventDefault();

        let file = document.getElementById("CV").files[0];

        const data = new FormData();

        data.append('file', file);
        data.append('user', this.state.name);
        data.append('fileName', file.name);

        let url = "http://localhost:8081/cvsystem/upload-cv";

        axios.post(url, data)
            .then(res => {
                console.log(res);
                this.getCVs();
            })
            .catch(err => console.log(err));
    }

    deleteCV(id) {
        let url = "http://localhost:8081/cvsystem//delete/" + id;

        axios.delete(url)
            .then(response => {
                console.log(response);
                if (response.status === 200) {
                    this.getCVs();
                }
            })
            .catch(err => console.log(err)
        );
    }

    handleChange(e) {
        this.setState({
            [e.target.name]:e.target.value
        });
        console.log(this.state.username);
        console.log(this.state.password);
    }

    login(e) {
        e.preventDefault();

        let url = "http://localhost:8081/cvsystem/login";
        let data = new FormData();

        data.append('username', this.state.username);
        data.append('password', this.state.password);

        axios.post(url, data)
            .then(res => console.log(res))
            .catch(err => console.log(err))
    }

    getCVs() {
        let url = 'http://localhost:8081/cvsystem/get';

        axios.get(url, {
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        })
            .then(res => {
                this.setState({
                    data: res.data
                });
            })
            .catch(err => {
                console.log(err);
            });

    }

    createAccount(e) {
        e.preventDefault();

        let url = "http://localhost:8081/cvsystem/create-account";

        let data = new FormData();

        data.append("username", this.state.username);
        data.append("password", this.state.password);

        axios.post(url, data)
            .then(res => console.log(res))
            .catch(err => console.log(err));

        this.setState({
            username: '',
            password: ''
        })
    }

    render() {
        return (
            <LandingComponent
                uploadCV={this.uploadCV}
                getCVs={this.getCVs}
                deleteCV={this.deleteCV}
                data={this.state.data}
                login={this.login}
                createAccount={this.createAccount}
                handleChange={this.handleChange}
            />
        );
    }
}