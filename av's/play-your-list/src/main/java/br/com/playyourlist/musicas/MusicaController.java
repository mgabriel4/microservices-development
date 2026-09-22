package br.com.playyourlist.musicas;

import java.net.URI;

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
@RequestMapping("/musicas")
public class MusicaController {

    private final MusicaService musicaService;

    public MusicaController(MusicaService musicaService) {
        this.musicaService = musicaService;
    }

    @PostMapping
    public ResponseEntity<Musica> criar(@Valid @RequestBody Musica musica) {
        Musica criada = musicaService.criar(musica);
        return ResponseEntity.created(URI.create("/musicas/" + criada.getId()))
                .body(criada);
    }

    @GetMapping
    public ResponseEntity<Iterable<Musica>> listar() {
        return ResponseEntity.ok(musicaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Musica> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(musicaService.buscar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Musica> atualizar(@PathVariable Integer id,
                                            @Valid @RequestBody Musica musica) {
        return ResponseEntity.ok(musicaService.atualizar(id, musica));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        musicaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
