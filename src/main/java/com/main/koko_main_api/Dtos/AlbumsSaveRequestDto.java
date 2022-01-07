package com.main.koko_main_api.Dtos;

import com.main.koko_main_api.Models.Albums;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Getter: 선언된 필드의 get메소드 생성
 * RequiredArgsConstructor: final 필드가 포함된 생성자를 생성
 */
@NoArgsConstructor
@Getter
public class AlbumsSaveRequestDto {
    private String title;

    @Builder
    public AlbumsSaveRequestDto(String title) {
        this.title = title;
    }

    public Albums toEntity() {
        return Albums.builder()
                .title(title)
                .build();
    }
}
