import React, {Component} from 'react';
import CreateAccountComponent from '../components/CreateAccountComponent.js';

class CreateAccountContainer extends Component {

    confirmPassword(e) {
        let password = e.target.value;
        let confirm = document.getElementById("password").value;

        console.log(e.target.value);
        console.log(confirm);

        if (password !== confirm ) {
            console.log("doesn't match")
        }
    }


    render() {
        return (<CreateAccountComponent
            createAccount={this.props.createAccount}
            handleChange={this.props.handleChange}
            confirmPassword={this.confirmPassword}
        />)
    }
}

export default CreateAccountContainer;