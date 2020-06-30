import React from 'react';
import renderer from 'react-test-renderer';
import '@testing-library/jest-dom';
import { MemoryRouter } from 'react-router-dom';
import {render, cleanup} from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import Homepage from '../containers/HomePageContainer';
import cvList from '../__mocks__/CvListJsonData';

const getCVs = jest.fn(() => console.log(`Get user CVs`));
const retrieveCV = jest.fn((id) => console.log(`get CV ${id}`));
const updateCV = jest.fn((id) => console.log(`Update CV ${id}`));
const uploadCV = jest.fn(() => console.log());
const deleteCV = jest.fn((id) => console.log(`delete CV ${id}`));


describe('Homepage container, test', () => {
    afterEach(cleanup);

    test('snapshot', () => {        

        expect(cvList).toMatchSnapshot(cvList);
        
        const tree = renderer.create(
            <MemoryRouter>
            <Homepage 
                    data={cvList}
                    cv={null}
                    getCVs={getCVs} 
                    updateCV={updateCV}
                    uploadCV={uploadCV}
                    deleteCV={deleteCV}
                    retrieveCV={retrieveCV}
                />
            </MemoryRouter>
        ).toJSON();
        
        expect(tree).toMatchSnapshot();

    })

    test('upload Cv', () => {
        const {getByRole, getByPlaceholderText} = render(
            <MemoryRouter>
                <Homepage 
                    data={cvList}
                    cv={''}
                    getCVs={getCVs} 
                    updateCV={updateCV}
                    uploadCV={uploadCV}
                    deleteCV={deleteCV}
                    retrieveCV={retrieveCV}
                />
            </MemoryRouter>
        );

        const fileUpload = getByPlaceholderText('Upload here');
        let file = new File([cvList[0].cvFile.data], "uploadImage.jpg", { type:'image/png', lastModified:Date.now() });

        userEvent.click(fileUpload);
        userEvent.upload(fileUpload, file);
    
        const files = document.getElementById('CV').files;
        expect(files).toHaveLength(1);
        // const submit = getByRole('button', {name: 'Submit'});
        // userEvent.click(submit);
    })

    test('update Cv', () => {
        const {getAllByRole, getByPlaceholderText} = render(
            <MemoryRouter>
                <Homepage 
                    data={cvList}
                    cv={''}
                    getCVs={getCVs} 
                    updateCV={updateCV}
                    uploadCV={uploadCV}
                    deleteCV={deleteCV}
                    retrieveCV={retrieveCV}
                />
            </MemoryRouter>
        );
        const fileUpload = getByPlaceholderText('Upload here');
        let file = new File([cvList[0].cvFile.data], "uploadImage.jpg", { type:'image/png', lastModified:Date.now() });
        userEvent.upload(fileUpload, file);

        const btn = getAllByRole('button');
        userEvent.click(btn[2]);

        expect(updateCV).toBeCalledTimes(1);
        expect(updateCV).toBeCalledWith('5ef7a354caa61f31d2b00dee');
    })

    test('delete Cv', () => {
        const {getAllByRole} = render(
            <MemoryRouter>
                <Homepage 
                    data={cvList}
                    cv={''}
                    getCVs={getCVs} 
                    updateCV={updateCV}
                    uploadCV={uploadCV}
                    deleteCV={deleteCV}
                    retrieveCV={retrieveCV}
                />
            </MemoryRouter>
        );
        const del = getAllByRole('button');
        userEvent.click(del[1]);

        expect(deleteCV).toBeCalledTimes(1);
        expect(deleteCV).toBeCalledWith('5ef7a354caa61f31d2b00dee');
    })

    test('retrieve Cv', () => {
        const {getByRole, getAllByRole} = render(
            <MemoryRouter>
                <Homepage
                data={cvList}
                cv={''}
                getCVs={getCVs} 
                updateCV={updateCV}
                uploadCV={uploadCV}
                deleteCV={deleteCV}
                retrieveCV={retrieveCV}
                 />
            </MemoryRouter>
        );

        const fileCell = getByRole('cell', {name: 'Test File.pdf'});
        const nameCell = getAllByRole('cell', {name: 'random'});
        userEvent.click(fileCell);
        userEvent.click(nameCell[0]);

        expect(retrieveCV).toBeCalledTimes(2);
    });
})