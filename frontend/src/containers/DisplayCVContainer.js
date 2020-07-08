import React from 'react';
import DisplayCVComponent from "../components/DisplayCVComponent";
import {Redirect} from 'react-router';

export default class DisplayCVContainer extends React.Component {

    setupDocument(cvFileName, fileType) {
        cvFileName = cvFileName.toLowerCase();
        if (cvFileName.includes(".pdf")) {
            fileType = 'application/pdf';
        } else if (cvFileName.includes(".png") || cvFileName.includes('.jpg')) {
            fileType = `image/png`
        }
        return fileType;
    }

    render (){
        if (this.props.cv === '') {
            return <Redirect to="/home" />
        }
        var cvFileName = this.props.cvFileName;
        var cvData = null;
        var fileType = '';

        fileType = this.setupDocument(cvFileName, fileType);

        cvData = `data:${fileType};base64,${this.props.cv}`;
        
        return(
            <DisplayCVComponent
            cvFileName={this.props.cvFileName}
            fileType={fileType}
            cvData={cvData}
            />
        );
    }
}