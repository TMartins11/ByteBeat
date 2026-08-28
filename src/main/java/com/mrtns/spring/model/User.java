package com.mrtns.spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank (message = "O usuário deve ter um nome")
    private String name;
    @NotBlank (message = "O usuário deve ter um email")
    private String email;


    @ManyToMany
    @JoinTable(
            name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Song> favoriteSongs = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Song> getFavoriteSongs() {
        return favoriteSongs;
    }

    public void setFavoriteSongs(List<Song> favoriteSongs) {
        this.favoriteSongs = favoriteSongs;
    }
}