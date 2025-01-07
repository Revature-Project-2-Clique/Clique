import { useEffect, useState } from "react";
import { useUser } from "../UserContext";
import api from "../../service/api";

api.defaults.withCredentials = true;

const ConnectionManagement = ({displayUser, getFollowers, getFollowing}) => {

    const { user, token } = useUser();
    const [connection, setConnection] = useState(null);
    const [loading, setLoading] = useState(true);

    const headers = token ? { Authorization: `Bearer ${token}`, "Content-Type": "application/json" } : {};

    const getConnectionStatus = async () => {
        try {
            const response = await api.get(`/connection/${user.userId}/isFollowing/${displayUser.userId}`, { headers });
            setConnection(response.data);
        } catch (error) {
            console.error("Error getting connection status: ", error)
        } finally {
            setLoading(false);
        }
    };

    useEffect(()=>{
        getConnectionStatus();
    },[]);

    const submitHandler = async () => {
        // if users are not connected, follow button is shown and clicking it will follow the user
        if(connection === false){
            try {
                await api.post("/connection/follow", displayUser.userId, { headers });
                setConnection(true);
                getFollowers();
                getFollowing();
            } catch (error) {
                console.error("Error following user: ", error);
            }
        } else {
            try {
                await api.post("/connection/unfollow", displayUser.userId, { headers });
                setConnection(false)
                getFollowers();
                getFollowing();
            } catch (error) {
                console.error("Error unfollowing user: ", error);
            }
        }
    };


    if (loading) {
        return <p>Loading...</p>;
      }

    return (
        <>
          <button onClick={()=>{submitHandler()}}>
            {connection ? "Unfollow" : "Follow"}
          </button>
        </>
      );
    
    

}

export default ConnectionManagement;