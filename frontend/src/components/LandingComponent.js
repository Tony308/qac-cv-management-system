import React from 'react';
import {Route, Switch} from "react-router";
import LoginContainer from "../containers/LoginContainer";
import HomePageContainer from "../containers/HomePageContainer";


function LandingComponent(props) {

    return (
        <div className="login">
            <Switch>
                <Route exact={true} path="/" render={
                    () => <LoginContainer/>
                } />
                <Route exact={true} path="/home" render={() => <HomePageContainer
                    uploadCV={props.uploadCV}
                    getCVs={props.getCVs}
                    data={props.data}
                    deleteCV={props.deleteCV}

                />} />
            </Switch>
        </div>
    )

}

export default LandingComponent;