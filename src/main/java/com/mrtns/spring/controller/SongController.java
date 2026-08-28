package com.mrtns.spring.controller;

import com.mrtns.spring.model.Song;
import com.mrtns.spring.service.SongService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping("/songs")
    public Song createSong(@Valid @RequestBody Song song){
        return songService.createSong(song);
    }

    @GetMapping("/songs")
    public List<Song> retrieveAllSongs(){
        return songService.retrieveAllSongs();
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<Song> getSong(@PathVariable Integer id){
        Optional<Song> result = songService.getSong(id);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<?> updateSong(@PathVariable Integer id, @Valid @RequestBody Song song){
        Optional<Song> updated = songService.updateSong(id,song);
        return updated.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Integer id){
        if(songService.deleteSong(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/songs/search")
    public List<Song> search(@RequestParam(required = false) String artist, @RequestParam(required = false) String title,
                             @RequestParam(required = false) String genre){
        return songService.search(artist,title,genre);
    }

}
