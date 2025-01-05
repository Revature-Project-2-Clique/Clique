import { useState } from "react";
import ConnectionList from "./ConnectionList";
import Modal from "react-modal";

const ConnectionDisplay = ({followers, following}) => {

    const [visible, setVisible] = useState(false);

    // list type is either followers or following
    const [listType, setListType] = useState("");

    const displayList = (type) => {
        setListType(type);
        setVisible(true);
    }

    const close = () => {
        setVisible(false);
        setListType("");
    }

    return (
        <>
            <span onClick={() => displayList("followers")}>Followers: {followers.length}</span><br/>
            <span onClick={() => displayList("following")}>Following: {following.length}</span>
            <Modal isOpen={visible}>
                <div>
                    <button onClick={close}>Close</button>
                    <ConnectionList connections={listType === "followers" ? followers : following} title={listType === "followers" ? "Followers:" : "Following:"} />
                </div>   
            </Modal>
        </>
    );
}

export default ConnectionDisplay;