import React, {Component} from 'react';
import DisplayCVComponent from "../components/DisplayCVComponent";
import {Redirect} from 'react-router';

export default class DisplayCVContainer extends Component {
  constructor(props){
    super(props);
  }

  render (){
    if (sessionStorage.getItem("auth") === "false") {
    } else if (this.props.cv === '') {
      return <Redirect to="/home" />
    }


    return(
      <DisplayCVComponent
        cv={this.props.cv}
        cvFileName={this.props.cvFileName}
      />
    );
  }
}
