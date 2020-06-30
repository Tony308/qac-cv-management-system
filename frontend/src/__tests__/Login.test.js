import React from 'react';
import { render, cleanup} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import renderer from 'react-test-renderer';
import '@testing-library/jest-dom';

import LoginContainer from '../containers/LoginContainer';

afterEach(cleanup);

describe('Login container test', () => {
    test('snapshot test', () => {
        const tree = renderer.create(<LoginContainer/>).toJSON();
        expect(tree).toMatchSnapshot();
    });

    test('form submit without function', () => {
        const {getByRole, getByPlaceholderText} = render(<LoginContainer/>);

        const userBox = getByRole('textbox');
        const passwordBox = getByPlaceholderText('Password');
    
        userEvent.type(userBox, 'username');
        userEvent.type(passwordBox, 'password');

        expect(userBox.value).toBe('username');
        expect(passwordBox.value).toBe('password');
    })

    test('error message prop', () => {
        const { getByText } = render(<LoginContainer errMessage="error message" />);

        const message = getByText('error message');
        expect(message).toHaveTextContent('error message');

    })
})

