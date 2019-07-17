import React from 'react';

export function HomePageComponent(props) {
    return(
        <div>

            <form onSubmit={props.uploadCV}>
                <label placeholder="Browse file to upload" />
                <input id="CV" type="file" placeholder="Upload here" alt = "upload-file"
                />
                <br />
                <br />
                <textarea id="output" placeholder={"hello world"} cols={50} rows={5}/>
                <br />
                <input type="submit" value="Submit" />
            </form>

            <br/>
            <input type="submit" onClick={props.getCVs} value="Get all"/>
        </div>
    );
}

export default HomePageComponent;