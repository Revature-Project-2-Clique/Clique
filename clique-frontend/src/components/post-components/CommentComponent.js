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
    <div>
        <p><strong>{comment.username}</strong></p>
        <p><small>{new Date(comment.posted_Time).toLocaleString()}</small></p>
        {isEditing ? (
          <div>
            <input
              type="text"
              value={editText}
              onChange={(e) => setEditText(e.target.value)}
            />
            <button onClick={handleSaveEdit}>Save</button>
            <button onClick={() => setIsEditing(false)}>Cancel</button>
          </div>
        ) : (
          <p>{comment.comment_text}</p>
        )}
        {!isEditing ? onEdit && (
        <>
          <button onClick={() => setIsEditing(true)}>Edit</button>
          <button onClick={() => onDelete(comment.comment_id)}>Delete</button>
        </>
      ): <></>}
    </div>
  )
}

export default CommentComponent