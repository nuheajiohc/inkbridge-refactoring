package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.FileRepository;
import com.nhnacademy.inkbridge.backend.service.ObjectService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

/**
 * class: FileObjectStorageServiceImplTest.
 *
 * @author jeongbyeonghun
 * @version 2/29/24
 */
@ExtendWith(MockitoExtension.class)
class FileObjectStorageServiceImplTest {

    @Mock
    private ObjectService objectService;
    @Mock
    private FileRepository fileRepository;


    @InjectMocks
    FileObjectStorageServiceImpl fileObjectStorageService;

    private static String fileName;
    private static String fileUrl;
    private static Long fileId;
    private static File file;
    private static byte[] fileByteArray;

    @BeforeAll
    static void setUp() {
        fileId = 1L;
        fileName = "testFile.png";
        fileUrl = "https://inkbridge.store/image-load/testFile.png";

        file = File.builder().fileName(fileName).fileUrl(fileUrl).fileId(fileId).build();
        fileByteArray = new byte[]{1, 2, 3, 4, 5};
    }

    @Test
    void saveFile() {
        when(objectService.uploadObject(any())).thenReturn(fileName);
        when(fileRepository.save(any())).thenReturn(file);
        assertEquals(fileId, fileObjectStorageService.saveFile(any()).getFileId());
        assertEquals(fileName, fileObjectStorageService.saveFile(any()).getFileName());
    }

    @Test
    void loadFile() {
        when(objectService.downloadObject(fileName)).thenReturn(ResponseEntity.ok(fileByteArray));
        assertEquals(ResponseEntity.ok(fileByteArray), fileObjectStorageService.loadFile(fileName));
    }

    @Test
    void loadFileById() {
        when(fileRepository.findById(fileId)).thenReturn(Optional.of(file));
        when(objectService.downloadObject(fileName)).thenReturn(ResponseEntity.ok(fileByteArray));
        assertEquals(ResponseEntity.ok(fileByteArray),
            fileObjectStorageService.loadFileById(fileId));
    }

    @Test
    void loadFileByIdWhenFileNotFound() {
        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> fileObjectStorageService.loadFileById(fileId));
    }
}