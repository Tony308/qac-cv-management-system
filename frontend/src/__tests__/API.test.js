import React from 'react';
import App from '../App.js';
import LandingContainer from '../containers/LandingContainer';
import { configure, shallow } from 'enzyme';

jest.mock("../__mocks__/LandingContainer");

describe('API test', function() {
  it( 'login works.', () => {
      const login = jest.fn( () => {

      let result = Promise.resolve({
        data: "Login Successful",
        status: 202
      });
      console.log(result);
      expect(result.status).toBe(202);
      expect('to').toBe('fail');
    });

  });

  it('test something', () => {
    // expect('').toBe('not');
  });
});
