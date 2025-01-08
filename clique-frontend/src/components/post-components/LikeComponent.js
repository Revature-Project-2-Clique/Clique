import React from 'react'

const LikeComponent = ({count, hasLiked}) => {
  return (
    <p>
        <button>
          {hasLiked ? 'Unlike' : 'Like'} ({count})
        </button>
    </p>
  )
}

export default LikeComponent