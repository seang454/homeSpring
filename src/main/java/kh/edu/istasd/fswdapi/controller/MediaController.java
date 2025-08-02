package kh.edu.istasd.fswdapi.controller;

import kh.edu.istasd.fswdapi.domain.Media;
import kh.edu.istasd.fswdapi.dto.media.MediaResponse;
import kh.edu.istasd.fswdapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;
    @PostMapping
    public MediaResponse upload(@RequestPart MultipartFile file) {
        return mediaService.upload(file);
    }

    @PostMapping("/files")
    public List<MediaResponse> uploadMultiFile(@RequestPart("files") List<MultipartFile> files) {
        log.info("uploadMultiFile {}", files);
        return mediaService.uploadMultiFile(files);
    }
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        log.info("download {}", filename);
        int dotIndex = filename.lastIndexOf('.');
        String name = filename.substring(0, dotIndex);
        String extension = filename.substring(dotIndex + 1);
        return mediaService.download(name, extension);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<String> delete(@PathVariable String filename) {
        int dotIndex = filename.lastIndexOf('.');
        String name = filename.substring(0, dotIndex);
        String extension = filename.substring(dotIndex + 1);
        return mediaService.deleteFile(name, extension);
    }
}
