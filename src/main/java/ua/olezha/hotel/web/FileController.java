package ua.olezha.hotel.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ua.olezha.hotel.model.FileMeta;
import ua.olezha.hotel.repository.FileMetaRepository;
import ua.olezha.hotel.service.FileStorageService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Oleh Shklyar
 */

@Controller
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileController {

    FileMetaRepository fileMetaRepository;

    FileStorageService fileStorageService;

    @ResponseBody
    @GetMapping("/files/{uuid}")
    public ResponseEntity<Resource> serveFile(@PathVariable String uuid) throws UnsupportedEncodingException {
        Resource file = fileStorageService.loadAsResource(uuid);
        FileMeta fileMeta = fileMetaRepository.findOneByUuid(uuid);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" +
                        URLEncoder.encode(fileMeta.getFilename(), "UTF-8")
                                .replace("+", "%20") + "\"")
                .body(file);
    }

    @ResponseBody
    @PostMapping("/management/files")
    public List<FileMeta> uploadFileMulti(MultipartHttpServletRequest request) {
        return request.getFileMap().values().stream()
                .map(fileStorageService::store)
                .filter(fileMeta -> !StringUtils.isEmpty(fileMeta.getUuid()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleStorageFileNotFound(RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
}
