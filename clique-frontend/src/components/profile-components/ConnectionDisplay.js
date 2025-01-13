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
                <div className="flex space-x-4 text-sm text-gray-800">
        <span
          onClick={() => displayList("followers")}
          className="cursor-pointer text-[#003a92] hover:underline"
        >
          Followers: {followers.length}
        </span>
        <span
          onClick={() => displayList("following")}
          className="cursor-pointer text-[#003a92] hover:underline"
        >
          Following: {following.length}
        </span>
      </div>
            <Modal isOpen={visible}>
                <div className="font-exo">
                    <button onClick={close}>Close</button>
                    <ConnectionList onLinkClick={close} connections={listType === "followers" ? followers : following} title={listType === "followers" ? "Followers:" : "Following:"} />
                </div>   
            </Modal>
        </>
    );
}

export default ConnectionDisplay;