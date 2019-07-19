import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import {Route, Switch} from 'react-router';
import LoginContainer from "./containers/LoginContainer";
import HomePageContainer from "./containers/HomePageContainer";
import axios from 'axios';

export class App extends Component {
    constructor(props) {
        super(props);

        this.uploadCV = this.uploadCV.bind(this);
        this.getCVs = this.getCVs.bind(this);

        this.state =  {
            name: 'test'
        };
    }

    uploadCV(e) {
        e.preventDefault();

        let arrayBuffer = null;
        let reader = new FileReader();

        reader.onload = () => {
            arrayBuffer = reader.result;
            console.log("Array buffer." + arrayBuffer);
        };

        let file = document.getElementById("CV").files[0];

        const data = new FormData();

        data.append('file', file);
        data.append('name', this.state.name);

        // console.log(file);

        let url = "http://localhost:8081/cvsystem/upload-cv";
        axios.post(url, data)
            .then(response => console.log(response))
            .catch(err => console.log(err));

    }

    getCVs(e) {
        e.preventDefault();

        let url = 'http://localhost:8081/cvsystem/get';
        axios.get(url, {
            headers: {
                'Access-Control-Allow-Origin': '*'
            }
        })

            .then(res => {
                for (let x = 0; x < res.data.length; x++) {
                    console.log(res.data[x]);
                    this.setState({
                        data: res.data
                    });
                    // document.getElementById("output").value = ;
                }
            })
            .catch(err => {
                console.log(err);
            });
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
                        getCVs={this.getCVs}
                    />} />
                </Switch>
            </div>
        );
    }
}

export default App;
