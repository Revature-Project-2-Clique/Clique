import React from 'react'

const LikeComponent = ({count, hasLiked, onLikeToggle}) => {
  return (
    <p className="my-2">
      <button 
        onClick={onLikeToggle}
        className="text-sm text-[#003a92] font-bold hover:underline"
      >
        {hasLiked ? 'Unlike' : 'Like'} ({count})
      </button>
    </p>
  )
}

export default LikeComponent
