import React from 'react';

function loginComponent(props) {
    return (
        <div>
            <h1> Login </h1>
            <form method="POST" name="login-form" onSubmit={props.login} >
              <p style={{"color":"red", "fontSize": "13px"}}>{props.errMessage ? props.errMessage : null}</p>
                <p>Username:</p><input type="text" placeholder="Username" name="username" onChange={props.handleChange}/>
                <p>Password</p><input type="password" placeholder="Password" name="password" onChange={props.handleChange}/>
                <br />
                <br />
                <input type="submit" value="Login"/>
            </form>
        </div>
    );
}

export default loginComponent;
