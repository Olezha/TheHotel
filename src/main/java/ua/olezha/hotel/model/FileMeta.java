package ua.olezha.hotel.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author  Oleh Shklyar
 */

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileMeta {

    @Id
    @GeneratedValue
    Long id;

    String uuid;

    String filename;
}
