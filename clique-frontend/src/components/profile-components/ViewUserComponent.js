import ConnectionDisplay from "./ConnectionDisplay";
import ConnectionManagement from "./ConnectionManagement";
import PostList from "../post-components/PostList";
import { useEffect, useState } from "react";
import { useUser } from "../UserContext";
import api from "../../service/api";

const ViewUserComponent = ({displayUser, posts, followers, following, getFollowers, getFollowing, setPosts}) => {

    const { user, token } = useUser();
    const [connection, setConnection] = useState(null);

    const hideContent = displayUser.private && !connection;

    const headers = token ? { Authorization: `Bearer ${token}`, "Content-Type": "application/json" } : {};

    const getConnectionStatus = async () => {
        try {
            const response = await api.get(`/connection/${user.userId}/isFollowing/${displayUser.userId}`, { headers });
            setConnection(response.data);
        } catch (error) {
            console.error("Error getting connection status: ", error)
        }
    };

    useEffect(()=>{
        getConnectionStatus();
    },[user, displayUser]);

    return(
        <>
            {hideContent ? (
                <>
                    <h2>{displayUser.username}</h2>
                    <ConnectionManagement displayUser={displayUser} getFollowers={getFollowers} getFollowing={getFollowing} connection={connection} setConnection={setConnection} /><br/>
                    <p>This user's profile is private, only approved followers can view their posts and personal details.</p>
                </>
                
            ) : (

                <>
                    <h2>{displayUser.username}</h2>
                    <h3>{displayUser.firstName} {displayUser.lastName}</h3>
                    <ConnectionDisplay followers={followers} following={following} /><br/>
                    <ConnectionManagement displayUser={displayUser} getFollowers={getFollowers} getFollowing={getFollowing} connection={connection} setConnection={setConnection} /><br/>
                    <PostList posts={posts} setPosts={setPosts} />
                </>
            )}
        </>
    )

}

export default ViewUserComponent;