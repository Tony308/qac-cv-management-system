import React from 'react';
import renderer from 'react-test-renderer';
import '@testing-library/jest-dom';
import {render, cleanup} from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import userEvent from '@testing-library/user-event';

import Navbar from '../containers/NavigationContainer';

afterEach(() => {
    cleanup();
    sessionStorage.removeItem('auth');
})

describe('Navbar ', () => {
    test('snapshot', () => {
        const tree = renderer.create(
            <MemoryRouter>
                <Navbar />
            </MemoryRouter>
            ).toJSON();
        expect(tree).toMatchSnapshot();
    })

    test('smoke test', () => {
        const {getByRole} = render(
            <MemoryRouter>
                <Navbar/>
            </MemoryRouter>
        );
        const home = getByRole('link', {name: 'Home'});
        const createacc = getByRole('link', {name: 'create-account'});

        expect(home).toHaveTextContent('Home');
        expect(createacc).toHaveTextContent('create-account');
    })

    test('logout', () => {
        sessionStorage.setItem('auth', 'true');
        var auth = sessionStorage.getItem('auth');
        expect(auth).toBe('true');

        const {getByRole} = render(
            <MemoryRouter>
                <Navbar/>
            </MemoryRouter>
        )

        const logout = getByRole('button');

        userEvent.click(logout);
        auth = sessionStorage.getItem('auth');
        expect(auth).toBeNull();

    })
})