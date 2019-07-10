import React from 'react';

export function HomePageComponent(props) {
    return(
        <div>
            <label placeholder="Browse file to upload" />
            <input id="CV" type="file" placeholder="Upload here" alt = "upload-file" onChange={props.uploadCV}/>

        </div>
    );
}

export default HomePageComponent;