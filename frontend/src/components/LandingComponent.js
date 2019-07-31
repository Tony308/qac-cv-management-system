import React from 'react';
import {Route, Switch} from "react-router";
import LoginContainer from "../containers/LoginContainer";
import HomePageContainer from "../containers/HomePageContainer";
import CreateAccountContainer from "../containers/CreateAccountContainer";
import Navbar from '../containers/NavigationContainer';

function LandingComponent(props) {

    return (
        <div className="login">
            <Navbar/>

            <Switch>
                <Route exact={true} path="/" render={
                    () => <LoginContainer
                        login={props.login}
                        handleChange={props.handleChange}
                    />
                } />
                <Route exact={true} path="/home" render={() => <HomePageContainer
                    uploadCV={props.uploadCV}
                    getCVs={props.getCVs}
                    data={props.data}
                    deleteCV={props.deleteCV}
                />} />

                <Route exact={true} path="/create-account" render={() => <CreateAccountContainer
                    handleChange={props.handleChange}
                    createAccount={props.createAccount}
                    />}
                />
            </Switch>
        </div>
    )

}

export default LandingComponent;
