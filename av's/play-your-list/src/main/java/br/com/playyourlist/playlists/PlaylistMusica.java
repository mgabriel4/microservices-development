package br.com.playyourlist.playlists;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "playlist_musicas")
public class PlaylistMusica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer playlistid;

    @Column(nullable = false)
    private Integer musicaid;

    public PlaylistMusica() {
    }

    public PlaylistMusica(Integer playlistid, Integer musicaid) {
        this.playlistid = playlistid;
        this.musicaid = musicaid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlaylistid() {
        return playlistid;
    }

    public void setPlaylistid(Integer playlistid) {
        this.playlistid = playlistid;
    }

    public Integer getMusicaid() {
        return musicaid;
    }

    public void setMusicaid(Integer musicaid) {
        this.musicaid = musicaid;
    }
}
