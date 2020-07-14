import React from 'react';
import { useHistory } from 'react-router-dom';

function DisplayCVComponent(props) {
    
    const history = useHistory();

  return (
    <div>
        <div>
            <button onClick={() => history.push('/home')}>
                Back
            </button>
        </div>
        <h3> Filename: {props.cvFileName}</h3>
        {<object style={{width:`100%`, height:`${window.screen.height/2}px`}}
        id='output' aria-label={props.cvFileName} 
        type={props.fileType}
        data={props.cvData}/>
        }
    </div>
    );
}
export default DisplayCVComponent;