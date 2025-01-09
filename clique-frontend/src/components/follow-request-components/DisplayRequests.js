const DisplayRequests = ({ requests, approveHandler, denyHandler }) => {

    return(
        <>
            {
                requests.length > 0 ? (
                    requests.map((request) =>
                    <div key={request.requestId}>
                        <span>{request.requester.username} wants to follow you.</span>
                        <button onClick={()=>approveHandler(request.requesterId)}>Approve</button>
                        <button onClick={()=>denyHandler(request.requesterId)}>Deny</button>
                        <br/>
                    </div>
                    )
                ) : (
                    <p>No pending follow requests.</p>
                )
            }
        </>
    )

}

export default DisplayRequests;