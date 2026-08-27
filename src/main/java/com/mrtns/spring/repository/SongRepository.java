package com.mrtns.spring.repository;

import com.mrtns.spring.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Integer>{

    List<Song> findByArtist(String artist);

    List<Song> findByTitleContaining(String keyword);

}
