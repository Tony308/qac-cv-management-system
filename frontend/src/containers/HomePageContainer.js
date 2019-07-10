import React, {Component} from 'react';
import HomePageComponent from "../components/HomePageComponent";

export default class HomePageContainer extends Component {
    render() {
        return(
            <HomePageComponent uploadCV={this.props.uploadCV}/>
        );
    }
}