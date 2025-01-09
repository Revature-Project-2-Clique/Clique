import ConnectionDisplay from "./ConnectionDisplay";
import ConnectionManagement from "./ConnectionManagement";
import PostList from "../post-components/PostList";

const ViewUserComponent = ({displayUser, posts, followers, following, getFollowers, getFollowing, setPosts}) => {

    return(
        <>
            <h2>{displayUser.username}</h2>
            <h3>{displayUser.firstName} {displayUser.lastName}</h3>
            <h3>Bio: {displayUser.bio}</h3>
            <ConnectionDisplay followers={followers} following={following} />
            <ConnectionManagement displayUser={displayUser} getFollowers={getFollowers} getFollowing={getFollowing} /><br/>
            <PostList posts={posts} setPosts={setPosts} />
        </>
    )

}

export default ViewUserComponent;