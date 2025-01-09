import ConnectionDisplay from "./ConnectionDisplay";
import ConnectionManagement from "./ConnectionManagement";

const ViewUserComponent = ({displayUser, posts, followers, following, getFollowers, getFollowing}) => {

    return(
        <>
            <h2>{displayUser.username}</h2>
            <h3>{displayUser.firstName} {displayUser.lastName}</h3>
            <h3>Bio: {displayUser.bio}</h3>
            <ConnectionDisplay followers={followers} following={following} />
            <ConnectionManagement displayUser={displayUser} getFollowers={getFollowers} getFollowing={getFollowing} /><br/>
            {/* This where the users posts will go once post components are made */}
        </>
    )

}

export default ViewUserComponent;