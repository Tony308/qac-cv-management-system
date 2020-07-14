import React from 'react';
import { Redirect, Route, Switch, useHistory } from "react-router-dom";

import LoginContainer from "../containers/LoginContainer";
import HomePageContainer from "../containers/HomePageContainer";
import CreateAccountContainer from "../containers/CreateAccountContainer";
import DisplayCVContainer from "../containers/DisplayCVContainer";
import Navbar from '../containers/NavigationContainer';
import '../css/DefaultStyling.css';

function LandingComponent(props) {
    const history = useHistory();
    return (
        <div className="login">
            <Navbar/>
            <Switch>                
                <Route exact={true} path="/create-account" render={() => {
                    return sessionStorage.getItem('auth') === 'true' ?
                    <Redirect to='/home'/>
                    :
                    <CreateAccountContainer
                        handleChange={props.handleChange}
                        createAccount={props.createAccount}
                        errMessage={props.errMessage}
                        validationMessage={props.validationMessage}
                    />
                }}/>

                <Route exact={true} path="/home" render={() => {
                    return sessionStorage.getItem('auth') === 'true' ?
                    <HomePageContainer
                        uploadCV={props.uploadCV}
                        getCVs={props.getCVs}
                        data={props.data}
                        deleteCV={props.deleteCV}
                        updateCV={props.updateCV}
                        retrieveCV={props.retrieveCV}
                        history={history}
                    />
                    :
                    <Redirect to="/" />
                }}/>
                <Route exact={true} path="/cv" render={() =>{ 
                    return sessionStorage.getItem("auth") === 'false' ?
                    <Redirect to="/"/>
                    :
                    <DisplayCVContainer
                    cv={props.cv}
                    cvFileName={props.cvFileName}
                    history={useHistory}

                    />
                }} />
                <Route path='/' render={() => {
                    return sessionStorage.getItem('auth') === 'true' ?
                    <Redirect to={'/home'}/>
                    :
                    <LoginContainer
                        login={props.login}
                        handleChange={props.handleChange}
                        errMessage={props.errMessage}
                    />
                }}/>
                <Redirect to='/' />
            </Switch>
        </div>
    )
}

export default LandingComponent;
