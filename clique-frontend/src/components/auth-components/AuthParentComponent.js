import { useState } from "react";
import LoginComponent from "./LoginComponent";
import RegisterComponent from "./RegisterComponent";
import welcomeImage from '../../images/welcome.png';

const AuthParentComponent = () => {
  const [showLogin, setShowLogin] = useState(true);

  const image =
  "https://www.inclusionhub.com/hubfs/Blog/Diverse-multiracial-and-multicultural-group-of-people-standing-together.jpg";
  return (
    <div className=" max-sm:px-4">
      <div className="min-h-screen flex flex-col items-center justify-center">
        <div
          className="
            grid md:grid-cols-2 gap-4 max-md:gap-8
            max-w-6xl max-md:max-w-lg w-full p-4 m-4
            shadow-[0_2px_10px_-3px_rgba(6,81,237,0.3)] rounded-md
            h-[800px] overflow-hidden
          "
        >
          <div className="flex flex-col justify-center h-full px-4 py-4">
          <img
                src={welcomeImage}
               
                alt="welcome"
            />
            <br />
            {showLogin ? <LoginComponent /> : <RegisterComponent />}
            <br />
            <button
              onClick={() => setShowLogin(!showLogin)}
              className="text-[#003a92] font-semibold hover:underline"
            >
              {showLogin ? "New user? Register" : "Existing user? Sign in"}
            </button>
          </div>
          <div className="relative w-full h-full rounded-xl overflow-hidden p-0">
            <img
                src={image}
                className="w-full h-full object-cover rounded-xl"
                alt="login"
            />
            </div>
        </div>
      </div>
    </div>
  );
};

export default AuthParentComponent;