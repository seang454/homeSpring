package kh.edu.istasd.fswdapi.service;

import kh.edu.istasd.fswdapi.dto.media.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    /**
     * Updalod single file**/
    MediaResponse upload(MultipartFile file);
    List<MediaResponse> uploadMultiFile(List<MultipartFile> files);
    ResponseEntity<Resource> download(String filename, String extension);
    ResponseEntity<String> deleteFile(String filename, String extension);
}
