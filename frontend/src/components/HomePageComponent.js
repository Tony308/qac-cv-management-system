import React from 'react';

import TableStyle from '../css/TableStyle.css';
import "../css/CVSection.css";

export function HomePageComponent(props) {
  return(
    <div style={{border: "1px dashed red"}}>
      <div className="form">
          <form onSubmit={props.uploadCV}>
              <label placeholder="Browse file to upload" />
              <input id="CV" type="file" placeholder="Upload here" alt="upload-file"/>
              <br />
              <br />
              <input type="submit" value="Submit" />
          </form>

          <br/>

      </div>

      <div className="cvs">
          <hr/>
          <br/>
          <table style={TableStyle}>
            <thead>
              <tr>
                <td>Username</td><td>File name</td>
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
