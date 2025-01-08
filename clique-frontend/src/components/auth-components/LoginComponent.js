import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { useUser } from "../UserContext";
import api from "../../service/api";

const LoginComponent = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { updateUser } = useUser();

  const handleLogin = async (e) => {
    e.preventDefault();
    const userData = { username, password };

    try {
      const response = await api.post("/auth/login", userData);
      const authorizationHeader = response.headers["authorization"];
      const token = authorizationHeader.split(" ")[1];
      updateUser(response.data, token);
      navigate("/");
    } catch (error) {
      console.error("Error logging in:", error);
    }
  };

  return (
    <form onSubmit={handleLogin} className="mt-8 space-y-6">
      <h3 className="text-gray-800 text-2xl font-bold">Sign In</h3>
      <div>
        <label htmlFor="username" className="text-gray-800 text-xs block mb-2">
          Username
        </label>
        <input
          placeholder="username"
          type="text"
          name="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="w-full text-gray-800 text-sm border-b border-gray-300
                     focus:border-blue-600 pl-2 py-2 outline-none"
        />
      </div>
      <div>
        <label htmlFor="password" className="text-gray-800 text-xs block mb-2">
          Password
        </label>
        <input
          placeholder="************"
          type="password"
          name="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="w-full text-gray-800 text-sm border-b border-gray-300
                     focus:border-blue-600 pl-2 py-2 outline-none"
        />
      </div>
      <button
        type="submit"
        className="w-full shadow-xl py-2.5 px-4 text-sm tracking-wide
                   rounded-md text-white bg-blue-600 hover:bg-blue-700
                   focus:outline-none"
      >
        Login
      </button>
    </form>
  );
};

export default LoginComponent;
