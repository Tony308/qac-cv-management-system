import React from 'react';

function CreateAccountComponent(props) {
    return (
        <div>
            <h1> Create Account</h1>
            <form method="POST" name="create-account-form" onSubmit={props.createAccount} >
                <p>Username:</p><input type="text" placeholder="Username" name="username" onChange={props.handleChange}/>
                <p>Password:</p><input id="password" type="password" placeholder="Password" name="password" onChange={props.handleChange}/>
                <p>Confirm Password:</p><input type="password" placeholder="Confirm Password" onChange={props.confirmPassword}/>
                <br/>
                <br/>
                <input type="submit" value="Create Account"/>
            </form>
        </div>
    )
}

export default CreateAccountComponent;