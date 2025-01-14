// // package com.example.Clique.Service;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyList;
// import static org.mockito.Mockito.never;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;
// import java.util.Set;

// // import org.junit.jupiter.api.BeforeEach;
// // import org.junit.jupiter.api.Test;
// // import org.mockito.InjectMocks;
// // import org.mockito.Mock;
// // import org.mockito.MockitoAnnotations;

// import com.example.Clique.Entities.Posts;
// import com.example.Clique.Entities.Users;
// import com.example.Clique.dto.PostDTO;
// import com.example.Clique.repository.ConnectionRepository;
// import com.example.Clique.repository.PostRepository;
// import com.example.Clique.repository.ReactionRepository;
// import com.example.Clique.repository.UsersRepository;
// import com.example.Clique.security.JwtUtil;
// import com.example.Clique.service.CommentService;
// import com.example.Clique.service.PostService;

// // public class PostServiceTest {
// //     @Mock
// //     private JwtUtil jwtUtil;

// //     @Mock
// //     private UsersRepository usersRepository;

// //     @Mock
// //     private PostRepository postRepository;

//     @Mock
//     private ConnectionRepository connectionRepository;

//     @Mock
//     private ReactionRepository reactionRepository;

//     @InjectMocks
//     private PostService postService;

//     @Mock
//     private CommentService commentService;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     // @Test
//     // void testCreatePost_Success() {
//     //     // Arrange
//     //     Long userId = 1L;

//     //     // Input post object
//     //     Posts inputPost = new Posts();
//     //     inputPost.setPostText("This is a valid post");

//     //     // Mocked saved post
//     //     Posts savedPost = new Posts();
//     //     savedPost.setPostId(100L);
//     //     savedPost.setPosterId(userId);
//     //     savedPost.setPostText(inputPost.getPostText());
//     //     savedPost.setPostedTime(LocalDateTime.now());

//     //     // Mocked user
//     //     Users user = new Users();
//     //     user.setUserId(userId);
//     //     user.setUsername("testUser");

//     //     // Mocked repository and service interactions
//     //     when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
//     //     when(postRepository.save(any(Posts.class))).thenReturn(savedPost);
//     //     when(reactionRepository.countByPostId(savedPost.getPostId())).thenReturn(10L);
//     //     when(reactionRepository.findByReactorIdAndPostId(userId, savedPost.getPostId())).thenReturn(Optional.empty());
//     //     when(commentService.getComments(savedPost.getPostId())).thenReturn(Set.of()); // Stub CommentService

//     //     // Act
//     //     PostDTO result = postService.createPost(userId, inputPost);

//     //     // Assert
//     //     assertNotNull(result, "Expected a non-null PostDTO");
//     //     assertEquals(100L, result.getPostId(), "Post ID should match the saved post ID");
//     //     assertEquals("This is a valid post", result.getPostText(), "Post text should match the input");
//     //     assertEquals("testUser", result.getUsername(), "Username should match the mock user");
//     //     assertEquals(10L, result.getLikes(), "Likes count should match the mocked count");
//     //     assertFalse(result.getHasLiked(), "HasLiked should be false for this user");
//     //     assertTrue(result.getCdto().isEmpty(), "Comments should be empty");

//     //     // Verify repository interactions
//     //     verify(usersRepository, times(3)).findById(userId);
//     //     verify(postRepository, times(1)).save(any(Posts.class));
//     //     verify(reactionRepository, times(1)).countByPostId(savedPost.getPostId());
//     //     verify(reactionRepository, times(1)).findByReactorIdAndPostId(userId, savedPost.getPostId());
//     //     verify(commentService, times(1)).getComments(savedPost.getPostId());
//     // }

//     @Test
//     void testCreatePost_InvalidText() {
//         Long userId = 1L;
//         Posts post = new Posts();
//         post.setPostText("");

// //         PostDTO createdPost = postService.createPost(userId, post);

// //         assertNull(createdPost);
// //         verify(postRepository, never()).save(any());
// //     }

// //     @Test
// //     void testCreatePost_UserNotFound() {
// //         Long userId = 1L;
// //         Posts post = new Posts();
// //         post.setPostText("This is a valid post.");

// //         when(usersRepository.findById(userId)).thenReturn(Optional.empty());

// //         PostDTO createdPost = postService.createPost(userId, post);

// //         assertNull(createdPost);
// //         verify(postRepository, never()).save(any());
// //     }

// //     @Test
// //     void testGetAllPosts() {
// //         when(postRepository.findAll()).thenReturn(List.of(new Posts(), new Posts()));

// //         List<Posts> posts = postService.getAllPosts(1L);

// //         assertNotNull(posts);
// //         assertEquals(2, posts.size());
// //         verify(postRepository, times(1)).findAll();
// //     }

// //     @Test
// //     void testGetPostsByUsername() {
// //         Long userId = 1L;
// //         Users user = new Users();
// //         when(postRepository.findAllByPosterId(userId)).thenReturn(List.of(new Posts(), new Posts()));

// //         List<Posts> posts = postService.getPostsByUsername(userId, user);

//         assertNotNull(posts);
//         assertEquals(2, posts.size());
//         verify(postRepository, times(1)).findAllByPosterId(userId);
//     }

//     // @Test
//     // void testGetUserFeed_Success() {
//     //     Long userId = 1L;

//     //     // Use a modifiable list to avoid UnsupportedOperationException
//     //     List<Long> followingIds = new ArrayList<>(Arrays.asList(2L, 3L));
//     //     List<Posts> posts = new ArrayList<>();
//     //     Posts post1 = new Posts(1L, "Post 1", 2L, LocalDateTime.now());
//     //     Posts post2 = new Posts(2L, "Post 2", 3L, LocalDateTime.now());
//     //     posts.add(post1);
//     //     posts.add(post2);

//     //     Users user2 = new Users();
//     //     user2.setUserId(2L);
//     //     user2.setUsername("user2");

//     //     Users user3 = new Users();
//     //     user3.setUserId(3L);
//     //     user3.setUsername("user3");

//     //     when(connectionRepository.findFollowingIdsByFollowerId(userId)).thenReturn(Optional.of(followingIds));
//     //     when(postRepository.findByPosterIdInOrderByPostIdDesc(anyList())).thenReturn(Optional.of(posts));
//     //     when(usersRepository.findById(2L)).thenReturn(Optional.of(user2));
//     //     when(usersRepository.findById(3L)).thenReturn(Optional.of(user3));

//     //     // Act
//     //     List<PostDTO> result = postService.getUserFeed(userId);

//     //     // Assert
//     //     assertNotNull(result, "Expected a non-null list of PostDTO");
//     //     assertEquals(2, result.size(), "Expected 2 posts in the feed");
//     //     assertEquals("user2", result.get(0).getUsername(), "Expected username for first post");
//     //     assertEquals("user3", result.get(1).getUsername(), "Expected username for second post");

//     //     // Verify interactions
//     //     verify(connectionRepository, times(1)).findFollowingIdsByFollowerId(userId);
//     //     verify(postRepository, times(1)).findByPosterIdInOrderByPostIdDesc(anyList());
//     //     verify(usersRepository, times(2)).findById(2L);
//     //     verify(usersRepository, times(2)).findById(3L);
//     // }

//     @Test
//     void testUpdatePost_Success() {
//         // Arrange
//         Long postId = 1L;
//         Posts existingPost = new Posts();
//         existingPost.setPostId(postId);
//         existingPost.setPostText("Original Text");

//         Posts updatedPost = new Posts();
//         updatedPost.setPostId(postId);
//         updatedPost.setPostText("Updated Text");

//         when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));
//         when(postRepository.save(any(Posts.class))).thenAnswer(invocation -> {
//             Posts p = invocation.getArgument(0);
//             p.setPostedTime(LocalDateTime.now());
//             return p;
//         });

//         // Act
//         Posts result = postService.updatePost(updatedPost);

//         // Assert
//         assertNotNull(result, "Expected a non-null updated post");
//         assertEquals("Updated Text", result.getPostText(), "Post text should match the updated value");
//         assertNotNull(result.getPostedTime(), "Posted time should be updated");
//         verify(postRepository, times(1)).findById(postId);
//         verify(postRepository, times(1)).save(existingPost);
//     }

//     @Test
//     void testDeletePost_Success() {
//         // Arrange
//         Long postId = 1L;

//         when(postRepository.existsById(postId)).thenReturn(true);

//         // Act
//         String result = postService.deletePost(postId);

//         // Assert
//         assertEquals("post deleted", result, "Expected a success message for post deletion");
//         verify(postRepository, times(1)).existsById(postId);
//         verify(postRepository, times(1)).deleteById(postId);
//     }

    
// }
