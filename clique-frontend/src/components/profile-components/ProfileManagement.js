import { useState } from "react";
import { useUser } from "../UserContext";
import ChangeName from "./ChangeName";
import ChangePassword from "./ChangePassword";
import ChangeBio from "./ChangeBio";
import api from "../../service/api";
import axios from "axios";

axios.defaults.withCredentials = true;

const ProfileManagement = () => {
    const { user, token } = useUser();
    const { updateUser } = useUser();

    const [firstName, setFirstName] = useState(user.firstName);
    const [lastName, setLastName] = useState(user.lastName);
    const [password, setPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [showChangeName, setShowChangeName] = useState(true);
    const [bio, setBios] = useState(user.bio)

    const headers = token ? { Authorization: `Bearer ${token}` } : {};

    const passwordSubmitHandler = async (e) => {
        e.preventDefault();

        const request = {
            username: user.username,
            password: password,
            newPassword: newPassword
        };

        try {
            await api.patch("/user/change-password", request, { headers });
            
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
            const response = await api.patch("/user/update-name", request, { headers });

            const authorizationHeader = response.headers["authorization"];
            const token = authorizationHeader.split(" ")[1];
            
            updateUser(response.data, token);
        } catch (error) {
            console.error("Error updating name: ", error);
        }
    }

    const bioSubmitHandler = async (e) => {
        e.preventDefault();

        const request = {
            bio: bio
        }

        try {
            const response = await api.put("/user/edit-bio", request, {headers});
            updateUser(response.data.bio, token);

        } catch (error) {
            console.error("Error updating bio: ", error);
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
        <ChangeBio bio={bio} setBios = {setBios} bioSubmitHandler={bioSubmitHandler}  />
        <br/>
        <button onClick = {() => setShowChangeName(!showChangeName)}>
            {showChangeName ? "Change your password" : "Change your name"}
        </button>
        </>
    );

}

export default ProfileManagement;