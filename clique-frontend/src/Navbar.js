import { Link } from 'react-router-dom';
import { useUser } from "./components/UserContext";

const Navbar = () => {
  const { user, clearUser } = useUser();

  const handleLogout = () => {
    clearUser();
  }

  return (
    <header className="flex py-3 px-4 sm:px-10 bg-white font-[sans-serif] min-h-[65px] tracking-wide relative z-50">
      <div className="flex flex-wrap items-center gap-4 max-w-screen-xl mx-auto w-full">
        {/* App Name as Logo Replacement */}
        <h2 className="text-2xl font-bold">Clique</h2>

        {/* Conditional Navigation Links for Logged-in Users */}
        {user && (
          <nav className="ml-auto flex gap-x-4">
            <Link 
              to="/" 
              className="lg:hover:text-[#007bff] text-gray-800 text-[15px]"
            >
              Home
            </Link>
            <Link 
              to={`/user/${user.userId}`} 
              className="lg:hover:text-[#007bff] text-gray-800 text-[15px]"
            >
              Profile
            </Link>
            <Link 
                to="/search"
                className="lg:hover:text-[#007bff] text-gray-800 text-[15px]"
            >
              Search
            </Link>
            <Link 
                to="/explore"
                className="lg:hover:text-[#007bff] text-gray-800 text-[15px]"
            >
              Explore
            </Link>
            {user.private && (
              <Link
                to="/requests"
                className="lg:hover:text-[#007bff] text-gray-800 text-[15px]"
              >
                Follow Requests
              </Link>
            )}
            <Link 
              to="/" 
              onClick={handleLogout}
              className="lg:hover:text-[#007bff] text-gray-800 text-[15px]"
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
