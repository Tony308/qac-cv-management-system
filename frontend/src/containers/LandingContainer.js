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
        this.updateCv = this.updateCv.bind(this);
        this.retrieveCV = this.retrieveCV.bind(this);

        this.state =  {
            data: [],
            cvContent: ''
        }
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
            password: null
        })
    }

    uploadCV(e) {
        e.preventDefault();

        let file = document.getElementById("CV").files[0];
        if (!file || !localStorage.getItem("username") || !file.name) {
            console.log('error');
            return "Error, not enough details.";
        }
        const data = new FormData();

        data.append('file', file);
        data.append('user', localStorage.getItem("username"));
        data.append('fileName', file.name);

        let url = "http://localhost:8081/cvsystem/upload-cv";

        axios.post(url, data)
            .then(res => {
                console.log(res);
                this.getCVs();
            })
            .catch(err => console.log(err));
    }

    updateCv(id) {
        let url = "http://localhost:8081/cvsystem/update-cv/" + id;
        let data = new FormData();

        let file = document.getElementById("CV").files[0];
        if (!file || !localStorage.getItem("username") || !file.name) {
            console.log('error');
            return "Error, not enough details.";
        }

        data.append("id", id,);
        data.append("file", file);
        data.append("fileName", file.name);
        axios.put(url, data)
            .then(res => {
                console.log(res);
                this.getCVs();
            })
            .catch(err => console.log(err));
    }


    deleteCV(id) {
        let url = "http://localhost:8081/cvsystem/delete/" + id;

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
    }

    login(e) {
        e.preventDefault();

        let url = "http://localhost:8081/cvsystem/login";
        let data = new FormData();

        data.append('username', this.state.username);
        data.append('password', this.state.password);

        localStorage.setItem("username", this.state.username);
        localStorage.getItem("username");

        axios.post(url, data)
            .then(res => {
                console.log(res);
                if (res.status === 202) {
                    sessionStorage.setItem("auth", 'true');
                }
                window.location.href = '/home';
            })
            .catch(err => {
                console.log(err);
            })

    }

    getCVs() {
        let url = "http://localhost:8081/cvsystem/get?name=" + localStorage.getItem("username");

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

    retrieveCV(id) {
        let url = 'http://localhost:8081/cvsystem/retrieve/' + id;

        axios.get(url)
            .then(res => {
              this.setState({
                cvContent: atob(res.data.cvFile.data),
                cvFileName: res.data.fileName
              });
            })
            .catch(err => console.log(err));
            
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
                updateCV={this.updateCv}
                retrieveCV={this.retrieveCV}
                cv={this.state.cvContent}
                cvFileName={this.state.cvFileName}
            />
        );
    }
}
