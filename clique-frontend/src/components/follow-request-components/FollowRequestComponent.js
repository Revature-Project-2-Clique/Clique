import api from "../../service/api";
import axios from "axios";
import { useEffect, useState } from "react";
import { useUser } from "../UserContext";
import DisplayRequests from "./DisplayRequests";

axios.defaults.withCredentials = true;

const FollowRequestComponent = () => {

    const { user, token } = useUser();
    const [requests, setRequests] = useState([]);
    const [loading, setLoading] = useState(false);

    const getHeaders = token ? { Authorization: `Bearer ${token}`} : {};
    const headers = token ? { Authorization: `Bearer ${token}`, "Content-Type": "application/json"} : {};

    const getRequests = async () => {
        setLoading(true);
        try {
            // get follow requests
            const response = await api.get("connection/get-requests", { headers });
            // get requester user DTOs
            const requestsWithUserData = await Promise.all(
                response.data.map(async (request) => {
                    const userResponse = await api.get(`/user/${request.requesterId}`, { headers });
                    return { ...request, requester: userResponse.data };
                })
            );
            setRequests(requestsWithUserData);
        } catch (error) {
            console.error("Error getting follow requests: ", error)
        } finally {
            setLoading(false);
        }
    }

    const approveHandler = async (requestUserId) => {
        try {
            await api.post("connection/approve-request", requestUserId, { headers });
            getRequests();
        } catch (error) {
            console.error("Error approving follow request: ", error);
        }
    }

    const denyHandler = async (requestUserId) => {
        try {
            await api.post("connection/delete-request", requestUserId, { headers });
            getRequests();
        } catch (error) {
            console.error("Error denying follow request: ", error);
        }
    }

    useEffect(()=>{
        getRequests();
    },[]);

    if (loading) return <p>Loading follow requests...</p>;
    
    return(
        <>
        <DisplayRequests requests={requests} approveHandler={approveHandler} denyHandler={denyHandler}/>
        </>
    )
}

export default FollowRequestComponent;