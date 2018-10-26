package ua.olezha.hotel.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ua.olezha.hotel.model.FileMeta;
import ua.olezha.hotel.repository.FileMetaRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;

/**
 * @author  Oleh Shklyar
 */

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileStorageServiceFileSystemImpl implements FileStorageService {

    Path rootLocation;

    FileMetaRepository fileMetaRepository;

    public FileStorageServiceFileSystemImpl(FileMetaRepository fileMetaRepository,
                                            @Value("${file-storage.url}") Path rootLocation) {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (FileAlreadyExistsException ignored) {}
        catch (IOException e) {
            throw new StorageException("Could not initialize file storage", e);
        }

        this.fileMetaRepository = fileMetaRepository;
        this.rootLocation = rootLocation;
    }

    @Override
    public FileMeta store(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (file.isEmpty())
            throw new StorageException("Failed to store empty file " + originalFileName);

        try {
            String uuid = UUID.randomUUID().toString();

            Files.copy(file.getInputStream(), rootLocation.resolve(uuid),
                    StandardCopyOption.REPLACE_EXISTING);

            FileMeta fileMeta = new FileMeta();
            fileMeta.setFilename(originalFileName);
            fileMeta.setUuid(uuid);
            fileMetaRepository.save(fileMeta);

            return fileMeta;
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + originalFileName, e);
        }
    }

    @Override
    public Resource loadAsResource(String uuid) {
        try {
            Path file = Paths.get(rootLocation.toString()).resolve(uuid);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;
            else
                throw new StorageFileNotFoundException("Could not read file: " + uuid);
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + uuid, e);
        }
    }
}

class StorageException extends RuntimeException {

    StorageException(String message) {
        super(message);
    }

    StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

class StorageFileNotFoundException extends StorageException {

    StorageFileNotFoundException(String message) {
        super(message);
    }

    StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
