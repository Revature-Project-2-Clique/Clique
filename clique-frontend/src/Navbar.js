import {Link} from 'react-router-dom';
import { useUser } from "./components/UserContext";

const Navbar = () => {

    const { user, clearUser } = useUser();

    const handleLogout = () => {
        clearUser();
    }

    // uses context to conditionally render the navbar, if a user is not logged in, they cannot navigate beyond the login/register forms
    return ( 
        <div className="navbar">
            <h2>Clique</h2>
            {user ?
            <>
                <div className="links">
                    <Link to="/">Home</Link>
                    <Link to={`/user/${user.userId}`}>Profile</Link>
                    <Link to="/" onClick={handleLogout}>Logout</Link>
                </div>
            </> 
            : null}

        </div>
     );
}
 
export default Navbar;