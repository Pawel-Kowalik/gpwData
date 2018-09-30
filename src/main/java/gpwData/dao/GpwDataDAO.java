package gpwData.dao;

import gpwData.model.GpwData;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;


public interface GpwDataDAO extends CrudRepository<GpwData, Long> {

    GpwData findFirstByNameOrderByDateDescTimeDesc(String name);
}
