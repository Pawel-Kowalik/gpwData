package gpwData.service;


import gpwData.dao.GpwDataDAO;
import gpwData.model.GpwData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.Calendar;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GpwDataService {
    private final GpwDataDAO gpwDataDAO;

    public GpwData getActualDataByName(String name){
        return gpwDataDAO.findFirstByNameOrderByDateDescTimeDesc(name.toUpperCase());
    }

    public BigDecimal getActualExchangeByName(String name){
        GpwData gpwData = gpwDataDAO.findFirstByNameOrderByDateDescTimeDesc(name);
        return gpwData.getExchange();
    }

    //TODO check is correct data

    public Collection<GpwData> getHighestCompaniesDataOfDay(){
        Date date = Date.valueOf(checkWhichDateIsAllowed());
        return gpwDataDAO.findFirst5GpwDataByDateOrderByChangePercentDescTimeDesc(date);
    }
    //TODO check is correct data

    public Collection<GpwData> getLowestCompaniesDataOfDay(){
        Date date = Date.valueOf(checkWhichDateIsAllowed());
        return gpwDataDAO.findFirst5GpwDataByDateOrderByChangePercentAscTimeDesc(date);
    }

    public Collection<GpwData> getLastMonthCompanyExchange(String name){
        return distinctLastMonthCompanyData(name);
    }

    private Collection<GpwData> distinctLastMonthCompanyData(String name) {
        Date startDate = Date.valueOf(getActualDate(30));
        Date endDate = Date.valueOf(getActualDate(0));
        Collection<GpwData> lastMonthCompanyData = gpwDataDAO.findGpwDataByDateBetweenAndNameOrderByDateDescTimeDesc(
                startDate, endDate, name);
        return lastMonthCompanyData.stream()
                .filter(distinctByKey(GpwData::getDate))
                .collect(Collectors.toList());
    }

    private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }


    private LocalDate checkWhichDateIsAllowed(){
        Calendar calendar = Calendar.getInstance();

        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            return getActualDate(1);
        }

        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return getActualDate(2);
        }

        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && isBeforeHourWhenStockIsOpen()) {
            return getActualDate(3);
        }

        if(isBeforeHourWhenStockIsOpen()){
            return getActualDate(1);
        }

        return getActualDate(0);
    }

    private boolean isBeforeHourWhenStockIsOpen() {
        return LocalTime.now().isBefore(LocalTime.of(9, 30));
    }

    private LocalDate getActualDate(Integer dayBefore){
        return LocalDate.now().minus(Period.ofDays(dayBefore));
    }

}
