package ua.olezha.hotel.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author  Oleh Shklyar
 */

@Data
@Entity
public class GeoPoint {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal longitude;

    private BigDecimal latitude;
}
