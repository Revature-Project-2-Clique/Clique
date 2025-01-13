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
            <div className="bg-[rgba(0,46,116,0.1)]flex items-center justify-center bg-opacity-15 z-50">
              <div className="w-[50vw] h-[75vh] mx-auto p-5 rounded-lg bg-white shadow-lg relative">
                    <button
                      className="absolute top-10 left-10 text-[#b32525] font-bold text-lg hover:underline cursor-pointer focus:outline-none"
                      onClick={close}>X</button>
                    <div className="flex flex-col items-center h-full">
                      <div className="font-exo">
                        <ConnectionList onLinkClick={close} connections={listType === "followers" ? followers : following} title={listType === "followers" ? "Followers:" : "Following:"} />
                    </div>
                    </div>
                </div>
              </div>   
            </Modal>
        </>
    );
}

export default ConnectionDisplay;