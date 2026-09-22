package br.com.playyourlist.playlists;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.playyourlist.erros.ConflitoException;
import br.com.playyourlist.erros.RecursoNaoEncontradoException;
import br.com.playyourlist.musicas.MusicaRepository;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMusicaRepository playlistMusicaRepository;
    private final MusicaRepository musicaRepository;

    public PlaylistService(PlaylistRepository playlistRepository,
                           PlaylistMusicaRepository playlistMusicaRepository,
                           MusicaRepository musicaRepository) {
        this.playlistRepository = playlistRepository;
        this.playlistMusicaRepository = playlistMusicaRepository;
        this.musicaRepository = musicaRepository;
    }

    public Iterable<Playlist> listar() {
        return playlistRepository.findAll();
    }

    public Playlist buscar(Integer playlistid) {
        return playlistRepository.findById(playlistid)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Playlist com id " + playlistid + " nao localizada"));
    }

    public Playlist criar(Playlist playlist) {
        playlist.setId(null);
        return playlistRepository.save(playlist);
    }

    public Playlist atualizar(Integer playlistid, Playlist playlist) {
        buscar(playlistid);
        playlist.setId(playlistid);
        return playlistRepository.save(playlist);
    }

    @Transactional
    public void excluir(Integer playlistid) {
        buscar(playlistid);
        playlistMusicaRepository.deleteByPlaylistid(playlistid);
        playlistRepository.deleteById(playlistid);
    }

    public PlaylistMusica adicionarMusica(Integer playlistid, Integer musicaId) {
        buscar(playlistid);

        if (!musicaRepository.existsById(musicaId)) {
            throw new RecursoNaoEncontradoException(
                    "Musica com id " + musicaId + " nao localizada");
        }

        if (playlistMusicaRepository.existsByPlaylistidAndMusicaid(playlistid, musicaId)) {
            throw new ConflitoException(
                    "A musica " + musicaId + " ja pertence a playlist " + playlistid);
        }

        return playlistMusicaRepository.save(new PlaylistMusica(playlistid, musicaId));
    }

    @Transactional
    public void removerMusica(Integer playlistid, Integer musicaId) {
        buscar(playlistid);

        long removidos = playlistMusicaRepository
                .deleteByPlaylistidAndMusicaid(playlistid, musicaId);

        if (removidos == 0) {
            throw new RecursoNaoEncontradoException(
                    "A musica " + musicaId + " nao pertence a playlist " + playlistid);
        }
    }

    public List<Integer> listarIdsMusicas(Integer playlistid) {
        buscar(playlistid);
        return playlistMusicaRepository.findMusicaIdsByPlaylistid(playlistid);
    }
}
