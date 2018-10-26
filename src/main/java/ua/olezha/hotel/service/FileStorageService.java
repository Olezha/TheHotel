package ua.olezha.hotel.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ua.olezha.hotel.model.FileMeta;

/**
 * @author Oleh Shklyar
 */

public interface FileStorageService {

    FileMeta store(MultipartFile file);

    Resource loadAsResource(String uuid);
}
