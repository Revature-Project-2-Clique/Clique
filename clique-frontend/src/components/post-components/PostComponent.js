import React from 'react'
import { useState } from 'react';

const PostComponent = ({poster, createdAt, content, onDeletePost, onEditPost}) => {
    const [isEditing, setIsEditing] = useState(false);
    const [editText, setEditText] = useState(content);

    const handleSaveEdit = () => {
        if (editText.trim()) {
            onEditPost(editText);
            setIsEditing(false);
        }
    };

    return (
        <>
            <h4>{poster}</h4>
            <small>{new Date(createdAt).toLocaleString()}</small>
            {!isEditing 
                ? onDeletePost && (
                    <>
                        <button onClick={() => setIsEditing(true)}>Edit</button>
                        <button onClick={onDeletePost}>Delete</button>
                    </>
                ) : (
                    <></>
                )
            }
            <hr/>
            {isEditing ? (
                <div>
                    <textarea
                        rows="4"
                        value={editText}
                        onChange={(e) => setEditText(e.target.value)}
                    />
                    <button onClick={handleSaveEdit}>Save</button>
                    <button onClick={() => setIsEditing(false)}>Cancel</button>
                </div>
            ) : (
                <p>{content}</p>
            )} 
        </>
    )
}

export default PostComponent