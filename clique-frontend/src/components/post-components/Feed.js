import React, { useEffect, useState } from 'react'
import api from "../../service/api";
import { useUser } from '../UserContext';
import PostList from './PostList'

const Feed = () => {

    const {user, token} = useUser();
    const [posts, setPosts] = useState([]);

    const headers = token ? { "Authorization": `Bearer ${token}` } : {};

    useEffect(() => {
        const loadFeed = async() => {
            try {
                const response = await api.get("/posts/feed",{headers});
                setPosts(response.data);
            } catch (err) {
                console.error("Error loading feed: ", err);
            }
        }
        loadFeed();
    },[]);

    return (
        <>
            {
                posts ? (
                    <PostList posts={posts}/>
                ) : (
                    <p>Loading posts</p>
                )
            }
        </>
    )
}

export default Feed