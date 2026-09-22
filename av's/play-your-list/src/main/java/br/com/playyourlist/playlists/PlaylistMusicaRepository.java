package br.com.playyourlist.playlists;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PlaylistMusicaRepository extends CrudRepository<PlaylistMusica, Integer> {

    boolean existsByPlaylistidAndMusicaid(Integer playlistid, Integer musicaid);

    long deleteByPlaylistidAndMusicaid(Integer playlistid, Integer musicaid);

    long deleteByPlaylistid(Integer playlistid);

    long deleteByMusicaid(Integer musicaid);

    @Query("select pm.musicaid from PlaylistMusica pm where pm.playlistid = :playlistid order by pm.id")
    List<Integer> findMusicaIdsByPlaylistid(@Param("playlistid") Integer playlistid);
}
