package gpwData.service;


import gpwData.dao.GpwDataDAO;
import gpwData.model.GpwData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GpwDataService {

    private final GpwDataDAO gpwDataDAO;

    @Autowired
    public GpwDataService(GpwDataDAO gpwDataDAO) {
        this.gpwDataDAO = gpwDataDAO;
    }

    public GpwData getActualDataByName(String name){
        return gpwDataDAO.findFirstByNameOrderByDateDescTimeDesc(name.toUpperCase());
    }

    public BigDecimal getActualExchangeByName(String name){
        GpwData gpwData = gpwDataDAO.findFirstByNameOrderByDateDescTimeDesc(name);
        return gpwData.getExchange();
    }
}
