import React from 'react'
import CommentComponent from './CommentComponent'
import CommentInput from './CommentInput'

const CommentList = ({comments, onAddComment}) => {
  return (
    <>
        {comments && comments.length > 0 && (
            <div >
                <h6>Comments:</h6>
                {Array.from(comments).map((comment) => (
                    <CommentComponent key={comment.comment_id} comment={comment} />
                ))}
            </div>
        )}
        <CommentInput onAddComment={onAddComment} />
    </>
    
  )
}

export default CommentList