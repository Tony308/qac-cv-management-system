import React, {Component} from 'react';
import LoginComponent from "../components/LoginComponent.js";
import {Redirect} from "react-router";


class loginContainer extends Component {

    render() {
        if (localStorage.getItem("auth")) {
            return <Redirect to="/home"/>
        }

        return(<LoginComponent
            login={this.props.login}
            handleChange={this.props.handleChange}
        />);
    }
}

export default loginContainer;