import React, { useState, useEffect } from "react";
import { useSearchParams, Link } from "react-router-dom";
import { searchUsers, searchPosts } from "../../service/api";
import axios from "axios";
import { useUser } from "../UserContext";

axios.defaults.withCredentials = true;

const SearchResults = () => {
    const [searchParams] = useSearchParams();
    const {user, token } = useUser();
    const query = searchParams.get("query");
    const [userResults, setUserResults] = useState([]);
    const [postResults, setPostResults] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const headers = token ? { Authorization: `Bearer ${token}`, "Content-Type": "application/json" } : {};

    useEffect(() => {
        const fetchResults = async () => {
            try {
                setLoading(true);
                const [users, posts] = await Promise.all([
                    searchUsers(query, headers),
                    searchPosts(query, headers),
                ]);
                setUserResults(users);
                setPostResults(posts);
            } catch (err) {
                setError("Failed to fetch search results.");
            } finally {
                setLoading(false);
            }
        };

        fetchResults();
    }, [query]);

    if (loading) return <p>Loading...</p>;
    if (error) return <p style={{ color: "red" }}>{error}</p>;

    return (
        <div>
            <h1>Search Results for: "{query}"</h1>
            <div className="search-section">
                <h2>Users</h2>
                {userResults.length === 0 ? (
                    <p>No users found.</p>
                ) : (
                    userResults.map((user) => (
                        <div key={user.id} className="user-result">
                            <h3>{user.username}</h3>
                            <Link to={`/user/${user.id}`}>
                                <button>View Profile</button>
                            </Link>
                        </div>
                    ))
                )}
            </div>
            <div className="search-section">
                <h2>Posts</h2>
                {postResults.length === 0 ? (
                    <p>No posts found.</p>
                ) : (
                    postResults.map((post) => (
                        <div
                            key={post.id}
                            className="post-result"
                            style={{
                                border: "1px solid #ccc",
                                margin: "10px",
                                padding: "10px",
                            }}
                        >
                            <h3>{post.content}</h3>
                            <small>
                                <strong>By:</strong> {post.user.username || "Unknown"} <br />
                                <strong>Posted on:</strong>{" "}
                                {new Date(post.createdAt).toLocaleString() || "Unknown Date"}
                                <br />
                                <strong>Likes: </strong>
                                {post.likes || 0}
                            </small>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
};

export default SearchResults;
