package com.mrtns.spring.service;

import com.mrtns.spring.exception.ResourceNotFoundException;
import com.mrtns.spring.model.Song;
import com.mrtns.spring.model.User;
import com.mrtns.spring.repository.SongRepository;
import com.mrtns.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SongRepository songRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUser(Integer id){
        return userRepository.findById(id);
    }

    public Optional<User> updateUser(Integer id, User user){
        if(userRepository.existsById(id)){
            user.setId(id);
            User updated = userRepository.save(user);
            return Optional.of(updated);
        }
        return Optional.empty();
    }

    public boolean deleteUser(Integer id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User addFavorite(Integer userId, Integer songId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Música não encontrada"));

        user.getFavoriteSongs().add(song);
        return userRepository.save(user);
    }

    public void deleteFavorite(Integer userId, Integer songId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Música não encontrada"));

        user.getFavoriteSongs().remove(song);
        userRepository.save(user);
    }

    public List<Song> getRecommendations(Integer userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Set<String> genres = new HashSet<>();
        for (Song song : user.getFavoriteSongs()) {
            genres.add(song.getGenre());
        }

        List<Integer> alreadyFavorites = new ArrayList<Integer>();
        for(Song song : user.getFavoriteSongs() ){
            alreadyFavorites.add(song.getId());
        }

        return songRepository.findByGenreInAndIdNotIn(genres, alreadyFavorites);
    }
}
