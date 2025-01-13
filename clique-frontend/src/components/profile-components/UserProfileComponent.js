import axios from "axios";
import { useEffect, useState } from "react";
import Modal from "react-modal";
import ProfileManagement from "./ProfileManagement";
import ConnectionDisplay from "./ConnectionDisplay";
import ConnectionManagement from "./ConnectionManagement";
import PostList from "../post-components/PostList";
import { useUser } from "../UserContext";
import { useParams } from "react-router-dom";
import api from "../../service/api";

axios.defaults.withCredentials = true;

const UserProfileComponent = () => {
  const { user, token } = useUser();
  const { id } = useParams();

  const [isCurrentUser, setIsCurrentUser] = useState(false);
  const [responseUser, setResponseUser] = useState(null);
  const [posts, setPosts] = useState([]);
  const [followers, setFollowers] = useState([]);
  const [following, setFollowing] = useState([]);
  const [visible, setVisible] = useState(false);
  const [connection, setConnection] = useState(null);

  const headers = token ? { Authorization: `Bearer ${token}` } : {};

  const getUserInformation = async () => {
    try {
      const response = await api.get(`/user/${id}`, { headers });
      setResponseUser(response.data);
      if (response.data.userId === user.userId) {
        setIsCurrentUser(true);
      } else {
        setIsCurrentUser(false);
      }
    } catch (error) {
      console.error("Error getting user: ", error);
    }
  };

  const getPosts = async () => {
    try {
      const response = await api.get(`/posts/poster/${id}`, { headers });
      setPosts(response.data);
    } catch (error) {
      console.error("Error getting posts: ", error);
    }
  };

  const getFollowers = async () => {
    try {
      const response = await api.get(`/connection/${id}/followers`, { headers });
      setFollowers(response.data);
    } catch (error) {
      console.error("Error getting followers: ", error);
    }
  };

  const getFollowing = async () => {
    try {
      const response = await api.get(`/connection/${id}/following`, { headers });
      setFollowing(response.data);
    } catch (error) {
      console.error("Error getting following: ", error);
    }
  };

  const getConnectionStatus = async () => {
    try {
      const response = await api.get(
        `/connection/${user.userId}/isFollowing/${responseUser.userId}`,
        { headers }
      );
      setConnection(response.data);
    } catch (error) {
      console.error("Error getting connection status: ", error);
    }
  };

  useEffect(() => {
    getUserInformation();
    getPosts();
    getFollowers();
    getFollowing();
  }, [id, user]);

  useEffect(() => {
    if (!isCurrentUser && responseUser) {
      getConnectionStatus();
    }
  }, [isCurrentUser, responseUser]);

  if (!responseUser) {
    return <div>Loading...</div>;
  }

  const hideContent = responseUser.private && !connection;

  return (
    <div className="max-w-screen-xl mx-auto p-6 bg-white shadow-md rounded-md space-y-6">
      <div>
        <h2 className="text-2xl font-bold text-[#003a92]">{responseUser.username}</h2>
        <h3 className="text-lg text-gray-700">
          {responseUser.firstName} {responseUser.lastName}
        </h3>
      </div>
      <ConnectionDisplay followers={followers} following={following} />
      {isCurrentUser ? (
        <>
          <button
            onClick={() => setVisible(true)}
            className="w-full shadow-xl py-2.5 px-4 text-sm tracking-wide rounded-md text-white bg-[#002e74] hover:bg-[#004dbd] focus:outline-none"
          >
            Profile Management
          </button>
          <Modal isOpen={visible}>
          <div className="bg-[rgba(0,46,116,0.15)]flex items-center justify-center bg-opacity-15 z-50">
          <div className="w-[50vw] h-[75vh] mx-auto p-5 rounded-lg bg-white shadow-lg relative">
              <button
                className="absolute top-10 left-10 text-[#b32525] font-bold text-lg hover:underline cursor-pointer focus:outline-none"
                onClick={() => setVisible(false)}>X</button><br/>
              <div className="flex flex-col items-center h-full">
                <div className="font-exo">
                  <ProfileManagement />
                </div>
              </div>
              </div>
            </div>
          </Modal>
          <PostList posts={posts} setPosts={setPosts} />
        </>
      ) : hideContent ? (
        <>
          <ConnectionManagement
            displayUser={responseUser}
            getFollowers={getFollowers}
            getFollowing={getFollowing}
            connection={connection}
            setConnection={setConnection}
          /><br/>
          <p>This user's profile is private, only approved followers can view their posts and personal details.</p>
        </>
      ) : (
        <>
          <h3>Bio: {responseUser.bio}</h3>
          <ConnectionManagement
            displayUser={responseUser}
            getFollowers={getFollowers}
            getFollowing={getFollowing}
            connection={connection}
            setConnection={setConnection}
          /><br/>
          <PostList posts={posts} setPosts={setPosts} />
        </>
      )}
      
    </div>
  );
};

export default UserProfileComponent;
