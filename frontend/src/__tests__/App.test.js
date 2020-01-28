import React from 'react';
import App from '../App.js';
import LandingContainer from '../containers/LandingContainer';
import { configure, shallow } from 'enzyme';

describe('App component', function() {
    it('contains LandingContainer element', function() {
      const wrapper = shallow(
        <App />
      );
      const frontPage = (
        <LandingContainer/>
      );
      expect(wrapper.containsMatchingElement(frontPage)).toEqual(true);
    });
});

//
// describe('Check LoginComponent component', function() {
//     it('login component exists', () => {
//         const wrapper = shallow(<LoginComponent/>);
//         const loginComponents = (
//             <div>
//                 <p>Username:</p><input type="text" placeholder="Username"/>
//                 <p>Password</p><input type="password" placeholder="Password"/>
//                 <br/>
//                 <br/>
//                 <input type="submit" value="Submit"/>
//             </div>
//         );
//         expect(wrapper.contains(loginComponents)).to.equal(true);
//     });
// });
