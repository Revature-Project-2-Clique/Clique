import {Link} from 'react-router-dom';

const Navbar = () => {
    return ( 
        <div className="navbar">
            <div className="links">
                <Link to="/register">Register</Link>
            </div>
        </div>
     );
}
 
export default Navbar;