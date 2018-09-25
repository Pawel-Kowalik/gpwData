package gpwData.service;


import gpwData.dao.GpwDataDAO;
import gpwData.model.GpwData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GpwDataService {

    @Autowired
    GpwDataDAO gpwDataDAO;

    public Iterable<GpwData> findAllGpwData(){
        return gpwDataDAO.findAll();
    }
}
