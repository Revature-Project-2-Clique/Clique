import './App.css';
import RegisterComponent from './components/auth-components/RegisterComponent';
import Navbar from './Navbar';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import { UserProvider } from './components/UserContext';
import LandingPage from './components/LandingPage';

function App() {
  return (
    <UserProvider>
      <Router>
          <div className="App">
            <Navbar/>
          </div>
          <Routes>
            <Route path ="/" element={<LandingPage />}/>
            <Route path='/register' element={<RegisterComponent/>}/>
          </Routes>
        </Router>
    </UserProvider>
  );
}

export default App;
