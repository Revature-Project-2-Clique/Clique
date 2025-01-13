import { Link } from "react-router-dom";

const DisplayRequests = ({ requests, approveHandler, denyHandler }) => {

    return(
        <div className="max-w-screen-xl mx-auto p-6 bg-white shadow-md rounded-md space-y-6 flex items-center justify-center">
            {
                requests.length > 0 ? (
                    requests.map((request) =>
                    <div key={request.requestId}>
                        <Link to={`/user/${request.requesterId}`} className="text-xl font-bold text-[#003a92] hover:underline">
                            {request.requester.username}    
                        </Link>
                        <span className="text-xl font-bold text-[#78716c]"> wants to follow you:</span>
                        <span 
                            onClick={()=>approveHandler(request.requesterId)}
                            className="cursor-pointer pl-2  text-[#003a92] hover:underline"
                        >Approve</span>
                        <span 
                            onClick={()=>denyHandler(request.requesterId)}
                            className="cursor-pointer pl-2  text-[#003a92] hover:underline"
                        >Deny</span>
                        <br/>
                    </div>
                    )
                ) : (
                    <p>No pending follow requests.</p>
                )
            }
        </div>
    )

}

export default DisplayRequests;