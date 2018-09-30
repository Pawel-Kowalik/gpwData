package gpwData.restcontroller;

import gpwData.model.GpwData;
import gpwData.service.GpwDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/")
public class GpwDataController {

    private final GpwDataService gpwDataService;

    @Autowired
    public GpwDataController(GpwDataService gpwDataService) {
        this.gpwDataService = gpwDataService;
    }

    @GetMapping(path = "/data/{name}")
    public GpwData getDataByName(@PathVariable String name){
        return gpwDataService.getActualDataByName(name);
    }

    @GetMapping(path = "/exchange/{name}")
    public BigDecimal getExchangeByName(@PathVariable String name){
        return gpwDataService.getActualExchangeByName(name);
    }
}
