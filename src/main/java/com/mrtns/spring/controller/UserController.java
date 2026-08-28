package com.mrtns.spring.controller;

import com.mrtns.spring.model.Song;
import com.mrtns.spring.model.User;
import com.mrtns.spring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userService.retrieveAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id){
        Optional<User> result = userService.getUser(id);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @Valid @RequestBody User user){
        Optional<User> updated = userService.updateUser(id,user);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        if(userService.deleteUser(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/users/{userId}/favorites/{songId}")
    public ResponseEntity<User> addFavorite(@PathVariable Integer userId, @PathVariable Integer songId){
        User updatedUser = userService.addFavorite(userId,songId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{userId}/favorites/{songId}")
    public ResponseEntity<User> deleteFavorite(@PathVariable Integer userId, @PathVariable Integer songId){
        userService.deleteFavorite(userId,songId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/recommendations")
    public ResponseEntity<List<Song>> getRecommendations(@PathVariable Integer userId){
        List<Song> recommendations = userService.getRecommendations(userId);
        return ResponseEntity.ok(recommendations);
    }
}
