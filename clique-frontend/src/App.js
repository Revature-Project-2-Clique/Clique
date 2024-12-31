import './App.css';
import RegisterComponent from './components/RegisterComponent';
import Navbar from './Navbar';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';

function App() {
  return (
    <Router>
    <div className="App">
      <Navbar/>
    </div>
    <Routes>
      <Route path='/register' element={<RegisterComponent/>}/>
    </Routes>
    </Router>
  );
}

export default App;
