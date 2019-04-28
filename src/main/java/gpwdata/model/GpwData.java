package gpwdata.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;


@Entity
@Data
public class GpwData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGpwData;

    private String name;

    private BigDecimal exchange;

    private Double changes;

    private String changePercent;

    private Integer numberOfTransaction;

    private Integer turnover;

    private BigDecimal openPrice;

    private BigDecimal maxPrice;

    private BigDecimal minPrice;

    private Time time;

    private Date date;

}







