import React from 'react'

const CommentComponent = ({comment}) => {
  return (
    <div>
        <p><strong>{comment.username}</strong></p>
        <p><small>{new Date(comment.posted_Time).toLocaleString()}</small></p>
        <p>{comment.comment_text}</p>
    </div>
  )
}

export default CommentComponent