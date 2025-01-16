import React from 'react'
import { useState } from 'react';
import { Link } from 'react-router-dom';

const PostComponent = ({ poster, userId, createdAt, content, imageUrl, videoUrl, onDeletePost, onEditPost }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [editText, setEditText] = useState(content);

  const handleSaveEdit = () => {
    if (editText.trim()) {
      onEditPost(editText);
      setIsEditing(false);
    }
  };

  return (
    <div className="mb-4">
      <Link to={`/user/${userId}`} className="username-link">
        <h4 className="text-xl font-bold text-[#003a92]">{poster}</h4>
      </Link>
      <small className="text-gray-600">{new Date(createdAt).toLocaleString()}</small>
      {!isEditing && onDeletePost && (
        <div className="space-x-2 mt-2">
          <button 
            onClick={() => setIsEditing(true)} 
            className="text-sm text-[#003a92] font-bold hover:underline"
          >
            Edit
          </button>
          <button 
            onClick={onDeletePost} 
            className="text-sm text-[#003a92] font-bold hover:underline"
          >
            Delete
          </button>
        </div>
      )}
      <hr className="my-2"/>
      {imageUrl && (
        <div className="mb-2">
          <img src={imageUrl} alt="Post image" className="max-w-full h-auto rounded-md" />
        </div>
      )}
      {videoUrl && (
        <div className="mb-2">
          <video controls className="max-w-full h-auto rounded-md">
            <source src={videoUrl} type="video/mp4" />
          </video>
        </div>
      )}
      {isEditing ? (
        <div className="space-y-2">
          <textarea
            rows="4"
            value={editText}
            onChange={(e) => setEditText(e.target.value)}
            className="w-full border border-gray-300 rounded-md p-2 outline-none focus:border-blue-600"
          />
          <div className="space-x-2">
            <button 
              onClick={handleSaveEdit}
              className="text-sm text-[#003a92] font-bold hover:underline"
            >
              Save
            </button>
            <button 
              onClick={() => setIsEditing(false)}
              className="text-sm text-[#003a92] font-bold hover:underline"
            >
              Cancel
            </button>
          </div>
        </div>
      ) : (
        <div className="bg-gray-50 p-4 rounded-md shadow-sm mt-2 text-gray-800">
          {content}
        </div>
      )} 
    </div>
  )
}

export default PostComponent;
