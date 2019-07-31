import React from 'react';

function NavigationComponent(props) {

    return(
        <nav>
            <ul>{props.navLinks} </ul>
            <br/>
            <br/>
            <hr/>
        </nav>
    );
}

export default NavigationComponent;