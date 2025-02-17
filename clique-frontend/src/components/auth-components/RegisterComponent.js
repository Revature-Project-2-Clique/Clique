import React, { useState } from "react";
import axios from "axios";
import api from "../../service/api";

axios.defaults.withCredentials = true;

const RegisterComponent = () => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const registerHandler = (e) => {
    e.preventDefault();
    const userData = { firstName, lastName, email, username, password };

    api
      .post("/auth/register", userData)
      .then((res) => {
        alert("Registration successful!");
      })
      .catch((error) => {
        console.log("Registration failed", error);
        alert("Registration UNsuccessful!");
      });
  };

  return (
    <form onSubmit={registerHandler} className="mt-8 space-y-6">
      <div>
        <label className="text-gray-800 text-xs block mb-2">First Name</label>
        <input
          type="text"
          id="first"
          placeholder="First Name"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
          className="w-full text-gray-800 text-sm border-b border-gray-300 focus:border-blue-600 pl-2 py-2 outline-none"
        />
      </div>
      <div>
        <label className="text-gray-800 text-xs block mb-2">Last Name</label>
        <input
          type="text"
          id="last"
          placeholder="Last Name"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
          className="w-full text-gray-800 text-sm border-b border-gray-300 focus:border-blue-600 pl-2 py-2 outline-none"
        />
      </div>
      <div>
        <label className="text-gray-800 text-xs block mb-2">Email</label>
        <input
          type="text"
          id="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="w-full text-gray-800 text-sm border-b border-gray-300 focus:border-blue-600 pl-2 py-2 outline-none"
        />
      </div>
      <div>
        <label className="text-gray-800 text-xs block mb-2">Username</label>
        <input
          type="text"
          id="name"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="w-full text-gray-800 text-sm border-b border-gray-300 focus:border-blue-600 pl-2 py-2 outline-none"
        />
      </div>
      <div>
        <label className="text-gray-800 text-xs block mb-2">Password</label>
        <input
          type="password"
          id="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="w-full text-gray-800 text-sm border-b border-gray-300 focus:border-blue-600 pl-2 py-2 outline-none"
        />
      </div>
      <button
        type="submit"
        className="w-full shadow-xl py-2.5 px-4 text-sm tracking-wide rounded-md text-white bg-[#002e74] hover:bg-[#004dbd] focus:outline-none"
      >
        Register
      </button>
    </form>
  );
};

export default RegisterComponent;
