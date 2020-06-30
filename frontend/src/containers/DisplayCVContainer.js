import React, {Component} from 'react';
import DisplayCVComponent from "../components/DisplayCVComponent";
import {Redirect} from 'react-router';

export default class DisplayCVContainer extends Component {
  render (){
    if (this.props.cv === '') {
      return <Redirect to="/home" />
    }

    let cvFileName = this.props.cvFileName;
    let cvData = null;
    let cv = this.props.cv;

    if (cvFileName.includes(".pdf")) {
      cvData = "data:application/pdf;base64," + cv;
      cv = '';
    } else if (cvFileName.includes(".PNG") || cvFileName.includes(".png") || cvFileName.includes('.jpg')) {
      let image = new Image();
      image.src = "data:image/png;base64, " + cv;
      document.body.append(image);
      cv = '';
    }

    return(
      <DisplayCVComponent
        cv={cv}
        cvFileName={this.props.cvFileName}
        cvData={cvData}
      />
    );
  }
}
