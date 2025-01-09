import './App.css';
import Navbar from './Navbar';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import { UserProvider } from './components/UserContext';
import LandingPage from './components/LandingPage';
import UserProfileComponent from './components/profile-components/UserProfileComponent';
import SearchBar from './components/search-components/SearchBar';
import Feed from './components/post-components/Feed';
import FollowRequestComponent from './components/follow-request-components/FollowRequestComponent';

function App() {
  return (
    <UserProvider>
      <Router>
          <div className="App">
            <Navbar/>
          </div>
          <Routes>
            <Route path ="/" element={<LandingPage />}/>
            <Route path="/user/:id" element={<UserProfileComponent />} />
            <Route path="/search" element={<SearchBar />} />
            <Route path="/explore" element={<Feed explore={true}/>} />
            <Route path="/requests" element={<FollowRequestComponent />} />
          </Routes>
        </Router>
    </UserProvider>
  );
}

export default App;
