package kh.edu.istasd.fswdapi.service.impl;

import kh.edu.istasd.fswdapi.domain.Media;
import kh.edu.istasd.fswdapi.dto.media.MediaResponse;
import kh.edu.istasd.fswdapi.repository.MediaRepository;
import kh.edu.istasd.fswdapi.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;
    @Value("${media.server-path}")
    private String serverPath;
    @Value("${media.base-uri}")
    private String baseUri;
    @Override
    public MediaResponse upload(MultipartFile file) {

        String name = UUID.randomUUID().toString();

        //get latest index of .
        int lastIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        //Extract extension
        String extension = file.getOriginalFilename().substring(lastIndex + 1);
        //create path object
        Path path = Paths.get(serverPath + String.format("%s.%s",name,extension));
        try {
            Files.copy(file.getInputStream(), path);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
        Media media = new Media();
        media.setName(name);
        media.setMimeType(file.getContentType());
        media.setIsDeleted(false);
        media.setExtension(extension);
        mediaRepository.save(media);
        return MediaResponse.builder()
                .name(media.getName())
                .mimeType(file.getContentType())
                .uri(baseUri + String.format("%s.%s",name,extension))
                .size(file.getSize())
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiFile(List<MultipartFile> files) {
        List<MediaResponse> mediaResponses = new ArrayList<>();
        for (MultipartFile file : files) {
            MediaResponse mediaResponse = upload(file);
            mediaResponses.add(mediaResponse);
        }
        files.forEach(System.out::println);
        return mediaResponses;
    }

    @Override
    public ResponseEntity<Resource> download(String filename, String extension) {
        Media media = mediaRepository.findByNameAndExtensionAndIsDeletedFalse(filename, extension);
        if (media == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, filename + " not found");
        }
        String fullPath = serverPath + String.format("%s.%s", media.getName(), media.getExtension());
        File file = new File(fullPath);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream"; // fallback
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<String> deleteFile(String filename, String extension) {
        Media media = mediaRepository.findByNameAndExtensionAndIsDeletedFalse(filename, extension);
        if (media == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, filename + " not found");
        }
        String fullPath = serverPath + String.format("%s.%s", media.getName(), media.getExtension());
        File file = new File(fullPath);
        try {
            // Prevent path traversal (security)
            if (filename.contains("..")) {
                return ResponseEntity.badRequest().body("Invalid filename");
            }
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: " + filename);
            }

            // Attempt to delete
            if (file.delete()) {
                mediaRepository.delete(media);
                return ResponseEntity.ok("File deleted successfully: " + filename);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Could not delete file: " + filename);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting file: " + e.getMessage());
        }
    }

}
