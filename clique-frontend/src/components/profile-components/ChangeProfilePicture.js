import { useState } from "react";
import axios from "axios";
import api from "../../service/api";
import { useUser } from "../UserContext";

axios.defaults.withCredentials = true;

const ChangeProfilePicture = ({ updateUser }) => {
  const { user, token } = useUser();
  const [selectedFile, setSelectedFile] = useState(null);

  const headers = token ? { Authorization: `Bearer ${token}` } : {};

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!selectedFile) {
      alert("Please select a profile picture first!");
      return;
    }
  
    try {
      const formData = new FormData();
      formData.append("profilePicture", selectedFile);
  
      const response = await api.post("/user/profile-picture", formData, {
        headers,
      });
      if (response.data) {
        updateUser(response.data);
        alert("Profile picture updated successfully!");
      }
    } catch (error) {
      console.error("Error updating profile picture:", error);
      alert("Could not update profile picture. Please try again.");
    }
  };
  

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      <div>
        <label htmlFor="profilePicture" className="text-gray-800 text-xs block mb-2">
          Select Profile Picture:
        </label>
        <input
          type="file"
          id="profilePicture"
          accept="image/*"
          onChange={(e) => setSelectedFile(e.target.files[0])}
          className="w-full text-gray-800 text-sm border-b border-gray-300 focus:border-blue-600 pl-2 py-2 outline-none"
        />
      </div>
      <button
        className="w-full shadow-xl py-2.5 px-4 text-sm tracking-wide rounded-md text-white bg-[#002e74] hover:bg-[#004dbd] focus:outline-none"
      >
        Upload
      </button>
    </form>
  );
};

export default ChangeProfilePicture;
