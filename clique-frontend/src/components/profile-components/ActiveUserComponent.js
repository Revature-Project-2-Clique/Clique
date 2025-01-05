import { useState } from "react";
import ProfileManagement from "./ProfileManagement";
import ConnectionDisplay from "./ConnectionDisplay";
import Modal from 'react-modal';

const ActiveUserComponent = ({displayUser, posts, followers, following}) => {

    const [visible, setVisible] = useState(false);

    return(
        <>  
            <h2>{displayUser.username}</h2>
            <h3>{displayUser.firstName} {displayUser.lastName}</h3>
            <ConnectionDisplay followers={followers} following={following} />
            <button onClick={() => setVisible(true)}>Profile Management</button>
            <Modal isOpen={visible}>
                <div>
                    <button onClick={() => setVisible(false)}>Close</button>
                    <ProfileManagement />
                </div>   
            </Modal>
           {/* This where the users posts will go once post components are made */}
        </>
    )

}


export default ActiveUserComponent;