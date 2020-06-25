import React, {Component} from 'react';

import { render, cleanup } from '@testing-library/react';
import renderer from 'react-test-renderer';
import "@testing-library/jest-dom";

import LoginContainer from '../containers/LoginContainer';

afterEach(cleanup);

describe("Check LoginContainer", function()  {
it('check errMessage prop', () => {
    const {getByText} = render(<LoginContainer errMessage="Error"/>);
    expect(getByText("Error")).toHaveTextContent("Error");
  })

  test("Snapshot tester", () => {
    const tree = renderer.create(<LoginContainer />).toJSON();
    expect(tree).toMatchSnapshot();
  })
})