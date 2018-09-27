package ua.olezha.hotel.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * @author  Oleh Shklyar
 */

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String password;

    @Lob
    private String remark;
}
