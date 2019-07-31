import React from 'react';
import '../css/Navbar.css';
import NavigationComponent from "../components/NavigationComponent";

class NavigationContainer extends React.Component {
    render() {
        const pages = ['home', 'blog', 'pics', 'bio', 'art', 'shop', 'about', 'contact'];
        const navLinks = pages.map(page => {
            return (
                <li>
                    <a href={'/' + page}>
                        {page} <br/>
                    </a>
                </li>
            )
        });


        return(
            <NavigationComponent navLinks={navLinks}/>
        );
    }
}
export default NavigationContainer;