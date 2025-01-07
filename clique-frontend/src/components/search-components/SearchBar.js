import { useState } from "react";
import { useNavigate } from "react-router-dom";

const SearchBar = () => {
    const [query, setQuery] = useState("");
    const [isSearching, setIsSearching] = useState(false);
    const navigate = useNavigate();

    const handleSearch = async (e) => {
        e.preventDefault();
        if (query.trim() === "") {
            alert("Search query cannot be empty.");
        }
        navigate(`/search?query=${encodeURIComponent(query)}`);
    };

    return (
        <div>
            <form onSubmit={handleSearch} className="search-bar">
                <input
                    type="text"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    placeholder="Search users or posts..."
                    className="search-input"
                />
                <button type="submit" className="search-button" disabled={isSearching}>
                    {isSearching ? "Searching..." : "Search"}
                </button>
            </form>
        </div>
    );
};

export default SearchBar;
