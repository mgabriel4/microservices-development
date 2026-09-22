package br.com.playyourlist.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiOrquestradorService orquestradorService;

    public ApiController(ApiOrquestradorService orquestradorService) {
        this.orquestradorService = orquestradorService;
    }

    @PostMapping("/adicionar/{playlistId}/musicas/{musicaId}")
    public ResponseEntity<MensagemResponse> adicionarMusica(
            @PathVariable Integer playlistId,
            @PathVariable Integer musicaId) {
        return ResponseEntity.ok(
                orquestradorService.adicionarMusica(playlistId, musicaId));
    }

    @PutMapping("/executar/{playlistId}")
    public ResponseEntity<ExecucaoResponse> executarPlaylist(
            @PathVariable Integer playlistId) {
        return ResponseEntity.ok(
                orquestradorService.executarPlaylist(playlistId));
    }
}
