import api from "../../service/api";
import { useState } from "react";
import { useUser } from "../UserContext";

const FollowRequestComponent = () => {

    const { user, token } = useUser();
    const [requests, setRequests] = useState([]);

    const headers = token ? { Authorization: `Bearer ${token}`, "Content-Type": "application/json" } : {};

    return(
        <>
        <p>Here's where requests would go..</p>
        </>
    )
}

export default FollowRequestComponent;