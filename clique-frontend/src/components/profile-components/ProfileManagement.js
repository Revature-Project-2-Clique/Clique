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

  const [currentForm, setCurrentForm] = useState(null);
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
      newPassword: newPassword,
    };
    try {
      await api.patch("/user/change-password", request, { headers });
    } catch (error) {
      console.error("Error updating password: ", error);
    }
  };

  const nameSubmitHandler = async (e) => {
    e.preventDefault();
    const request = {
      username: user.username,
      firstName: firstName,
      lastName: lastName,
    };
    try {
      const response = await api.patch("/user/update-name", request, { headers });
      updateUser(response.data);
    } catch (error) {
      console.error("Error updating name: ", error);
    }
  };

  const privacySubmitHandler = async () => {
    try {
      await api.patch("/user/change-visibility", {}, { headers });
      const updatedUser = { ...user, private: !user.private };
      updateUser(updatedUser);
      setIsPrivate(!isPrivate);
    } catch (error) {
      console.error("Error changing profile privacy: ", error);
    }
  };

  const bioSubmitHandler = async (e) => {
    e.preventDefault();
    const request = {
      bio: bio,
    };
    try {
      const response = await api.patch("/user/edit-bio", request, { headers });
      const updatedUser = { ...user, bio: response.bio };
      updateUser(updatedUser);
    } catch (error) {
      console.error("Error updating bio: ", error);
    }
  };

  return (
    <div className="space-y-6">
      <br />
      <br />
      <h2 className="text-2xl font-bold text-[#003a92]">Profile Management</h2>
      <br />
      <div className="space-y-3">
        <button
          onClick={() => setCurrentForm("name")}
          className="block text-[#003a92] font-bold text-lg hover:underline cursor-pointer focus:outline-none"
        >
          Update Name
        </button>
        <button
          onClick={() => setCurrentForm("password")}
          className="block text-[#003a92] font-bold text-lg hover:underline cursor-pointer focus:outline-none"
        >
          Change Password
        </button>
        <button
          onClick={() => setCurrentForm("privacy")}
          className="block text-[#003a92] font-bold text-lg hover:underline cursor-pointer focus:outline-none"
        >
          Change Privacy
        </button>
        <button
          onClick={() => setCurrentForm("bio")}
          className="block text-[#003a92] font-bold text-lg hover:underline cursor-pointer focus:outline-none"
        >
          Update Bio
        </button>
      </div>
      <br />
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
          newPassword={newPassword}
          passwordSubmitHandler={passwordSubmitHandler}
          setPassword={setPassword}
          setNewPassword={setNewPassword}
        />
      )}
      {currentForm === "privacy" && (
        <ChangePrivacy
          isPrivate={isPrivate}
          privacySubmitHandler={privacySubmitHandler}
        />
      )}
      {currentForm === "bio" && (
        <ChangeBio setBios={setBios} bioSubmitHandler={bioSubmitHandler} />
      )}
    </div>
  );
};

export default ProfileManagement;
