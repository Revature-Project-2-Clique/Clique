import React from 'react'

const LikeComponent = ({count, hasLiked, onLikeToggle}) => {
  return (
    <p>
        <button onClick={onLikeToggle}>
          {hasLiked ? 'Unlike' : 'Like'} ({count})
        </button>
    </p>
  )
}

export default LikeComponent