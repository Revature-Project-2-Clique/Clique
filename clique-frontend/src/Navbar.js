import { Link } from 'react-router-dom';
import { useUser } from "./components/UserContext";
import banner from './images/banner.png';

const Navbar = () => {
  const { user, clearUser } = useUser();

  const handleLogout = () => {
    clearUser();
  }

  return (
    <header className="flex py-3 px-4 sm:px-10 bg-white min-h-[65px] tracking-wide relative z-50">
      <div className="flex flex-wrap items-center gap-4 max-w-screen-xl mx-auto w-full">
      <Link 
              to="/" 
              >
      <img
          src={banner}
          className="w-auto h-9"
          alt="banner"
        />
     </Link>
        {/* Conditional Navigation Links for Logged-in Users */}
        {user && (
          <nav className="ml-auto flex gap-x-4">
            <Link 
              to="/" 
               className="text-[#003a92] font-bold text-lg hover:underline"
            >
              Home
            </Link>
            <Link 
              to={`/user/${user.userId}`} 
               className="text-[#003a92] font-bold text-lg hover:underline"
            >
              Profile
            </Link>
            <Link 
                to="/search"
                 className="text-[#003a92] font-bold text-lg hover:underline"
            >
              Search
            </Link>
            <Link 
                to="/explore"
                 className="text-[#003a92] font-bold text-lg hover:underline"
            >
              Explore
            </Link>
            {user.private && (
              <Link
                to="/requests"
                className="text-[#003a92] font-bold text-lg hover:underline"
              >
                Follow Requests
              </Link>
            )}
            <Link 
              to="/" 
              onClick={handleLogout}
               className="text-[#003a92] font-bold text-lg hover:underline"
            >
              Logout
            </Link>
          </nav>
        )}
      </div>
    </header>
  );
}

export default Navbar;
