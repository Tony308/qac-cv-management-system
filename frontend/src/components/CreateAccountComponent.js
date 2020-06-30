import React from 'react';
function CreateAccountComponent(props) {
    return (
        <div>
            <h1> Create Account</h1>
                <p style={{"color":"red", "fontSize":"14px"}}><b>{props.errMessage ? props.errMessage: null}</b></p>
                <form method="POST" name="create-account-form" onSubmit={props.createAccount} >
                <p>Username:</p><input name="username" id="username" type="text" placeholder="Username"  onChange={props.handleChange}/>
                <p>Password:</p><input name="password" id="password" type="password" placeholder="Password"  onChange={props.handleChange}/>
                <p style={{"color":"red","fontSize":"12px"}}>{props.passwordMismatchMessage === true ? "Passwords don't match" : null}</p>
                <p>Confirm Password:</p><input type="password" placeholder="Confirm Password" onChange={props.confirmPassword}/>
                <br/>
                <br/>
                <input type="submit" value="Create Account"/>
            </form>
        </div>
    )
}
export default CreateAccountComponent;