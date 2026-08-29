package com.mrtns.spring.service;

import com.mrtns.spring.exception.ResourceNotFoundException;
import com.mrtns.spring.model.Song;
import com.mrtns.spring.model.User;
import com.mrtns.spring.repository.SongRepository;
import com.mrtns.spring.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUser_shouldReturnUser_whenIdExists(){
        Integer id = 1;
        User user = new User();
        user.setId(id);
        user.setName("Test User");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUser(id);

        assertTrue(result.isPresent());
        assertEquals("Test User", result.get().getName());
    }

    @Test
    public void getUser_shouldReturnEmpty_whenIdDoesNotExist(){
        Integer id = -1;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUser(id);

        assertTrue(result.isEmpty());
    }


    @Test
    public void addFavorite_shouldAddSong_whenUserAndSongExist(){
        Integer userId = 1;
        Integer songId = 1;
        User user = new User();
        user.setId(userId);

        Song song = new Song();
        song.setId(songId);
        song.setArtist("Queen");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(songRepository.findById(songId)).thenReturn(Optional.of(song));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.addFavorite(userId, songId);

        assertTrue(result.getFavoriteSongs().contains(song));
    }

    @Test
    public void addFavorite_shouldThrow_whenUserDoesNotExist(){
        Integer userId = -1;
        Integer songId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.addFavorite(userId, songId));
    }

    @Test
    public void addFavorite_shouldThrow_whenSongDoesNotExist(){
        Integer userId = 1;
        Integer songId = -1;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(songRepository.findById(songId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.addFavorite(userId, songId));
    }

    @Test
    public void deleteFavorite_shouldRemoveSong_whenUserAndSongExist(){
        Integer userId = 1;
        Integer songId = 1;
        Song song = new Song();
        song.setId(songId);

        User user = new User();
        user.setId(userId);
        user.getFavoriteSongs().add(song);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(songRepository.findById(songId)).thenReturn(Optional.of(song));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.deleteFavorite(userId, songId);

        assertFalse(user.getFavoriteSongs().contains(song));
    }

    @Test
    public void getRecommendations_shouldReturnSongsFromSameGenre_excludingAlreadyFavorited(){
        Integer userId = 1;

        Song favorited = new Song();
        favorited.setId(1);
        favorited.setGenre("Rock");

        User user = new User();
        user.setId(userId);
        user.getFavoriteSongs().add(favorited);

        Song recommended = new Song();
        recommended.setId(2);
        recommended.setGenre("Rock");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(songRepository.findByGenreInAndIdNotIn(anySet(), anyList()))
                .thenReturn(List.of(recommended));

        List<Song> result = userService.getRecommendations(userId);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getId());
    }
}
