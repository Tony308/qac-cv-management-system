import React from 'react';
import {Redirect, Route, Switch} from "react-router-dom";

import LoginContainer from "../containers/LoginContainer";
import HomePageContainer from "../containers/HomePageContainer";
import CreateAccountContainer from "../containers/CreateAccountContainer";
import DisplayCVContainer from "../containers/DisplayCVContainer";
import Navbar from '../containers/NavigationContainer';
import '../css/DefaultStyling.css';

function LandingComponent(props) {

    return (
        <div className="login">
            <Navbar/>

            <Switch>
                <Route exact={true} path="/" render={
                    () => {
                      if (sessionStorage.getItem("auth") === 'true') {
                          return (<Redirect to="/home"/>)
                      }
                          return <LoginContainer
                          login={props.login}
                          handleChange={props.handleChange}
                          errMessage={props.errMessage}
                      />
                    }
                } />
                <Route exact={true} path="/home" render={() => {
                    if (sessionStorage.getItem("auth") === 'true') {
                        return <HomePageContainer
                            uploadCV={props.uploadCV}
                            getCVs={props.getCVs}
                            data={props.data}
                            deleteCV={props.deleteCV}
                            updateCV={props.updateCV}
                            retrieveCV={props.retrieveCV}
                            cv={props.cv}
                        />
                    }
                        return <Redirect to="/" />
                }} />

                <Route exact={true} path="/create-account" render={() => {
                        if (sessionStorage.getItem("auth") === 'true') {
                            return (<Redirect to="/home" />)
                        }
                        return <CreateAccountContainer
                            handleChange={props.handleChange}
                            createAccount={props.createAccount}
                            errMessage={props.errMessage}
                            validationMessage={props.validationMessage}
                        />
                    }
                }
                />
                <Route exact={true} path="/cv" render={() => {
                        if (sessionStorage.getItem("auth") === 'false') {
                            return (<Redirect to="/"/>)
                        }
                        return <DisplayCVContainer
                          cv = {props.cv}
                          cvFileName={props.cvFileName}
                        />
                    }
                }
                />
            </Switch>
        </div>
    )
}

export default LandingComponent;
