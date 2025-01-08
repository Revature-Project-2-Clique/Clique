import React from 'react'
import { useUser } from '../UserContext'
import PostComponent from './PostComponent'
import LikeComponent from './LikeComponent'
import CommentList from './CommentList'

const PostList = ({posts}) => { 

  return (
    <>
      {
        posts.length > 0 ? (
          posts.map((post) =>
            <div key={post.postId}>
              <PostComponent poster={post.username} createdAt={post.postedTime} content={post.postText} />
              <LikeComponent count={post.likes} hasLiked={post.hasLiked}/>
              <CommentList comments={post.cdto}/>
            </div> 
          )
        ) : (
          <p>No posts available</p>
        )
      }
    </>
  )
}

export default PostList