import React from 'react';
import TableStyle from '../css/TableStyle.css';
import "../css/CVSection.css";

export function HomePageComponent(props) {
    return(
        <div>
            <div className="form">
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

            </div>


            <div className="cvs" style={{}}>
                <hr/>
                <br/>
                <table style={TableStyle}>

                    <thead>
                        <tr>
                            <td>ID</td><td>Username</td>
                        </tr>
                    </thead>

                    <tbody>
                    {props.CVs}
                    </tbody>
                </table>

            </div>
        </div>
    );
}

export default HomePageComponent;