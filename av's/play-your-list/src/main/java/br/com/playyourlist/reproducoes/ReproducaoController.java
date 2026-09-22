package br.com.playyourlist.reproducoes;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reproducao")
public class ReproducaoController {

    private final ReproducaoService reproducaoService;

    public ReproducaoController(ReproducaoService reproducaoService) {
        this.reproducaoService = reproducaoService;
    }

    @PostMapping
    public ResponseEntity<Reproducao> criar(
            @Valid @RequestBody ReproducaoRequest request) {
        return ResponseEntity.status(201)
                .body(reproducaoService.criar(request.playlistid()));
    }

    @GetMapping("/{playlistid}")
    public ResponseEntity<List<Reproducao>> listar(
            @PathVariable Integer playlistid) {
        return ResponseEntity.ok(reproducaoService.listar(playlistid));
    }

    @GetMapping("/total/{playlistid}")
    public ResponseEntity<TotalReproducoesResponse> total(
            @PathVariable Integer playlistid) {
        return ResponseEntity.ok(reproducaoService.total(playlistid));
    }
}
