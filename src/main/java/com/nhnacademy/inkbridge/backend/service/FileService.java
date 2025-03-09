package com.nhnacademy.inkbridge.backend.service;


import com.nhnacademy.inkbridge.backend.dto.file.FileCreateResponseDto;
import com.nhnacademy.inkbridge.backend.entity.File;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: FileService.
 *
 * @author jeongbyeonghun
 * @version 2/27/24
 */
public interface FileService {

    /**
     * 업로드된 파일을 서버에 저장하고, 파일 정보를 데이터베이스에 기록합니다.
     *
     * @param file 저장할 파일
     * @return 저장된 파일의 정보를 담은 {@link File} 객체
     */
    FileCreateResponseDto saveFile(MultipartFile file);

    File saveThumbnail(MultipartFile file);


    /**
     * 지정된 파일 이름으로 저장된 파일을 로드합니다. 파일이 존재하지 않을 경우, 대체 이미지를 반환합니다.
     *
     * @param fileName 로드할 파일의 이름
     * @return 파일 리소스
     */
    ResponseEntity<byte[]> loadFile(String fileName);

    /**
     * 지정된 파일 이름으로 저장된 파일을 로드합니다. 파일이 존재하지 않을 경우, 대체 이미지를 반환합니다.
     *
     * @param fileId 로드할 파일의 이름
     * @return 파일 리소스
     */
    ResponseEntity<byte[]> loadFileById(Long fileId);

}

