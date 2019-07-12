import React from 'react';


function loginComponent() {
    return (
        <div>
            <form method="POST" action="">
                <p>Username:</p><input type="text" placeholder="Username"/>
                <p>Password</p><input type="password" placeholder="Password"/>
                <br/>
                <br/>
                <input type="submit" value="Submit" onSubmit=""/>
            </form>
        </div>
    );
}

export default loginComponent;