import React from 'react'
import { useUser } from '../UserContext'
import PostComponent from './PostComponent'
import LikeComponent from './LikeComponent'
import CommentList from './CommentList'
import api from '../../service/api'

const PostList = ({posts, setPosts}) => { 

  const {user, token} = useUser();
  const headers = token ? { Authorization: `Bearer ${token}` } : {};

  const handleLikeToggle = async (postId, hasLiked) => {
    try {
      const endpoint = hasLiked ? `/reactions/${postId}/unlike` : `reactions/${postId}/like`;
      const response = hasLiked ? await api.delete(endpoint, {headers}) : await api.post(endpoint, null, {headers});
      const updatedPosts = posts.map((post) => 
        post.postId === postId 
        ? {...post, likes: post.likes + (hasLiked ? -1 : 1), hasLiked: !hasLiked} 
        : post
      );
      setPosts(updatedPosts);
    } catch (err) {
      console.error("Error updating like", err);
    }
  }

  const handleAddComment = async (postId, commentText) => {
    try {
      const response = await api.post(`/comments/add`, {postId, commentText}, {headers});
      const newComment = response.data;
      const updatedPosts = posts.map((post) =>
        post.postId === postId
        ? {...post, cdto: [newComment, ...post.cdto]}
        : post
      );
      setPosts(updatedPosts);
    } catch (err) {
      console.error("Error adding comment", err);
    }
  }

  return (
    <>
      {
        posts.length > 0 ? (
          posts.map((post) =>
            <div key={post.postId}>
              <PostComponent poster={post.username} createdAt={post.postedTime} content={post.postText} />
              <LikeComponent count={post.likes} hasLiked={post.hasLiked} onLikeToggle={() => handleLikeToggle(post.postId, post.hasLiked)}/>
              <CommentList comments={post.cdto} onAddComment={(commentText) => handleAddComment(post.postId, commentText)}/>
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