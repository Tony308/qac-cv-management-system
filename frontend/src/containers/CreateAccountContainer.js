import React, {Component} from 'react';
import CreateAccountComponent from '../components/CreateAccountComponent.js';

class CreateAccountContainer extends  Component {

    render() {
        return (<CreateAccountComponent
            createAccount={this.props.createAccount}
            handleChange={this.props.handleChange}
        />)
    }
};

export default CreateAccountContainer;