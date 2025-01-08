import React from 'react'
import CommentComponent from './CommentComponent'

const CommentList = ({comments}) => {
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
    </>
    
  )
}

export default CommentList