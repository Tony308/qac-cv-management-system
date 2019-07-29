import React, {Component} from 'react';
import LoginComponent from "../components/LoginComponent.js";

class loginContainer extends Component {

    render() {
        return(<LoginComponent
            login={this.props.login}
            handleChange={this.props.handleChange}
        />);
    }
}

export default loginContainer;