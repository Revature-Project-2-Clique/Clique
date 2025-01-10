import React from 'react'
import { useUser } from '../UserContext'
import CommentComponent from './CommentComponent'
import CommentInput from './CommentInput'

const CommentList = ({comments, onAddComment, onDeleteComment, onEditComment}) => {
  const {user} = useUser();
  return (
    <>
        {comments && comments.length > 0 && (
            <div >
                <h6>Comments:</h6>
                {Array.from(comments).map((comment) => (
                    <CommentComponent 
                      key={comment.comment_id} 
                      comment={comment} 
                      onDelete={comment.username === user.username ? () => onDeleteComment(comment.comment_id) : null}
                      onEdit={comment.username === user.username ? (newText) => {onEditComment(comment.comment_id, newText)} : null}
                    />
                ))}
            </div>
        )}
        <CommentInput onAddComment={onAddComment} />
    </>
    
  )
}

export default CommentList