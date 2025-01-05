import axios from "axios";
import { useState } from "react";
import { useUser } from "../UserContext";
import ChangeName from "./ChangeName";
import ChangePassword from "./ChangePassword";

axios.defaults.withCredentials = true;

const ProfileManagement = () => {
    const { user, token } = useUser();
    const { updateUser } = useUser();

    const [firstName, setFirstName] = useState(user.firstName);
    const [lastName, setLastName] = useState(user.lastName);
    const [password, setPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [showChangeName, setShowChangeName] = useState(true);

    const headers = token ? { Authorization: `Bearer ${token}` } : {};

    const passwordSubmitHandler = async (e) => {
        e.preventDefault();

        const request = {
            username: user.username,
            password: password,
            newPassword: newPassword
        };

        try {
            await axios.patch("http://3.82.150.19:8080/user/change-password", request, { headers });
            
        } catch (error) {
            console.error("Error updating password: ", error);
        }
    }

    const nameSubmitHandler = async (e) => {
        e.preventDefault();

        const request = {
            username: user.username,
            firstName: firstName,
            lastName: lastName
        }

        try{
            const response = await axios.patch("http://3.82.150.19:8080/user/update-name", request, { headers });

            const authorizationHeader = response.headers["authorization"];
            const token = authorizationHeader.split(" ")[1];
            
            updateUser(response.data, token);
        } catch (error) {
            console.error("Error updating name: ", error);
        }
    }



    return(
        <>
        <br/><br/>
        <h2>Profile Management</h2>
        <br/>
        {
            showChangeName ? 
            <ChangeName firstName={firstName} lastName={lastName} nameSubmitHandler={nameSubmitHandler} setFirstName = {setFirstName} setLastName={setLastName} /> 
            :
            <ChangePassword password={password} 
                            newPassword = {newPassword} 
                            passwordSubmitHandler={passwordSubmitHandler} 
                            setPassword={setPassword} 
                            setNewPassword={setNewPassword} />
        }
        <br/>
        <button onClick = {() => setShowChangeName(!showChangeName)}>
            {showChangeName ? "Change your password" : "Change your name"}
        </button>
        </>
    );

}

export default ProfileManagement;