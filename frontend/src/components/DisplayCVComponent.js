import React from 'react';

export function DisplayCVComponent(props) {
  return (

    <div>
      <h3> Filename: {props.cvFileName}</h3>
      <pre id='output'>
      {props.cv}
      </pre>
    </div>
  );
}


export default DisplayCVComponent;
