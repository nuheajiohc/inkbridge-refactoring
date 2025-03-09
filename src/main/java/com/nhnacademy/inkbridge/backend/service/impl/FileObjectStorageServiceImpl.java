package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.file.FileCreateResponseDto;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.enums.FileMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.FileRepository;
import com.nhnacademy.inkbridge.backend.service.FileService;
import com.nhnacademy.inkbridge.backend.service.ObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: FileObjectStorageServiceImpl.
 *
 * @author jeongbyeonghun
 * @version 2/27/24
 */

@Service
@RequiredArgsConstructor
public class FileObjectStorageServiceImpl implements FileService {


    private final FileRepository fileRepository;
    private final ObjectService objectService;


    /**
     * 업로드된 파일을 서버에 저장하고, 파일 정보를 데이터베이스에 기록합니다.
     *
     * @param file 저장할 파일
     * @return 저장된 파일의 정보를 담은 {@link File} 객체
     */
    @Override
    @Transactional
    public FileCreateResponseDto saveFile(MultipartFile file) {
        String fileName = objectService.uploadObject(file);
        String url = "https://inkbridge.store/image-load/";
        File newFile = fileRepository.save(
            File.builder().fileName(fileName).fileUrl(url + fileName).build());

        return FileCreateResponseDto.builder().fileId(newFile.getFileId())
            .fileName(newFile.getFileName()).build();
    }

    @Override
    @Transactional
    public File saveThumbnail(MultipartFile file) {
        String fileName = objectService.uploadObject(file);
        String url = "https://inkbridge.store/image-load/";

        return fileRepository.save(
            File.builder().fileName(fileName).fileUrl(url + fileName).build());
    }


    /**
     * 지정된 파일 이름으로 저장된 파일을 로드합니다. 파일이 존재하지 않을 경우, 대체 이미지를 반환합니다.
     *
     * @param fileName 로드할 파일의 이름
     * @return 파일 리소스
     */
    @Override
    @Transactional
    public ResponseEntity<byte[]> loadFile(String fileName) {
        return objectService.downloadObject(fileName);
    }


    /**
     * 지정된 파일 이름으로 저장된 파일을 로드합니다. 파일이 존재하지 않을 경우, 대체 이미지를 반환합니다.
     *
     * @param fileId 로드할 파일의 이름
     * @return 파일 리소스
     */
    @Override
    @Transactional
    public ResponseEntity<byte[]> loadFileById(Long fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new NotFoundException(
            FileMessageEnum.FILE_NOT_FOUND_ERROR.getMessage()));
        return objectService.downloadObject(file.getFileName());
    }

}