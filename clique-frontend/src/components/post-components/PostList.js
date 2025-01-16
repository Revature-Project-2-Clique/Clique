import React from 'react'
import { useUser } from '../UserContext'
import PostComponent from './PostComponent'
import LikeComponent from './LikeComponent'
import CommentList from './CommentList'
import api from '../../service/api'

const PostList = ({posts, setPosts}) => { 
  const {user, token} = useUser();
  const headers = token ? { Authorization: `Bearer ${token}` } : {};

  const handleUpdatePost = async (postId, postText) => {
    try {
      const response = await api.patch(`/posts`, {postId, postText}, {headers});
      const updatedPost = response.data;
      const updatedPosts = posts.map((post) => 
        post.postId === postId
        ? {...post, postText: updatedPost.postText, postedTime: updatedPost.postedTime}
        : post
      );
      setPosts(updatedPosts);
    } catch (err) {
      console.error("Unable to update post", err);
    }
  }

  const handleDeletePost = async(postId) => {
    try {
      await api.delete(`/posts/${postId}`,{headers});
      const updatedPosts = posts.filter((post) => post.postId !== postId);
      setPosts(updatedPosts);
    } catch (err) {
      console.error("Unable to delete post", err);
    }
  }

  const handleLikeToggle = async (postId, hasLiked) => {
    try {
      const endpoint = hasLiked ? `/reactions/${postId}/unlike` : `reactions/${postId}/like`;
      hasLiked ? await api.delete(endpoint, {headers}) : await api.post(endpoint, null, {headers});
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

  const handleDeleteComment = async (postId, commentId) => {
    try {
      await api.delete(`/comments/delete/${commentId}`, {headers});
      const updatedPosts = posts.map((post) =>
        post.postId === postId
        ? {...post, cdto: post.cdto.filter((comment)=> comment.comment_id !== commentId)}
        : post
      );
      setPosts(updatedPosts);
    } catch (err) {
      console.error("Unable to delete comment", err);
    }
  }

  const handleEditComment = async (postId, commentId, newText) => {
    try {
      const response = await api.patch(`/comments/update/${commentId}`,{commentId: commentId, postId: postId, commentText: newText}, {headers});
      const updatedComment = response.data;
      const updatedPosts = posts.map((post) =>
        post.postId === postId
        ? {...post, cdto: post.cdto.map((comment) =>
            comment.comment_id === commentId
            ? { ...comment, comment_text: updatedComment.commentText, posted_Time: updatedComment.postedTime}
            : comment
          )}
        : post
      )
      setPosts(updatedPosts);
    } catch (err) {
      console.error("Unable to update comment", err);
    }
  }

  return (
    <>
      {posts.length > 0 ? (
        posts.map((post) => (
          <div key={post.postId} className="bg-white p-4 shadow-md rounded-md mb-6">
            <PostComponent 
              userId={post.userId}
              poster={post.username} 
              createdAt={post.postedTime} 
              content={post.postText}
              onEditPost={post.username === user.username ? (newText) => handleUpdatePost(post.postId, newText) : null} 
              onDeletePost={post.username === user.username ? () => handleDeletePost(post.postId) : null}  
              imageUrl={post.imageUrl}
              videoUrl={post.videoUrl}
              profilePhotoUrl={post.profilePictureUrl} 
            />
            <LikeComponent 
              count={post.likes} 
              hasLiked={post.hasLiked} 
              onLikeToggle={() => handleLikeToggle(post.postId, post.hasLiked)}
            />
            <CommentList 
              comments={post.cdto} 
              onAddComment={(commentText) => handleAddComment(post.postId, commentText)}
              onDeleteComment={(commentId) => handleDeleteComment(post.postId, commentId)}
              onEditComment={(commentId, newText) => handleEditComment(post.postId, commentId, newText)}
            />
          </div>
        ))
      ) : (
        <p className="text-gray-600">No posts available</p>
      )}
    </>
  )
}

export default PostList;
