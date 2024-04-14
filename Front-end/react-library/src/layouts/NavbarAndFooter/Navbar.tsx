import { NavLink } from "react-router-dom";

const Navbar = () => {
    return (
        <>
        <div className="navbar-container">
            <div className="navbar-top">
                <div className="navbar-logo">SparkleSilver</div>
                <div className="navbar-right">
                    <input type="search" placeholder="Пребарувајте" />
                    <NavLink to="/profile" className="navbar-icon">👤</NavLink>
                    <NavLink to="/cart" className="navbar-icon">🛒</NavLink>
                </div>
            </div>
            <nav className="navbar">
                <ul className="nav-links">
                    <li><NavLink to="/earrings">ОБЕТКИ</NavLink></li>
                    <li><NavLink to="/necklaces">ОГРЛИЦИ</NavLink></li>
                    <li><NavLink to="/bracelets">АЛКИ</NavLink></li>
                    <li><NavLink to="/rings">ПРСТЕНИ</NavLink></li>
                </ul>
        </nav>
        </div>

        </>
    );
};

export default Navbar;
