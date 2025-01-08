import React from 'react'
import { useState } from 'react';

const CommentInput = ({onAddComment}) => {
    const [commentText, setCommentText] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!commentText.trim()) {
            alert("Empty post");
            return;
        } else {
            onAddComment(commentText);
            setCommentText('');
        }

    }

    return (
        <form onSubmit={handleSubmit}>
            <input 
                type="text" 
                value={commentText} 
                onChange={(e) => setCommentText(e.target.value)} 
                placeholder="Add a comment..." 
            />
            <button type="submit">Post</button>
        </form>
    )
}

export default CommentInput