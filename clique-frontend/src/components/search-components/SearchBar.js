import React, { useState, useContext, useEffect, useCallback } from 'react';
import { useUser } from "../UserContext";
import debounce from 'lodash.debounce';
import api, { searchAllUsers, searchPosts } from '../../service/api';
import axios from 'axios';

axios.defaults.withCredentials = true;

function SearchBar() {
    const { loggedInUser, token } = useUser();
    const [users, setUsers] = useState([]);
    const [posts, setPosts] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [filteredUsers, setFilteredUsers] = useState([]);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const [loadingPosts, setLoadingPosts] = useState(false);

    const headers = token ? { Authorization: `Bearer ${token}`, "Content-Type": "application/json" } : {};


    // Fetch all users on component mount
    useEffect(() => {
        const fetchUsers = async () => {

            try {
                setLoading(true);
                const res = await searchAllUsers(headers);

                setUsers(res);
            } catch (err) {
                console.error(err);
                setError('Failed to load users.');
            } finally {
                setLoading(false);
            }
        };



        fetchUsers();
    }, []);

    const handleInputChange = debounce((value) => {
        setSearchTerm(value);
        const lowercasedValue = value.toLowerCase();
        if (value.trim() === '') {
            setFilteredUsers([]); // Clear results when input is empty
            return;
        }
        const filtered = users.filter((user) =>
            user.username.toLowerCase().includes(lowercasedValue)
        ).slice(0, 10);
        setFilteredUsers(filtered);
    }, 300);

    const handleSearchPosts = async () => {
        if (!searchTerm.trim()) {
            setPosts([]); // Clear post results if search term is empty
            return;
        }
        try {
            setLoadingPosts(true);
            const res = await searchPosts(searchTerm, headers); // Assuming searchPosts fetches posts by query
            setPosts(res || []);
        } catch (err) {
            console.error(err);
            setError('Failed to load posts.');
        } finally {
            setLoadingPosts(false);
        }
    };

    return (
        <div className="search-container">
            <h2>Search</h2>
            <div className='search-input-container'>
                <input
                    type="text"
                    placeholder="Search users or posts..."
                    onChange={(e) => handleInputChange(e.target.value)}
                    className="search-input"
                />
                <button onClick={handleSearchPosts} className="search-button">
                    Search Posts
                </button>
            </div>



            <div className="search-user-section">
                <h2>Users</h2>
                {loading ? (
                    <p>Loading users...</p>
                ) : error ? (
                    <p className="error">{error}</p>
                ) : filteredUsers.length === 0 ? (
                    <p>No users found.</p>
                ) : (
                    filteredUsers.map((user) => (
                        <div key={user.userId} className="user-result">
                            <h3>{user.username}</h3>
                        </div>
                    ))
                )}
            </div>

            <div className="search-post-section">
                <h2>Posts</h2>
                {loadingPosts ? (
                    <p>Loading posts...</p>
                ) : posts.length === 0 ? (
                    <p>No posts found.</p>
                ) : (
                    posts.map((post) => (
                        <div key={post.postId} className="post-result">
                            <h3>{post.username}</h3>
                            <p>{post.postText}</p>
                            <p>Created at: {post.postedTime}</p>
                            <p>Likes: {post.likes}</p>
                        </div>
                    ))
                )}
            </div>

        </div>
    );
}

export default SearchBar;
