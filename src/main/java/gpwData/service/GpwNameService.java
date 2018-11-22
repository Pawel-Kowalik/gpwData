package gpwData.service;

import gpwData.dao.GpwNameDAO;
import gpwData.model.GpwName;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GpwNameService {
    private final GpwNameDAO gpwNameDAO;

    public Collection<String> getAllName() {
        Collection<GpwName> gpwNames = (Collection<GpwName>) gpwNameDAO.findAll();
        return gpwNames.stream()
                .map(gpwName -> gpwName.getName())
                .collect(Collectors.toList());
    }
}
