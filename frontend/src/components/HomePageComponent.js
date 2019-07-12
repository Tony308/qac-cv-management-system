import React from 'react';

export function HomePageComponent(props) {
    return(
        <div>
            <label placeholder="Browse file to upload" />
            <input id="CV" type="file" placeholder="Upload here" alt = "upload-file"
                   onChange={props.uploadCV}
            />
            <br />
            <br />
            <textarea id="output" placeholder={"helloworld"} cols={50} rows={5}/>

        </div>
    );
}

export default HomePageComponent;