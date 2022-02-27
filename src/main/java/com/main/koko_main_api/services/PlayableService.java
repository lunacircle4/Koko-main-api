package com.main.koko_main_api.services;

import com.main.koko_main_api.dtos.playable.PlayableDetailResponseEntityDto;
import com.main.koko_main_api.dtos.playable.PlayableListResponseEntityDto;
import com.main.koko_main_api.dtos.playable.PlayableSaveEntityDto;
import com.main.koko_main_api.dtos.playable.PlayableSavePayload;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.domains.Playable;
import com.main.koko_main_api.repositories.BpmRepository;
import com.main.koko_main_api.repositories.music.MusicRepository;
import com.main.koko_main_api.repositories.playable.PlayableRepository;

import com.main.koko_main_api.repositories.playable.PlayableSearchRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PlayableService extends URIToID {
    private final PlayableRepository playableRepository;
    private final MusicRepository musicRepository;
    private final BpmRepository bpmRepository;
    private final PlayableSearchRepository playableSearchRepository;

    @Transactional
    public PlayableDetailResponseEntityDto save(PlayableSavePayload dto) {
        Long music_id = this.convertURItoID(dto.getMusic());
        Music music = musicRepository.findById(music_id).orElseThrow(
                () -> new IllegalArgumentException(
                        "해당 게시글이 없습니다. id= " + music_id));
        PlayableSaveEntityDto saveDto = PlayableSaveEntityDto
                .builder().music(music).level(dto.getLevel()).build();

        Playable playable = playableRepository.save(saveDto.toEntity());

        return new PlayableDetailResponseEntityDto(playable);
    }

    public PlayableDetailResponseEntityDto findById(Long id) {
        Playable p = playableRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(
                        "해당 게시글이 없습니다. id= " + id));

        return new PlayableDetailResponseEntityDto(p);
    }

    public Page<PlayableListResponseEntityDto> findAll(Pageable pageable, String play_type) {
        return playableRepository.findAll(pageable).map(
                p -> new PlayableListResponseEntityDto(p));
    }
}
