package com.mrtns.spring.service;

import com.mrtns.spring.model.Song;
import com.mrtns.spring.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    SongRepository songRepository;

    public List<Song> retrieveAllSongs(){
        return songRepository.findAll();
    }

    public Song createSong(Song song){
        return songRepository.save(song);
    }

    public Optional<Song> getSong(Integer id){
        return songRepository.findById(id);
    }

    public Optional<Song> updateSong(Integer id, Song song){
        if(songRepository.existsById(id)){
            song.setId(id);
            Song updated = songRepository.save(song);
            return Optional.of(updated);
        }
        return Optional.empty();
    }

    public boolean deleteSong(Integer id){
        if(songRepository.existsById(id)){
            songRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Song> search(String artist, String title, String genre){
        List <Song> allSongs = songRepository.findAll();

        List<Song> result = allSongs.stream()
                .filter(song -> artist == null || song.getArtist().equals(artist))
                .filter(song -> title  == null || song.getTitle().contains(title))
                .filter(song -> genre  == null || song.getGenre().equals(genre))
                .toList();

        return result;
    }
}
