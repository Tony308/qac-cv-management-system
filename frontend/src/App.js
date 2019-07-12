import React, { Component } from 'react';
// import logo from './logo.svg';
import './App.css';
import {Route, Switch} from 'react-router';
import LoginContainer from "./containers/LoginContainer";
import HomePageContainer from "./containers/HomePageContainer";
import axios from 'axios';

export class App extends Component {
    constructor(props) {
        super(props);

        this.uploadCV = this.uploadCV.bind(this);
    }

    uploadCV(e) {
        let arrayBuffer = null;
        let reader = new FileReader();

        reader.onload = () => {
            arrayBuffer = reader.result;
            console.log(arrayBuffer);
        };

        let url = "http://localhost:1234/cv-upload";
        axios.post(url, {
            header: {
                allow: "*"
            },
            body: arrayBuffer
        })
    }

    render() {
        return (
            <div className="login">
                <Switch>
                    <Route exact={true} path="/" render={
                        () => <LoginContainer/>
                        } />
                    <Route exact={true} path="/home" render={() => <HomePageContainer
                        uploadCV={this.uploadCV}
                    />} />
                </Switch>
            </div>
        );
    }
}

export default App;
