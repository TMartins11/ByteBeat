package com.mrtns.spring.controller;

import com.mrtns.spring.model.Song;
import com.mrtns.spring.repository.SongRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SongController {

    @Autowired
    SongRepository songRepository;

    @PostMapping("/songs")
    public Song createSong(@Valid @RequestBody Song song){
        return songRepository.save(song);
    }

    @GetMapping("/songs")
    public List<Song> retrieveAllSongs(){
        return songRepository.findAll();
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSong(@PathVariable Integer id){
        Optional<Song> result = songRepository.findById(id);

        if(result.isPresent()){
            return ResponseEntity.ok(result.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<?> updateSong(@PathVariable Integer id, @Valid @RequestBody Song song){
        if(songRepository.existsById(id)){
            song.setId(id);
            return ResponseEntity.ok(songRepository.save(song));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Integer id){
        if(songRepository.existsById(id)){
            songRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
