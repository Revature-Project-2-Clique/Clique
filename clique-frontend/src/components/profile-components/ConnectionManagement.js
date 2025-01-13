import { useEffect, useState } from "react";
import { useUser } from "../UserContext";
import api from "../../service/api";

api.defaults.withCredentials = true;

const ConnectionManagement = ({displayUser, getFollowers, getFollowing, connection, setConnection}) => {

    const { user, token } = useUser();
    const [loading, setLoading] = useState(true);
    const [submitted, setSubmitted] = useState(false);

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
            if (displayUser.private) {
                try {
                    await api.post("/connection/follow-request", displayUser.userId, { headers });
                    setSubmitted(true);
                } catch(error) {
                    console.error("Error making follow request: ", error);
                }
            } else {
                try {
                    await api.post("/connection/follow", displayUser.userId, { headers });
                    setConnection(true);
                    getFollowers();
                    getFollowing();
                } catch (error) {
                    console.error("Error following user: ", error);
                }
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

    const getButton = () => {
        if (displayUser.private && !connection) {
            return submitted ? "Request Submitted" : "Request to Follow";
        } else {
            return connection ? "Unfollow" : "Follow";
        }
    }

    if (loading) {
        return <p>Loading...</p>;
      }

    return (
        <>
            <button 
                className={`w-full shadow-xl py-2.5 px-4 text-sm tracking-wide rounded-md text-white focus:outline-none ${connection 
                    ? "bg-[#002e74] hover:bg-[#004dbd]" 
                    : "bg-[#004dbd] hover:bg-[#002e74]"
                }`}
                onClick={() => { submitHandler() }}
                disabled={submitted}
            >
                {getButton()}
            </button>
        </>
      );
    
    

}

export default ConnectionManagement;