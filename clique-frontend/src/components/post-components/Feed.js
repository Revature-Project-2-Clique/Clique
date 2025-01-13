import React, { useEffect, useState } from 'react'
import api from "../../service/api";
import { useUser } from '../UserContext';
import CreatePost from './CreatePost';
import PostList from './PostList'

const Feed = ({explore}) => {
    const {user, token} = useUser();
    const [posts, setPosts] = useState([]);
    const [loading, setLoading] = useState(true);

    const headers = token ? { "Authorization": `Bearer ${token}` } : {};

    useEffect(() => {
        const loadFeed = async() => {
            try {
                const response = explore 
                    ? await api.get("/posts/explore", {headers}) 
                    : await api.get("/posts/feed", {headers});
                setPosts(response.data);
                setLoading(false);
            } catch (err) {
                console.error("Error loading feed: ", err);
            }
        }
        loadFeed();
    }, []);

    const addNewPost = (newPost) => {
        setPosts((prevPosts) => [newPost, ...prevPosts]);
    }

    return (
        <div className="max-w-screen-xl mx-auto p-6 rounded-md space-y-6">
            {explore ? null : <CreatePost addNewPost={addNewPost} />}
            {loading ? (
                <p className="text-gray-600">Loading posts...</p>
            ) : posts && posts.length > 0 ? (
                <PostList posts={posts} setPosts={setPosts}/>
            ) : (
                <p className="text-gray-600">No posts available</p>
            )}
        </div>
    )
}

export default Feed
