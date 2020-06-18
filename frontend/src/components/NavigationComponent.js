import React from 'react';

function NavigationComponent(props) {

    return(
        <nav>
            <ul>{props.navLinks} </ul>
            <br/>

            {sessionStorage.getItem("auth") === 'true' ?
                <button className="logoutBtn" onClick={props.logout}>Logout</button>
                :
                null
            }
            <br/>
            <hr/>

        </nav>
    );
}

export default NavigationComponent;
