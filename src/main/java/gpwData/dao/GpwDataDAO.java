package gpwData.dao;

import gpwData.model.GpwData;
import org.springframework.data.repository.CrudRepository;


public interface GpwDataDAO extends CrudRepository<GpwData, Long> {
}
