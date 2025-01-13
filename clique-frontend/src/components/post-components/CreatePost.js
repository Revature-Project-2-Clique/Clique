import React, { useState } from 'react'
import { useUser } from '../UserContext';
import api from '../../service/api';

const CreatePost = ({addNewPost}) => {

    const {user, token} = useUser();
    const [postText, setPostText] = useState("");

    const headers = token ? { "Authorization": `Bearer ${token}`, "Content-Type": "application/json" } : {};

    const createPost = async (postData) => {
        try {
            const response = await api.post(`/posts`, postData, {headers})
            return response.data;
        } catch (err) {
            console.error("Error creating post", err)
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!postText.trim()) {
            alert("Empty post");
            return;
        }
        try{
            const postData = {postText};
            const newPost = await createPost(postData);
            addNewPost(newPost);
            setPostText("");

        }catch(err) {
            console.log(err);
            alert("Cannot create Post");
        }
    }
    return (
        <div className="bg-white p-6 shadow-md rounded-md space-y-6">
            <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                    <label htmlFor="message" className="text-gray-800 text-xs block mb-2">
                        Create Post
                    </label>
                    <textarea
                        id="message"
                        rows="4"
                        value={postText}
                        onChange={(e)=>setPostText(e.target.value)}
                        placeholder="What's on your mind?"
                        className="w-full text-gray-800 text-sm border border-gray-300 focus:border-blue-600 p-2 outline-none rounded-md"
                    ></textarea>
                </div>
                <button
                    type="submit"
                    className="w-full shadow-xl py-2.5 px-4 text-sm tracking-wide rounded-md text-white bg-[#002e74] hover:bg-[#004dbd] focus:outline-none"
                >
                    Post
                </button>
            </form>
        </div>
    )
}

export default CreatePost
