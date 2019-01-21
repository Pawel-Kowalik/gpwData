package gpwData.dao;

import gpwData.model.GpwData;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.Collection;


public interface GpwDataDAO extends CrudRepository<GpwData, Long> {

    GpwData findFirstByNameOrderByDateDescTimeDesc(String name);

    Collection<GpwData> findFirst5GpwDataByDateOrderByChangePercentDescTimeDesc(Date date);

    Collection<GpwData> findFirst5GpwDataByDateOrderByChangePercentAscTimeDesc(Date date);

    Collection<GpwData> findGpwDataByDateBetweenAndNameOrderByDateDescTimeDesc(Date startDate, Date endDate, String name);

    Collection<GpwData> findAllByDate(Date date);
}
