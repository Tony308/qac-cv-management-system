import React from 'react';
import { render, cleanup } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import renderer from 'react-test-renderer';
import '@testing-library/jest-dom';

import AccountContainer from '../containers/CreateAccountContainer';

afterEach(cleanup);

describe('Create account test', () => {
  test('submit form without function', () => {
    const {getByRole, getByPlaceholderText, getByText} = render(
      <AccountContainer/>
    );

    const username = getByRole('textbox');
    const password = document.getElementById('password');
    const confirm = getByPlaceholderText('Confirm Password');

    userEvent.type(username, 'username');
    userEvent.type(password, 'password');
    userEvent.type(confirm, 'pass');

    const message = getByText("Passwords don't match");

    expect(message).toHaveTextContent("Passwords don't match");
    userEvent.type(confirm, 'word');
    
    expect(confirm.value).toBe('password');
    expect(username.value).toBe('username');
    expect(password.value).toBe('password');    
  })

  test('error message prop', () => {
    const error = "Error Message";
    const {getByText} = render(
      <AccountContainer errMessage={error}/>
    );
    const message = getByText('Error Message');
    expect(message).toHaveTextContent('Error Message');
  })  
})

test('snapshot test', () => {
  const tree = renderer.create(<AccountContainer/>).toJSON();
  expect(tree).toMatchSnapshot();
})