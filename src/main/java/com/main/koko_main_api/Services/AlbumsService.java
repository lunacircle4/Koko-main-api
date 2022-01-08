package com.main.koko_main_api.Services;

import com.main.koko_main_api.Dtos.AlbumsResponseDto;
import com.main.koko_main_api.Dtos.AlbumsSaveRequestDto;
import com.main.koko_main_api.Dtos.AlbumsUpdateRequestDto;
import com.main.koko_main_api.Models.Albums;
import com.main.koko_main_api.Repositories.AlbumsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumsService {
    private final AlbumsRepository albumsRepository;

    @Transactional
    public Long save(AlbumsSaveRequestDto requestDto) {
        return albumsRepository.save(requestDto.toEntity()).getId();
    }

    // transaction이 끝나는순간 변경된 부분을 반영한다.
    @Transactional
    public Long update(Long id, AlbumsUpdateRequestDto dto) {
        Albums album = albumsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        album.update(dto.getTitle());
        return id;
    }

    @Transactional
    public Long delete(Long id) {
        albumsRepository.deleteById(id);
        return id;
    }

    public AlbumsResponseDto findById(Long id) {
        Albums album = albumsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));
        return new AlbumsResponseDto(album);
    }

    public List<AlbumsResponseDto> findAll() {
        return albumsRepository.findAll().parallelStream().map(
                (x) -> {return new AlbumsResponseDto(x);})
                .collect(Collectors.toList());
    }
}
