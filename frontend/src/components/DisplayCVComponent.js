import React from 'react';

export function DisplayCVComponent(props) {
  return (

    <div>
      <h3> Filename: {props.cvFileName}</h3>

      {props.cvData ?
        <object aria-labelledby={props.cvFileName}type="application/pdf" data={props.cvData}/>
      :
      <pre id='output'>
        {atob(props.cv)}
      </pre>
      }
    </div>
  );
}


export default DisplayCVComponent;
