package ua.olezha.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.olezha.hotel.model.FileMeta;

import java.util.List;

/**
 * @author Oleh Shklyar
 */

public interface FileMetaRepository extends JpaRepository<FileMeta, Long> {

    FileMeta findOneByUuid(String uuid);

    List<FileMeta> findAllByUuidIn(List<String> uuids);
}
