import React, { Component } from 'react';

import LandingComponent from "../components/LandingComponent";
import {login, createAccount, getCVs, uploadCV, retrieveCV, updateCv, deleteCV} from '../_services/API';

export default class LandingContainer extends Component {
    constructor(props) {
        super(props);

        this.uploadCV = uploadCV.bind(this);
        this.getCVs = getCVs.bind(this);
        this.deleteCV = deleteCV.bind(this);
        this.login = login.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.createAccount = createAccount.bind(this);
        this.updateCv = updateCv.bind(this);
        this.retrieveCV = retrieveCV.bind(this);

        this.state =  {
            data: [],
            cvContent: '',
            username:'',
            password:''
        };
    }

    handleChange(e) {
        this.setState({
            [e.target.name]:e.target.value
        });
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
