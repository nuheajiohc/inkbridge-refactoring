package com.nhnacademy.inkbridge.backend.dto.file;

import lombok.Builder;
import lombok.Getter;

/**
 * 파일 생성 요청에 대한 응답 데이터를 담는 DTO(Data Transfer Object) 클래스입니다.
 * 파일 생성 후 반환되는 파일의 식별자(ID)와 파일 이름을 포함합니다.
 * 이 클래스는 클라이언트에 파일 생성 결과를 전달하는 데 사용됩니다.
 *
 * @author jeongbyeonghun
 * @version 2/28/24
 */
@Getter
public class FileCreateResponseDto {

    private final Long fileId;
    private final String fileName;

    /**
     * 파일 생성 응답 DTO 객체를 생성합니다.
     *
     * @param fileId 생성된 파일의 ID
     * @param fileName 생성된 파일의 이름
     */
    @Builder
    public FileCreateResponseDto(Long fileId, String fileName) {
        this.fileId = fileId;
        this.fileName = fileName;
    }

}

