import axios from "axios";
import { useEffect, useState } from "react";
import { useUser } from "../UserContext";
import { useParams } from "react-router-dom";
import ActiveUserComponent from "./ActiveUserComponent";
import ViewUserComponent from "./ViewUserComponent";

axios.defaults.withCredentials = true;

const UserProfileComponent = () => {

    const { user, token } = useUser();
    const { id } = useParams();

    // flag to determine if the requested user profile is the logged in user
    const [isCurrentUser, setIsCurrentUser] = useState(false);

    // state to store user information
    const [responseUser, setResponseUser] = useState(null);

    // states used for child component rendering
    const [posts, setPosts] = useState([]);
    const [followers, setFollowers] = useState([]);
    const [following, setFollowing] = useState([]);

    const headers = token ? { Authorization: `Bearer ${token}` } : {};


    const getUserInformation = async () => {
        try {
            const response = await axios.get(`http://3.82.150.19:8080/user/${id}`, { headers });
            setResponseUser(response.data);
            if (response.data.userId === user.userId) {
                setIsCurrentUser(true);
            } else {
                setIsCurrentUser(false);
            }
        } catch (error) {
            console.error('Error getting user: ', error);
        }
    }

    const getPosts = async () => {
        try {
            const response = await axios.get(`http://3.82.150.19:8080/posts/poster/${id}`, { headers });
            setPosts(response.data);
        } catch (error) {
            console.error("Error getting posts: ", error);
        }
    }

    const getFollowers = async() => {
        try {
            const response = await axios.get(`http://3.82.150.19:8080/connection/${id}/followers`, { headers });
            setFollowers(response.data);
        } catch (error) {
            console.error("Error getting followers: ", error);
        }
    }

    const getFollowing = async() => {
        try {
            const response = await axios.get(`http://3.82.150.19:8080/connection/${id}/following`, { headers });
            setFollowing(response.data);
        } catch (error) {
            console.error("Error getting followers: ", error);
        }
    }

    useEffect(() => {
        getUserInformation();
        getPosts();
        getFollowers();
        getFollowing();
    }, [id, user, posts, followers, following]);


    if (!responseUser) {
        return <div>Loading...</div>; // Show a loading message while data is being fetched
    }

    if(isCurrentUser){
        return (
            <ActiveUserComponent
                displayUser={responseUser}
                posts={posts}
                followers={followers}
                following={following}
            />
        );
    } else {
        return (
            <ViewUserComponent
                displayUser={responseUser}
                posts={posts}
                followers={followers}
                following={following}
                getFollowers={getFollowers}
                getFollowing={getFollowing}
            />
        );
    }

}

export default UserProfileComponent;