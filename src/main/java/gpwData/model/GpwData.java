package gpwData.model;

import lombok.Builder;
import lombok.Data;


import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;


@Data
//@Builder
@Entity
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







