import React, { useState } from 'react'
import { useUser } from '../UserContext';
import api from '../../service/api';

const CreatePost = () => {

    const {user, token} = useUser();
    const [postText, setPostText] = useState("");

    const headers = token ? { "Authorization": `Bearer ${token}`, "Content-Type": "application/json" } : {};

    const createPost = async (postData) => {
        try {
            console.log(postData)
            await api.post(`/posts`, postData, {headers})
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
            await createPost(postData);
            setPostText("");

        }catch(err) {
            console.log(err);
            alert("Cannot create Post");
        }
    }
    return (
        <>
            <form onSubmit={handleSubmit}>
                <label
                    htmlFor="message"
                >
                    Create Post
                </label><br />
                <textarea
                    id="message"
                    rows="4"
                    value={postText}
                    onChange={(e)=>setPostText(e.target.value)}
                    placeholder="What's on your mind?"
                ></textarea><br />
                <button
                    type="submit"
                >
                    Post
                </button>
            </form>
        </>
    )
}

export default CreatePost