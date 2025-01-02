import { useState } from "react";
import RegisterComponent from "./RegisterComponent";
import LoginComponent from "./LoginComponent";

const AuthParentComponent = () => {

    // state to toggle between rendering login/register components
    const [showLogin, setShowLogin] = useState(true);

    return(
        <div>
            <h2>Welcome to Clique</h2><br/>
            {showLogin ? <LoginComponent /> : <RegisterComponent />}<br/>
            <button onClick={() => setShowLogin(!showLogin)}>
                {showLogin ? "New user? Register" : "Existing user? Sign in"}
            </button>
        </div>
    )

}

export default AuthParentComponent;