package br.com.playyourlist.reproducoes;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.playyourlist.erros.RecursoNaoEncontradoException;
import br.com.playyourlist.playlists.PlaylistRepository;

@Service
public class ReproducaoService {

    private final ReproducaoRepository reproducaoRepository;
    private final PlaylistRepository playlistRepository;

    public ReproducaoService(ReproducaoRepository reproducaoRepository,
                             PlaylistRepository playlistRepository) {
        this.reproducaoRepository = reproducaoRepository;
        this.playlistRepository = playlistRepository;
    }

    public Reproducao criar(Integer playlistid) {
        validarPlaylist(playlistid);
        return reproducaoRepository.save(
                new Reproducao(playlistid, LocalDateTime.now()));
    }

    public List<Reproducao> listar(Integer playlistid) {
        validarPlaylist(playlistid);
        return reproducaoRepository.findByPlaylistidOrderByDatahoraAsc(playlistid);
    }

    public TotalReproducoesResponse total(Integer playlistid) {
        validarPlaylist(playlistid);
        return new TotalReproducoesResponse(
                playlistid, reproducaoRepository.countByPlaylistid(playlistid));
    }

    private void validarPlaylist(Integer playlistid) {
        if (!playlistRepository.existsById(playlistid)) {
            throw new RecursoNaoEncontradoException(
                    "Playlist com id " + playlistid + " nao localizada");
        }
    }
}
