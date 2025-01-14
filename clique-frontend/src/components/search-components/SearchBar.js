import React, { useState, useContext, useEffect } from 'react';
import { useUser } from "../UserContext";
import debounce from 'lodash.debounce';
import api, { searchAllUsers, searchPosts } from '../../service/api';
import axios from 'axios';
import PostList from '../post-components/PostList';
import { Link } from 'react-router-dom';

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
            setFilteredUsers([]);
            return;
        }
        const filtered = users.filter((user) =>
            user.username.toLowerCase().includes(lowercasedValue)
        ).slice(0, 10);
        setFilteredUsers(filtered);
    }, 300);

    const handleSearchPosts = async () => {
        if (!searchTerm.trim()) {
            setPosts([]);
            return;
        }
        try {
            setLoadingPosts(true);
            const res = await searchPosts(searchTerm, headers);
            setPosts(res || []);
        } catch (err) {
            console.error(err);
            setError('Failed to load posts.');
        } finally {
            setLoadingPosts(false);
        }
    };

    return (
        <div className="max-w-screen-xl mx-auto p-6 bg-white shadow-md rounded-md space-y-6">
            <h2 className="text-2xl font-bold text-[#003a92]">Search</h2>
            <div className="flex space-x-2">
                <input
                    type="text"
                    placeholder="Search users or posts..."
                    onChange={(e) => handleInputChange(e.target.value)}
                    className="flex-1 p-2 border border-gray-300 rounded-md focus:border-blue-600 outline-none"
                />
                <button 
                    onClick={handleSearchPosts}
                    className="py-2 px-4 bg-[#002e74] text-white rounded-md hover:bg-[#004dbd] focus:outline-none"
                >
                    Search Posts
                </button>
            </div>

            <div>
                <h2 className="text-xl font-bold text-[#003a92]">Users</h2>
                {loading ? (
                    <p className="text-gray-600">Loading users...</p>
                ) : error ? (
                    <p className="text-red-500">{error}</p>
                ) : filteredUsers.length === 0 ? (
                    <p className="text-gray-600">No users found.</p>
                ) : (
                    filteredUsers.map((user) => (
                        <div key={user.userId} className="py-2">
                            <Link 
                                to={`/user/${user.userId}`}
                                className="text-sm text-[#003a92] hover:underline"
                            >
                                {user.username}
                            </Link>
                        </div>
                    ))
                )}
            </div>

            <div>
                <h2 className="text-xl font-bold text-[#003a92]">Posts</h2>
                {loadingPosts ? (
                    <p className="text-gray-600">Loading posts...</p>
                ) : (
                    <PostList posts={posts} setPosts={setPosts}/>
                )}
            </div>
        </div>
    );
}

export default SearchBar;
