import React from 'react';
import TableStyle from '../css/TableStyle.css';

export function HomePageComponent(props) {
    return(
        <div>
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

            <hr/>
            <br/>
            <div id="cvs" style={{textAlign:'center', border: 'solid black 2px'}}>
                <table style={TableStyle}>

                    <thead>
                        <tr>
                            <td>ID</td><td>File Name</td>
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