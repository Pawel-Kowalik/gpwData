package gpwdata.restcontroller;

import gpwdata.model.GpwData;
import gpwdata.model.GpwDataWithoutPercent;
import gpwdata.service.GpwDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/gpw")
public class GpwDataController {

    private final GpwDataService gpwDataService;

    @GetMapping(path = "/data/company/{name}")
    public GpwData getDataByName(@PathVariable String name){
        return gpwDataService.getActualDataByName(name);
    }

    @GetMapping(path = "/exchange/company/{name}")
    public BigDecimal getExchangeByName(@PathVariable String name) {
        return gpwDataService.getActualExchangeByName(name);
    }

    @GetMapping(path = "/highest-companies-data")
    public Collection<GpwDataWithoutPercent> getHighestCompaniesData() {
        return gpwDataService.getHighestCompaniesDataOfDay();
    }

    @GetMapping(path = "/lowest-companies-data")
    public Collection<GpwDataWithoutPercent> getLowestCompaniesData() {
        return gpwDataService.getLowestCompaniesDataOfDay();
    }

    @GetMapping(path = "data/company/{name}/last-days/{dayBefore}")
    public Collection<GpwData> getLastMonthCompanyData(@PathVariable String name, @PathVariable Integer dayBefore) {
        return gpwDataService.getHistoryCompanyExchange(name, dayBefore);
    }
}
