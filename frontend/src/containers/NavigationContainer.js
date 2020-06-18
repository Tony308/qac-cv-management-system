import React from 'react';
import '../css/Navbar.css';
import NavigationComponent from "../components/NavigationComponent";
// import Cookies from 'js-cookie';

class NavigationContainer extends React.Component {
    constructor(props) {
        super(props);
        this.logout = this.logout.bind(this);
    }

    logout() {
      // Cookies.remove('authToken');
      sessionStorage.removeItem('auth');
      localStorage.removeItem('username');
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
