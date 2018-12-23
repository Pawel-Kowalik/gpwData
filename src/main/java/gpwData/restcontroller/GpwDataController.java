package gpwData.restcontroller;

import gpwData.model.GpwData;
import gpwData.service.GpwDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/")
public class GpwDataController {

    private final GpwDataService gpwDataService;

    @GetMapping(path = "/data/{name}")
    public GpwData getDataByName(@PathVariable String name){
        return gpwDataService.getActualDataByName(name);
    }

    @GetMapping(path = "/exchange/{name}")
    public BigDecimal getExchangeByName(@PathVariable String name) {
        return gpwDataService.getActualExchangeByName(name);
    }

    @GetMapping(path = "/higestCompaniesData")
    public Collection<GpwData> getHighestCompaniesData() {
        return gpwDataService.getHighestCompaniesDataOfDay();
    }

    @GetMapping(path = "/lowestCompaniesData")
    public Collection<GpwData> getLowestCompaniesData() {
        return gpwDataService.getLowestCompaniesDataOfDay();
    }

    @GetMapping(path = "companyLastMonth/{name}")
    public Collection<GpwData> getLastMonthCompanyData(@PathVariable String name) {
        return gpwDataService.getLastMonthCompanyExchange(name);
    }

    @GetMapping(path = "companyLastHalfMonth/{name}")
    public Collection<GpwData> getLastHalfMonthCompanyData(@PathVariable String name) {
        return gpwDataService.getLastHalfMonthComapnyExchange(name);
    }
}
