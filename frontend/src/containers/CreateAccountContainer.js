import React, {Component} from 'react';
import CreateAccountComponent from '../components/CreateAccountComponent.js';

class CreateAccountContainer extends Component {
  constructor(props) {
    super(props);
    this.confirmPassword = this.confirmPassword.bind(this);
    this.state = {
      passwordMismatchMessage: false
    }
  };

  confirmPassword(e) {
    let password = document.getElementById("password").value;
    let confirm = e.target.value;

    if (password !== confirm ) {
      this.setState({
        passwordMismatchMessage: true
      })
    } else {
      this.setState({
        passwordMismatchMessage: false
      });
    }
  }

    render() {
        return (<CreateAccountComponent
            createAccount={this.props.createAccount}
            handleChange={this.props.handleChange}
            confirmPassword={this.confirmPassword}
            passwordMismatchMessage={this.state.passwordMismatchMessage}
            errMessage={this.props.errMessage}
            validationMessage={this.props.validationMessage}
        />)
    }
}

export default CreateAccountContainer;
