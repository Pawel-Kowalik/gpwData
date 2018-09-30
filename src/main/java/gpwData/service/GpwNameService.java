package gpwData.service;

import gpwData.dao.GpwNameDAO;
import gpwData.model.GpwName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class GpwNameService {

    private final GpwNameDAO gpwNameDAO;

    @Autowired
    public GpwNameService(GpwNameDAO gpwNameDAO) {
        this.gpwNameDAO = gpwNameDAO;
    }

    public Collection<String> getAllName() {
        Collection<GpwName> gpwNames = (Collection<GpwName>) gpwNameDAO.findAll();
        return gpwNames.stream()
                .map(gpwName -> gpwName.getName())
                .collect(Collectors.toList());
    }
}
