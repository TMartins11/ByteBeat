package com.mrtns.spring.service;

import com.mrtns.spring.model.Song;
import com.mrtns.spring.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest {

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongService songService;

    @Test
    public void getSong_shouldReturnSong_whenIdExists(){
        Integer id = 1;
        Song song = new Song();
        song.setId(id);
        song.setTitle("Test Song");

        when(songRepository.findById(id)).thenReturn(Optional.of(song));

        Optional<Song> result = songService.getSong(id);

        assertTrue(result.isPresent());
        assertEquals("Test Song", result.get().getTitle());
    }

    @Test
    public void getSong_shouldReturnEmpty_whenIdDoesNotExist(){
        Integer id = -1;
        when(songRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Song> result = songService.getSong(id);

        assertTrue(result.isEmpty());
    }

    @Test
    public void updateSong_shouldReturnUpdatedSong_whenIdExists(){
        Integer id = 1;
        Song song = new Song();
        song.setId(id);
        song.setTitle("Test Song");

        when(songRepository.existsById(id)).thenReturn(true);
        when(songRepository.save(song)).thenReturn(song);

        Optional<Song> result = songService.updateSong(id, song);

        assertTrue(result.isPresent());
        assertEquals("Test Song",result.get().getTitle());
    }

    @Test
    public void updateSong_shouldReturnEmpty_whenIdDoesNotExist(){
        Integer id = -1;
        Song song = new Song();

        when(songRepository.existsById(id)).thenReturn(false);

        Optional<Song> result = songService.updateSong(id,song);

        assertTrue(result.isEmpty());
    }

    @Test
    public void deleteSong_shouldReturnTrue_whenIdExists(){
        Integer id = 1;
        Song song = new Song();
        song.setId(id);

        when(songRepository.existsById(id)).thenReturn(true);

        boolean result = songService.deleteSong(id);

        assertTrue(result);
    }

    @Test
    public void deleteSong_shouldReturnFalse_whenIdDoesNotExist(){
        Integer id = -1;
        when(songRepository.existsById(id)).thenReturn(false);

        boolean result = songService.deleteSong(id);

        assertFalse(result);
    }

    @Test
    public void search_shouldReturnFilteredSongs_whenArtistProvided(){
        List<Song> songsList = new ArrayList<>();

        Song songOne = new Song();
        songOne.setTitle("Bohemian Rhapsody");
        songOne.setArtist("Queen");

        Song songTwo = new Song();
        songTwo.setTitle("Remember the Time");
        songTwo.setArtist("Michael Jackson");


        Song songThree = new Song();
        songThree.setTitle("Harvest Moon");
        songThree.setArtist("Neil Young");

        songsList.add(songOne);
        songsList.add(songTwo);
        songsList.add(songThree);

        when(songRepository.findAll()).thenReturn(songsList);

        List<Song> result = songService.search("Queen", null, null);

        assertEquals(1,result.size());
        assertEquals("Queen",result.get(0).getArtist());
    }
}