import React, { Component } from 'react';
import axios from "axios";
import LandingComponent from "../components/LandingComponent";
import Cookies from 'js-cookie';
import API from '../_services/API';

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
        this.setCookie = this.setCookie.bind(this);
        this.getCookie = this.getCookie.bind(this);

        this.state =  {
            data: [],
            cvContent: '',
            username:'',
            password:''
        };
    }

    getCookie(cookieName) {
      var token = Cookies.get(cookieName);
      console.log(token);
    }

    setCookie(cookieName, cookieValue) {
      Cookies.set(cookieName, cookieValue, {
        expires: 1,
        secure: true,
        httpOnly: true,
      });
    }

    createAccount(e) {
        e.preventDefault();
        const url = `http://localhost:8081/cvsystem/create-account`;

        let data = new FormData();
        data.append("username", this.state.username);
        data.append("password", this.state.password);

        axios.post(url, data)
        .then(res => {
          console.log(res);
          this.setState({
              password: null
          });
          window.location.href= "/home";
        })
        .catch(err => {
          this.handleHttpError(err);
        });
    }

    login(e) {
        e.preventDefault();
        let url = "http://localhost:8081/cvsystem/login";
        let data = new FormData();

        data.append('username', this.state.username);
        data.append("password", this.state.password);

        localStorage.setItem("username", this.state.username);;

        axios.post(url, data)
        .then(res => {
          if (res.status === 202) {
            this.setState({
              password: ""
            });
            console.log(res);
            this.setCookie('authToken', res.data.token);
            sessionStorage.setItem("auth", 'true');
          }
          window.location.href = '/home';
        })
        .catch(err => this.handleHttpError(err));
    } //End

    getCVs() {
      let resource = `/get?name=${localStorage.getItem("username")}`;

      API.get(resource)
      .then(res => {
        this.setState({
            data: res.data
        });
      })
      .catch(err => this.handleHttpError(err));
    } //End

    uploadCV(e) {
        e.preventDefault();

        let file = document.getElementById("CV").files[0];

        if (!file) {
            console.log('No file');
            return;
        } else if (!localStorage.getItem("username")) {
          //Is only ever set in login hence, requries re-login
          sessionStorage.setItem('auth', 'false');
          window.location.href="/"
          return;
        } else if (!file.name) {
          console.log('File name cannot be null');
          return;
        }

        const data = new FormData();

        data.append('file', file);
        data.append('user', localStorage.getItem("username"));
        data.append('fileName', file.name);

        let url = '/upload-cv';

        API.post(url, data)
            .then(res => {
                console.log(res);
                this.getCVs();
            })
            .catch(err => this.handleHttpError(err));
    } //End

    updateCv(id) {
        let url = `/update-cv/${id}`;
        let data = new FormData();

        let file = document.getElementById("CV").files[0];
        if (!file || !localStorage.getItem("username") || !file.name) {
            console.log('err');
            return "err, not enough details.";
        }

        data.append("id", id);
        data.append("file", file);
        data.append("fileName", file.name);

        API.put(url, data)
            .then(res => {
                console.log(res);
                this.getCVs();
            })
            .catch(err => this.handleHttpError(err));
    } //End


    deleteCV(id) {
        let resource = `/delete/${id}`;

        API.delete(resource)
        .then(response => {
          console.log(response);
          if (response.status === 200) {
              this.getCVs();
          }
        })
        .catch(err => this.handleHttpError(err));
    } //End

    handleChange(e) {
        this.setState({
            [e.target.name]:e.target.value
        });
    }

    retrieveCV(id) {
      let url = `/retrieve/${id}`;

      API.get(url)
          .then(res => {
            this.setState({
              cvContent: res.data.cvFile.data,
              cvFileName: res.data.fileName
            });
          })
          .catch(err => this.handleHttpError(err));
    }

    handleHttpError(err) {
      if (err.response) {
          console.log(err.response.data);
          console.log(err.response.status);
          console.log(err.response.headers);
          this.setState({
            errMessage: err.response.data
          });
        } else if (err.request) {
          console.log(err.request);
        } else {
          console.log('err: ', err.message);
        }
        console.log(err.config);
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
            errMessage={this.state.errMessage}
            validationMessage={this.state.validationMessage}
        />
      );
    }
}
