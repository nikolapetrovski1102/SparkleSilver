import { NavLink, useHistory } from "react-router-dom";
import Cookies from 'js-cookie';

const Navbar = () => {
  const history = useHistory();

  const handleLogout = () => {
    // Clear the userId cookie
    Cookies.remove('userId');
    // Redirect to the login page
    history.push('/login');
  };

  return (
    <>
      <div className="navbar-container">
        <div className="navbar-top">
          <div className="navbar-logo">SparkleSilver</div>
          <div className="navbar-right">
            <input type="search" placeholder="Пребарувајте" />
            <NavLink to="/profile" className="navbar-icon">👤</NavLink>
            <NavLink to="/cart" className="navbar-icon">🛒</NavLink>
            <button onClick={handleLogout} className="navbar-icon">🚪</button>
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
