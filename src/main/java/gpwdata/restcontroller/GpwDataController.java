package gpwdata.restcontroller;

import gpwdata.model.GpwData;
import gpwdata.model.GpwDataWithoutPercent;
import gpwdata.service.ActualDateService;
import gpwdata.service.GpwDataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/gpw")
public class GpwDataController {

    private final GpwDataService gpwDataService;
    private final ActualDateService actualDateService;

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
        Date actualEnableDate = Date.valueOf(actualDateService.getActualDate(LocalDate.now(), LocalTime.now()));
        return gpwDataService.getHighestCompaniesDataOfDay(actualEnableDate);
    }

    @GetMapping(path = "/lowest-companies-data")
    public Collection<GpwDataWithoutPercent> getLowestCompaniesData() {
        Date actualEnableDate = Date.valueOf(actualDateService.getActualDate(LocalDate.now(), LocalTime.now()));
        return gpwDataService.getLowestCompaniesDataOfDay(actualEnableDate);
    }

    @GetMapping(path = "data/company/{name}/last-days/{dayBefore}")
    public Collection<GpwData> getLastMonthCompanyData(@PathVariable String name, @PathVariable Integer dayBefore) {
        Date actualEnableDate = Date.valueOf(actualDateService.getActualDate(LocalDate.now(), LocalTime.now()));
        return gpwDataService.getHistoryCompanyExchange(name, actualEnableDate, dayBefore);
    }
}
