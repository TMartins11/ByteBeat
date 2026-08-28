package com.mrtns.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "favoriteSongs")
    @JsonIgnore
    private List<User> favoritedBy = new ArrayList<>();

    public Song(Integer id) {
        super();
        this.id = id;
    }

    public Song() {
        super();
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

    public List<User> getFavoritedBy() {
        return favoritedBy;
    }

    public void setFavoritedBy(List<User> favoritedBy) {
        this.favoritedBy = favoritedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return id != null && id.equals(song.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
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
}
