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
    <form onSubmit={handleSubmit} className="flex space-x-2 mt-2">
      <input 
        type="text" 
        value={commentText} 
        onChange={(e) => setCommentText(e.target.value)} 
        placeholder="Add a comment..." 
        className="flex-1 border-b border-gray-300 focus:border-blue-600 outline-none p-2"
      />
      <button 
        type="submit" 
        className="text-sm text-[#003a92] font-bold hover:underline"
      >
        Post
      </button>
    </form>
  )
}

export default CommentInput
