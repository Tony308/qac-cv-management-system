import React from 'react';
import App from './App';

import { configure, shallow } from 'enzyme';
import { expect } from 'chai';
import Adapter from 'enzyme-adapter-react-16'

import {LoginComponent} from './components/LoginComponent.js';
import {NavigationContainer} from "./containers/NavigationContainer";
import loginAuthen from "./containers/LoginContainer";



configure({ adapter: new Adapter() });

describe('Check main App.js loads', function() {
    it('login component renders in App.js', function() {
        const wrapper = shallow(<App />);
        const frontPage = (
            <div className="login">
            <NavigationContainer/>
            <LoginComponent />
            </div>
        );
        expect(wrapper.contains(frontPage)).to.equal(true);
    });
});

describe('Check LoginComponent component', function() {
    it('login component exists', () => {
        const wrapper = shallow(<LoginComponent/>);
        const loginComponents = (
            <div>
                <p>Username:</p><input type="text" placeholder="Username"/>
                <p>Password</p><input type="password" placeholder="Password"/>
                <br/>
                <br/>
                <input type="submit" value="Submit"/>
            </div>
        );
        expect(wrapper.contains(loginComponents)).to.equal(true);
    });
});

describe('REST test', () => {
    it ('LoginComponent function', () => {
      
    });
});
