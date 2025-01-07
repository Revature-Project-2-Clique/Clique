import React, {useState} from 'react';
import axios from 'axios';
import api from "../../service/api";

axios.defaults.withCredentials = true;

const RegisterComponent = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const registerHandler=(e)=>{
        console.log("Form submitted!");
        e.preventDefault();
        const userData={
            firstName: firstName,
            lastName: lastName,
            email: email,
            username: username,
            password: password
        }
        console.log("Sending axios request...");
        api.post("/auth/register", userData)
        .then((res)=>{
            console.log(res.data);
            alert("Registration successful!");
        })
        .catch((error)=>{
            console.log("Registration failed due to one or more fields being invalid", error);
            alert("Registration UNsuccessful!");
        })
    }
    return ( 
        <>
        <form onSubmit={registerHandler}>
            <label>First Name:</label>
                <input type="text" id="first" value={firstName} onChange={(e)=>setFirstName(e.target.value)}/><br/>
            <label>Last Name:</label>
                <input type="text" id="last" value={lastName} onChange={(e)=>setLastName(e.target.value)}/><br/>
            <label>Email:</label>
                <input type="text" id="email" value={email} onChange={(e)=>setEmail(e.target.value)}/><br/>
            <label>Username:</label>
                <input type="text" id="name" value={username} onChange={(e)=>setUsername(e.target.value)}/><br/>
            <label>Password:</label>
                <input type="password" id="password" value={password} onChange={(e)=>setPassword(e.target.value)}/><br/>
            <button type="submit" value="Register">Register</button>
        </form>
        </>
     );
}
 
export default RegisterComponent;