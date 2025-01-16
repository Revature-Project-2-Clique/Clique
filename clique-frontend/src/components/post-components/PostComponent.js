import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const PostComponent = ({ 
  poster, 
  profilePhotoUrl,
  userId, 
  createdAt, 
  content, 
  imageUrl, 
  videoUrl, 
  onDeletePost, 
  onEditPost 
}) => {
  const [isEditing, setIsEditing] = useState(false);
  const [editText, setEditText] = useState(content);

  const handleSaveEdit = () => {
    if (editText.trim()) {
      onEditPost(editText);
      setIsEditing(false);
    }
  };

  const hasMedia = Boolean(imageUrl || videoUrl);

  return (
    <div className="mb-4">
      <Link to={`/user/${userId}`} className="username-link">
        <div className="flex items-center space-x-2">
          <img 
            src={profilePhotoUrl || 'https://example.com/default-profile.jpg'} 
            alt={`${poster}'s profile`} 
            className="w-8 h-8 rounded-full object-cover"
          />
          <h4 className="text-xl font-bold text-[#003a92]">{poster}</h4>
        </div>
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
      <div 
        className={`media-container mb-2 w-full max-w-md h-64 rounded-md flex items-center justify-center overflow-hidden ${
          hasMedia ? 'bg-white' : 'bg-gray-100'
        } shadow-md`}
      >
        {imageUrl ? (
          <img 
            src={imageUrl} 
            alt="Post media" 
            className="object-contain w-full h-full" 
          />
        ) : videoUrl ? (
          <video controls className="object-contain w-full h-full">
            <source src={videoUrl} type="video/mp4" />
            Your browser does not support the video tag.
          </video>
        ) : (
          <p className="text-2xl font-bold text-center text-black px-4">
            {content}
          </p>
        )}
      </div>

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
        hasMedia && (
          <div className="bg-gray-50 p-4 rounded-md shadow-sm mt-2 text-gray-800">
            {content}
          </div>
        )
      )} 
    </div>
  );
}

export default PostComponent;