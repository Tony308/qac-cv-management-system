import React from 'react';
import { cleanup, render } from '@testing-library/react'
import '@testing-library/jest-dom';
import renderer from 'react-test-renderer';
import {useHistory} from 'react-router-dom';
import userEvent from '@testing-library/user-event';

import DisplayCvContainer from '../containers/DisplayCVContainer';
import imageBlob from '../__mocks__/TestImageBlob';
import pdfBlob from '../__mocks__/TestPdfBlob';

const mockHistoryPush = jest.fn(() => console.log('history.pushing'));

jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useHistory: () => ({
      push: mockHistoryPush,
    }),
}));

var history = null;

beforeEach(() => history = useHistory());
afterEach(cleanup);

describe('DisplayCV', () => {

    var data;
    var filename;
    test('plain/text snapshot', () => {
        data = "SW5pdGlhbCBGaWxlCkZlZWwgZnJlZSB0byBkZWxldGUgdGhpcyBmaWxlIHdoZW4gdGhlIGRhdGFiYXNlIGlzIHNldHVwLg==";
        filename = 'testFile.txt';
        const tree = renderer.create(<DisplayCvContainer cv={data} cvFileName={filename} />).toJSON();
        expect(tree).toMatchSnapshot('default');
    })
    test('render plain/text', () => {
        const {getByLabelText} = render(<DisplayCvContainer cv={data} cvFileName={filename} />);
        const document = getByLabelText(filename);
        expect(document).toBeInTheDocument();
    })

    test('render PDF', () => {
        data = pdfBlob;
        filename = "test file.pdf";

        const {getByLabelText} = render(<DisplayCvContainer cv={data} cvFileName={filename}/>);
        const pdf = getByLabelText(filename);
        expect(pdf).toBeInTheDocument();
    })

    test('PDF snapshot', () => {
        data = pdfBlob;
        filename = "test file.pdf";

        const tree = renderer.create(<DisplayCvContainer cv={data} cvFileName={filename} />).toJSON();
        expect(tree).toMatchSnapshot('pdf');
    })

    test('render image', () => {
        data = imageBlob;
        filename = 'image.jpg';
        const {getByLabelText} = render(<DisplayCvContainer cv={data} cvFileName={filename} />);
        const doc = getByLabelText(filename);
        expect(doc).toBeInTheDocument();
    })

    test('image snapshot', () => {
        data = imageBlob;
        filename = 'image.jpg';

        const tree = renderer.create(<DisplayCvContainer cv={data} cvFileName={filename}/>).toJSON();
        expect(tree).toMatchSnapshot('image');
    })

    test('Back button', () => {
        const {getByRole} = render(
            <DisplayCvContainer cv='random' cvFileName='file.txt'/>
        )

        const backBtn = getByRole('button');
        userEvent.click(backBtn);
        expect(mockHistoryPush).toBeCalledTimes(1);
    })
})