import React from 'react';
import '../css/Navbar.css';
import NavigationComponent from "../components/NavigationComponent";


class NavigationContainer extends React.Component {
    constructor(props) {
        super(props);
        this.logout = this.logout.bind(this);
    }

    logout() {
        sessionStorage.setItem("auth", 'false');
        console.log(sessionStorage.getItem("auth"));
        window.location.href = "/";
    }

    render() {
        const pages = ['home', 'create-account', 'about', 'contact'];
        const navLinks = pages.map(page => {
            return (
                <li key={page}>
                    <a href={'/' + page}>
                        {page} <br/>
                    </a>
                </li>
            )
        });


        return(
            <NavigationComponent logout={this.logout} navLinks={navLinks}/>
        );
    }
}
export default NavigationContainer;
