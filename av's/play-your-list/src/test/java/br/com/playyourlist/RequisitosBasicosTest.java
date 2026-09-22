package br.com.playyourlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.playyourlist.musicas.Musica;
import br.com.playyourlist.musicas.MusicaRepository;
import br.com.playyourlist.playlists.PlaylistMusicaRepository;
import br.com.playyourlist.playlists.PlaylistRepository;
import br.com.playyourlist.playlists.PlaylistService;
import br.com.playyourlist.reproducoes.ReproducaoService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@SpringBootTest
@Transactional
class RequisitosBasicosTest {

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private PlaylistMusicaRepository playlistMusicaRepository;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private ReproducaoService reproducaoService;

    @Autowired
    private Validator validator;

    @Test
    void deveCarregarOsDadosIniciais() {
        assertEquals(5, musicaRepository.count());
        assertEquals(5, playlistRepository.count());
        assertEquals(10, playlistMusicaRepository.count());
        assertEquals(5, reproducaoService.total(1).total());
    }

    @Test
    void deveAplicarAsValidacoesDeMusica() {
        Musica invalida = new Musica("   ", "", null, 0, "x".repeat(51));

        Set<ConstraintViolation<Musica>> violacoes = validator.validate(invalida);

        assertEquals(4, violacoes.size());
        assertTrue(violacoes.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("titulo")));
        assertTrue(violacoes.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("artista")));
        assertTrue(violacoes.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("duracao")));
        assertTrue(violacoes.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("genero")));
    }

    @Test
    void deveAdicionarListarERemoverMusicaDaPlaylist() {
        playlistService.adicionarMusica(1, 2);
        assertTrue(playlistService.listarIdsMusicas(1).contains(2));

        playlistService.removerMusica(1, 2);
        assertTrue(!playlistService.listarIdsMusicas(1).contains(2));
    }
}
