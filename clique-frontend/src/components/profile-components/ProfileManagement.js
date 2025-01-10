import { useState } from "react";
import { useUser } from "../UserContext";
import ChangeName from "./ChangeName";
import ChangePassword from "./ChangePassword";
import ChangeBio from "./ChangeBio";
import api from "../../service/api";
import axios from "axios";
import ChangePrivacy from "./ChangePrivacy";

axios.defaults.withCredentials = true;

const ProfileManagement = () => {
    const { user, token } = useUser();
    const { updateUser } = useUser();

    const [currentForm, setCurrentForm] = useState("name");     // state profile management forms, name/password/privacy
    const [firstName, setFirstName] = useState(user.firstName);
    const [lastName, setLastName] = useState(user.lastName);
    const [password, setPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [isPrivate, setIsPrivate] = useState(user.private);
    const [bio, setBios] = useState(user.bio);


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
            const response = await api.patch("/user/update-name", request, { headers })
            updateUser(response.data);
        } catch (error) {
            console.error("Error updating name: ", error);
        }
    }


    const privacySubmitHandler = async (e) => {
        try {
            await api.patch("/user/change-visibility", {},{ headers })
            const updatedUser = { ...user, private: !user.private };
            updateUser(updatedUser); 
            setIsPrivate(!isPrivate);
        } catch (error) {
            console.error("Error changing profile privacy: ", error);
        }
    }

    const bioSubmitHandler = async (e) => {
        e.preventDefault();

        const request = {
            bio: bio
        }

        try {
            const response = await api.patch("/user/edit-bio", request, {headers});
            const updatedUser = { ...user, bio: response.bio};
            updateUser(updatedUser);

        } catch (error) {
            console.error("Error updating bio: ", error);

        }
    }

    return(
        <>
        <br/><br/>
        <h2>Profile Management</h2>
        <br/>
        <div>
            <button onClick = {() => setCurrentForm("name")}>Update Name</button>
            <br/>
            <button onClick = {() => setCurrentForm("password")}>Change Password</button>
            <br/>
            <button onClick = {() => setCurrentForm("privacy")}>Change Privacy</button>
            <br/>
            <button onClick = {() => setCurrentForm("bio")}>Update Bio</button>
        </div>
        <br/>
        {currentForm === "name" && (
                <ChangeName
                    firstName={firstName}
                    lastName={lastName}
                    nameSubmitHandler={nameSubmitHandler}
                    setFirstName={setFirstName}
                    setLastName={setLastName}
                />
        )}
        
        {currentForm === "password" && (
            <ChangePassword 
                password={password} 
                newPassword = {newPassword} 
                passwordSubmitHandler={passwordSubmitHandler} 
                setPassword={setPassword} 
                setNewPassword={setNewPassword} 
            />
        )}

        {currentForm === "privacy" && (
            <ChangePrivacy 
                isPrivate={isPrivate} 
                setIsPrivate = {setIsPrivate} 
                privacySubmitHandler={privacySubmitHandler} 
            />
        )}

        {currentForm === "bio" && (
            <ChangeBio 
                setBios = {setBios}
                bioSubmitHandler={bioSubmitHandler}
            />
        )}

        </>
    )

}

export default ProfileManagement;