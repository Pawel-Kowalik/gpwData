package gpwdata.service;

import gpwdata.dao.GpwNameDAO;
import gpwdata.model.GpwName;
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
                .map(GpwName::getName)
                .collect(Collectors.toList());
    }
}
