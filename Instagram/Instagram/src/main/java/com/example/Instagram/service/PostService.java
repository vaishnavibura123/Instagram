package com.example.Instagram.service;

import com.example.Instagram.Dao.PostRepo;
import com.example.Instagram.model.Post;
import com.example.Instagram.model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepo postRepo;

    public int savePost(Post post) {
        Post savedPost = postRepo.save(post);
        return savedPost.getPostId();

    }

    public JSONArray getPost(int userId, String postId) {

        JSONArray postArr = new JSONArray();

        if (null != postId && postRepo.findById(Integer.valueOf(postId)).isPresent()) {
            Post post = postRepo.findById(Integer.valueOf(postId)).get();
            JSONObject postObj = setPostData(post);
            postArr.put(postObj);
        } else {
            List<Post> postList = postRepo.findAll();
            for (Post post : postList) {
                JSONObject postObj = setPostData(post);
                postArr.put(postObj);
            }
        }

        return postArr;
    }

    private JSONObject setPostData(Post post) {

        JSONObject masterJson = new JSONObject();
        masterJson.put("postId", post.getPostId());
        masterJson.put("postData", post.getPostData());

        User user = post.getUser();

        JSONObject userJsonObj = new JSONObject();
        userJsonObj.put("userId", user.getUserId());
        userJsonObj.put("firstName", user.getFirstName());
        userJsonObj.put("age", user.getAge());

        masterJson.put("user", userJsonObj);

        return masterJson;
    }

    public void updatePost(String postId, Post updatedPost) {

        if (postRepo.findById(Integer.valueOf(postId)).isPresent()) {
            Post olderPost = postRepo.findById(Integer.valueOf(postId)).get();
            updatedPost.setPostId(olderPost.getPostId());
            updatedPost.setUser(olderPost.getUser());
            updatedPost.setCreatedDate(olderPost.getCreatedDate());
            Timestamp  createTime=new Timestamp(System.currentTimeMillis());
            //Timestamp updatedDate = new Timestamp(System.currentTimeMillis());
            updatedPost.setUpdateDate(createTime);


            postRepo.save(updatedPost);
        }

    }
}
