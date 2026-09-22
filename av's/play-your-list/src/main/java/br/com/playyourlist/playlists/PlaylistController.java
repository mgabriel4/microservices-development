package br.com.playyourlist.playlists;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<Playlist> criar(@Valid @RequestBody Playlist playlist) {
        Playlist criada = playlistService.criar(playlist);
        return ResponseEntity.created(URI.create("/playlists/" + criada.getId()))
                .body(criada);
    }

    @GetMapping
    public ResponseEntity<Iterable<Playlist>> listar() {
        return ResponseEntity.ok(playlistService.listar());
    }

    @GetMapping("/{playlistid}")
    public ResponseEntity<Playlist> buscar(@PathVariable Integer playlistid) {
        return ResponseEntity.ok(playlistService.buscar(playlistid));
    }

    @PutMapping("/{playlistid}")
    public ResponseEntity<Playlist> atualizar(@PathVariable Integer playlistid,
                                              @Valid @RequestBody Playlist playlist) {
        return ResponseEntity.ok(playlistService.atualizar(playlistid, playlist));
    }

    @DeleteMapping("/{playlistid}")
    public ResponseEntity<Void> excluir(@PathVariable Integer playlistid) {
        playlistService.excluir(playlistid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{playlistid}/musicas/{musicaId}")
    public ResponseEntity<PlaylistMusica> adicionarMusica(
            @PathVariable Integer playlistid,
            @PathVariable Integer musicaId) {
        PlaylistMusica associacao = playlistService
                .adicionarMusica(playlistid, musicaId);
        return ResponseEntity.status(201).body(associacao);
    }

    @DeleteMapping("/{playlistid}/musicas/{musicaId}")
    public ResponseEntity<Void> removerMusica(
            @PathVariable Integer playlistid,
            @PathVariable Integer musicaId) {
        playlistService.removerMusica(playlistid, musicaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{playlistid}/musicas")
    public ResponseEntity<List<Integer>> listarMusicas(
            @PathVariable Integer playlistid) {
        return ResponseEntity.ok(playlistService.listarIdsMusicas(playlistid));
    }
}
