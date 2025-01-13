import React from 'react'
import { useState } from 'react';

const CommentComponent = ({comment, onDelete, onEdit}) => {
  const [isEditing, setIsEditing] = useState(false);
  const [editText, setEditText] = useState(comment.comment_text);

  const handleSaveEdit = () => {
    if (editText.trim()) {
      onEdit(editText);
      setIsEditing(false);
    }
  };

  return (
    <div className="border-b border-gray-100 pb-2 mb-2">
      <p><strong className="text-sm text-[#003a92]">{comment.username}</strong></p>
      <p><small className="text-xs text-gray-500">{new Date(comment.posted_Time).toLocaleString()}</small></p>
      {isEditing ? (
        <div className="space-y-2 mt-2">
          <input
            type="text"
            value={editText}
            onChange={(e) => setEditText(e.target.value)}
            className="w-full border border-gray-300 rounded-md p-1 text-sm outline-none focus:border-blue-600"
          />
          <div className="space-x-2">
            <button 
              onClick={handleSaveEdit}
              className="text-xs text-[#003a92] font-medium hover:underline"
            >
              Save
            </button>
            <button 
              onClick={() => setIsEditing(false)}
              className="text-xs text-[#003a92] font-medium hover:underline"
            >
              Cancel
            </button>
          </div>
        </div>
      ) : (
        <p className="text-sm text-gray-700 mt-1">{comment.comment_text}</p>
      )}
      {!isEditing && onEdit && (
        <div className="space-x-2 mt-2">
          <button 
            onClick={() => setIsEditing(true)}
            className="text-xs text-[#003a92] font-medium hover:underline"
          >
            Edit
          </button>
          <button 
            onClick={() => onDelete(comment.comment_id)}
            className="text-xs text-[#003a92] font-medium hover:underline"
          >
            Delete
          </button>
        </div>
      )}
    </div>
  )
}

export default CommentComponent
