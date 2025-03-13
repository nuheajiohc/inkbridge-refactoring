package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.file.FileCreateResponseDto;
import com.nhnacademy.inkbridge.backend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: FileController.
 *
 * @author jeongbyeonghun
 * @version 2/28/24
 */
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;


    /**
     * 이미지를 서버에 저장하는 api 입니다.
     *
     * @param image MultipartFile
     * @return FileCreateResponseDto
     */
    @PostMapping
    public ResponseEntity<FileCreateResponseDto> uploadBookImages(
        @RequestPart MultipartFile image) {

        return ResponseEntity.status(HttpStatus.CREATED).body(fileService.saveFile(image));
    }

    /**
     * 이미지를 클라이언트에 보여주는 api 입니다.
     *
     * @param fileName RequestParam, String
     * @return byte[]
     */
    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> loadBookImage(@PathVariable String fileName) {
        return fileService.loadFile(fileName);
    }

    /**
     * 이미지를 클라이언트에 보여주는 api 입니다.
     *
     * @param fileId RequestParam, Long
     * @return byte[]
     */
    @GetMapping("/id/{fileId}")
    public ResponseEntity<byte[]> loadBookImageById(@PathVariable(name = "fileId") Long fileId) {
        return fileService.loadFileById(fileId);
    }


}