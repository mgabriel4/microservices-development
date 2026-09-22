package br.com.playyourlist.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.playyourlist.playlists.Playlist;
import br.com.playyourlist.playlists.PlaylistMusica;

@FeignClient(name = "playlists-client", url = "${app.base-url}")
public interface PlaylistClient {

    @GetMapping("/playlists/{playlistid}")
    ResponseEntity<Playlist> buscar(
            @PathVariable("playlistid") Integer playlistid);

    @PostMapping("/playlists/{playlistid}/musicas/{musicaId}")
    ResponseEntity<PlaylistMusica> adicionarMusica(
            @PathVariable("playlistid") Integer playlistid,
            @PathVariable("musicaId") Integer musicaId);
}
