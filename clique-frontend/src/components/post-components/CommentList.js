import React, { useState } from 'react'
import { useUser } from '../UserContext'
import CommentComponent from './CommentComponent'
import CommentInput from './CommentInput'

const CommentList = ({comments, onAddComment, onDeleteComment, onEditComment}) => {
  const {user} = useUser();
  const [showAll, setShowAll] = useState(false);

  const displayedComments = comments && comments.length > 0
    ? showAll
      ? comments
      : comments.slice(0, 1)
    : [];

  return (
    <div className="mt-4 space-y-2">
      {comments && comments.length > 0 && (
        <div>
          <h6 className="text-sm font-medium text-gray-700 mb-2">Comments:</h6>
          {displayedComments.map((comment) => (
            <CommentComponent 
              userId={comment.userId}
              key={comment.comment_id} 
              comment={comment} 
              onDelete={comment.username === user.username ? () => onDeleteComment(comment.comment_id) : null}
              onEdit={comment.username === user.username ? (newText) => {onEditComment(comment.comment_id, newText)} : null}
            />
          ))}
          {comments.length > 1 && (
            <button 
              onClick={() => setShowAll(!showAll)}
              className="text-xs text-[#003a92] font-medium hover:underline mt-2"
            >
              {showAll ? 'Collapse comments' : 'Show more comments'}
            </button>
          )}
        </div>
      )}
      <CommentInput onAddComment={onAddComment} />
    </div>
  )
}

export default CommentList
