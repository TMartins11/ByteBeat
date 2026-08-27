package com.mrtns.spring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Song {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Uma música deve ter um título")
    private String title;
    @NotBlank(message = "Uma música deve ser composta por um artista")
    private String artist;
    private String album;
    private String genre;
    private Integer releaseYear;
    private String coverUrl;

    public Song(Integer id) {
        super();
        this.id = id;
    }

    public Song() {
        super();
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title=" + title + '\'' +
                ", artist=" + artist + '\'' +
                ", album=" + album +
                ", genre=" + genre + '\'' +
                ", release year=" + releaseYear + '\''+
                ", coverUrl=" + coverUrl + '\'' +
                '}';
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum(){
        return album;
    }

    public void setAlbum(String album){
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getReleaseYear(){
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear){
        this.releaseYear = releaseYear;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
