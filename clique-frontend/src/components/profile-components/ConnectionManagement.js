import { useEffect, useState } from "react";
import { useUser } from "../UserContext";
import api from "../../service/api";

api.defaults.withCredentials = true;

const ConnectionManagement = ({displayUser, getFollowers, getFollowing, connection, setConnection}) => {

    const { user, token } = useUser();
    const [submitted, setSubmitted] = useState(false);

    const headers = token ? { Authorization: `Bearer ${token}`, "Content-Type": "application/json" } : {};

    const submitHandler = async () => {
        // if users are not connected, follow button is shown and clicking it will follow the user
        if(connection === false){
            // if users are not connected, and the display user is private, a follow request is made
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

    // function to determine button text to be rendered
    const getButton = () => {
        if (displayUser.private) {
            return submitted ? "Request Submitted" : "Request to Follow";
        } else {
            return connection ? "Unfollow" : "Follow";
        }
    }

    return (
        <>
          <button onClick={()=>{submitHandler()}} disabled={submitted}>
            {getButton()}
          </button>
        </>
      );
    
}

export default ConnectionManagement;