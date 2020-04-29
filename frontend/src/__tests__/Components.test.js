import React from 'react';
import LoginContainer from '../containers/LoginContainer.js';
import LoginComponent from '../components/LoginComponent';
import HomepageComponent from '../components/HomepageComponent.js';
import LandingContainer from '../containers/LandingContainer.js';
import LandingComponent from '../components/LandingComponent.js';


import { configure, shallow } from 'enzyme';


describe("Check LoginComponent", function()  {
  it("loads login page elements", () => {
    const wrapper = shallow(<LoginComponent/>);
    const loginPage = (
      <div>
          <h1> Login </h1>
          <form method="POST" name="login-form"  >
              <p>Username:</p><input type="text" placeholder="Username" name="username" />
              <p>Password</p><input type="password" placeholder="Password" name="password"/>
              <br />
              <br />
              <input type="submit" value="Login"/>
          </form>
      </div>
    );
    expect(wrapper).toContainReact(loginPage);
  });
});

describe('Check components', () => {

  it('Check Homepage smoke test', () => {
    const wrapper = shallow(<HomepageComponent/>);
    const homepage = (
      <input id="CV" type="file" placeholder="Upload here" alt = "upload-file"/>
    );

    expect(wrapper).toContainReact(homepage);
    expect(wrapper).toContainReact(<input type="submit" value="Submit" />
    );

  });

  describe('passes props', () => {
    it('to landingcomponent', () => {
      const container = shallow(<LandingContainer/>);
      const component = mount(<LandingComponent/>);
      component.find("Something").forEach(item => {

      });

    })
  });









});
