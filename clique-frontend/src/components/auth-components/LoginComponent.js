import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { useUser } from '../UserContext'
import api from "../../service/api";

const LoginComponent = () => {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();
    const { updateUser } = useUser();

    const handleLogin = async(e) => {
        e.preventDefault();

        const userData = {
            username: username,
            password: password
        };

        try {
            const response = await api.post("/auth/login", userData);

            const authorizationHeader = response.headers["authorization"];
            const token = authorizationHeader.split(" ")[1];

            updateUser(response.data, token);
            navigate("/");
        } catch(error) {
            console.error("Error logging in:", error);
        }
    }

    return(
        <>
            <form onSubmit={handleLogin}>
                <label htmlFor="username">Username:</label>
                <input 
                    placeholder="username"
                    type="text" 
                    name="username"
                    value={username}
                    onChange={(e)=>setUsername(e.target.value)}
                /><br/>
                <label htmlFor="password">Password:</label>
                <input 
                    placeholder="************"
                    type="password" 
                    name="password"
                    value={password}
                    onChange={(e)=>setPassword(e.target.value)}
                /><br/>
                <br/>
                <button type="submit">Login</button>
            </form>
        </>
    )

}


export default LoginComponent;