package com.example.Instagram.service;

import com.example.Instagram.Dao.UserRepo;
import com.example.Instagram.model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public int saveUser(User user) {
        User user1=userRepo.save(user);
        return user1.getUserId();
    }

    public JSONArray getUser(String userId) {
        JSONArray userArray=new JSONArray();
        if(null != userId && userRepo.findById(Integer.valueOf(userId)).isPresent()){
            User user=userRepo.findById(Integer.valueOf(userId)).get();
            JSONObject userObj=setUser(user);
            userArray.put(userObj);
        }
        else{
            List<User> userList=userRepo.findAll();
            for(User user: userList){
                JSONObject userObj=setUser(user);
                userArray.put(userObj);
            }
        }
        return userArray;
    }

    private JSONObject setUser(User user) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userId", user.getUserId());
        jsonObject.put("firstName", user.getFirstName());
        jsonObject.put("lastName", user.getLastName());
        jsonObject.put("age", user.getAge());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("phoneNumber", user.getPhoneNumber());
        return jsonObject;

    }

    public void updateUser(User newUser, String userId) {
        if(userRepo.findById(Integer.valueOf(userId)).isPresent()){
            User user=userRepo.findById(Integer.valueOf(userId)).get();
            newUser.setUserId(user.getUserId());
            userRepo.save(newUser);
        }
    }
}
