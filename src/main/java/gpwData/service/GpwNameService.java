package gpwData.service;

import gpwData.dao.GpwNameDAO;
import gpwData.model.GpwName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GpwNameService {

    @Autowired
    GpwNameDAO gpwNameDAO;

    public Iterable<GpwName> findAllGpwData() {
        return gpwNameDAO.findAll();
    }
}
