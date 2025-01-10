import { useState } from "react";
import ProfileManagement from "./ProfileManagement";
import ConnectionDisplay from "./ConnectionDisplay";
import Modal from 'react-modal';
import PostList from "../post-components/PostList";

const ActiveUserComponent = ({displayUser, posts, followers, following, setPosts}) => {

    const [visible, setVisible] = useState(false);

    return(
        <>  
            <h2>{displayUser.username}</h2>
            <h3>{displayUser.firstName} {displayUser.lastName}</h3>
            <h3>Bio: {displayUser.bio}</h3>
            <ConnectionDisplay followers={followers} following={following} /><br/>
            <button onClick={() => setVisible(true)}>Profile Management</button>
            <Modal isOpen={visible}>
                <div>
                    <button onClick={() => setVisible(false)}>Close</button>
                    <ProfileManagement />
                </div>   
            </Modal>
            <PostList posts={posts} setPosts={setPosts} />
           
        </>
    )

}


export default ActiveUserComponent;