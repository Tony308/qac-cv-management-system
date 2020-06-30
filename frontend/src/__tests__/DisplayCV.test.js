import React from 'react';
import { cleanup, render } from '@testing-library/react'
import '@testing-library/jest-dom';
import renderer from 'react-test-renderer';
import {MemoryRouter} from 'react-router-dom';

import DisplayCvContainer from '../containers/DisplayCVContainer';
import imageBlob from '../__mocks__/TestImageBlob';
import pdfBlob from '../__mocks__/TestPdfBlob';

describe('DisplayCV', () => {
    var data;
    var filename;

    afterEach(cleanup);
    
    test('plain/text snapshot', () => {
        data = "SW5pdGlhbCBGaWxlCkZlZWwgZnJlZSB0byBkZWxldGUgdGhpcyBmaWxlIHdoZW4gdGhlIGRhdGFiYXNlIGlzIHNldHVwLg==";
        filename = 'testFile.txt';
        const tree = renderer.create(<DisplayCvContainer cv={data} cvFileName={filename} />).toJSON();
        expect(tree).toMatchSnapshot('default');
    })
    test('render plain/text', () => {
        const {getByText} = render(<DisplayCvContainer cv={data} cvFileName={filename} />);
        const content = getByText(/Initial File/);
        expect(content).toHaveTextContent('Initial File Feel free to delete this file when the database is setup.');
    })

    test('render PDF', () => {
        data = pdfBlob;
        filename = "test file.pdf";

        const {getByLabelText} = render(<DisplayCvContainer cv={data} cvFileName={filename}/>);
        const pdf = getByLabelText(/test file.pdf/);
        expect(pdf).toBeInTheDocument();
    })

    test('PDF snapshot', () => {
        data = pdfBlob;
        filename = "test file.pdf";

        const tree = renderer.create(<DisplayCvContainer cv={pdfBlob} cvFileName={filename} />).toJSON();
        expect(tree).toMatchSnapshot('pdf');
    })
        
    

    test('render image', () => {
        data = imageBlob;
        filename = 'image.jpg';

        const {getByRole} = render(<DisplayCvContainer cv={data} cvFileName={filename} />);
        const image = getByRole('img');
        expect(image).toBeInTheDocument();
    })

    test('image snapshot', () => {
        data = imageBlob;
        filename = 'image.jpg';

        const tree = renderer.create(<DisplayCvContainer cv={data} cvFileName={filename}/>).toJSON();
        expect(tree).toMatchSnapshot('image');
    })

    test('redirect to homepage', () => {
        const {getByRole} = render(
            <MemoryRouter
                initialEntries={['/home', '/create-account']}
                >
                <DisplayCvContainer cv='' cvFileName=''/>
            </MemoryRouter>
        );

    })
})