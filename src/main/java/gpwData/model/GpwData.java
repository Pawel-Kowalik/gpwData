package gpwData.model;

import lombok.Data;
import lombok.Getter;

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

    public GpwData(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_gpw_data;

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







