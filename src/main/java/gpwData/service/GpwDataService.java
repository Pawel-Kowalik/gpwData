package gpwData.service;


import gpwData.dao.GpwDataDAO;
import gpwData.model.GpwData;
import gpwData.model.GpwDataWithoutPercent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.*;
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

    public Collection<GpwDataWithoutPercent> getHighestCompaniesDataOfDay(){
         Date date = Date.valueOf(checkWhichDateIsAllowed());
         long count = sortGpwDataWithoutPercent(date).stream().count();
         Collection<GpwDataWithoutPercent> gpwDataWithoutPercents = sortGpwDataWithoutPercent(date).stream()
                 .skip(count - 5)
                 .collect(Collectors.toList());

         Collections.reverse((List<?>) gpwDataWithoutPercents);

         return gpwDataWithoutPercents;
    }

    public Collection<GpwDataWithoutPercent> getLowestCompaniesDataOfDay(){
        Date date = Date.valueOf(checkWhichDateIsAllowed());
        return sortGpwDataWithoutPercent(date).stream()
                .limit(5)
                .collect(Collectors.toList());
    }

    //TODO check price format and round to .00
    public Collection<GpwData> getLastMonthCompanyExchange(String name){
        return distinctLastMonthCompanyData(name, 30);
    }

    public Collection<GpwData> getLastHalfMonthComapnyExchange(String name){
        return distinctLastHalfMonthCompanyData(name, 15);
    }

    private Collection<GpwData> distinctLastMonthCompanyData(String name, Integer dayBefore) {
        Date startDate = Date.valueOf(getActualDate(dayBefore));
        Date endDate = Date.valueOf(getActualDate(0));
        Collection<GpwData> lastMonthCompanyData = gpwDataDAO.findGpwDataByDateBetweenAndNameOrderByDateDescTimeDesc(
                startDate, endDate, name);
        return lastMonthCompanyData.stream()
                .filter(distinctByKey(GpwData::getDate))
                .sorted((date1, date2) -> compareTo(date1.getDate(), date2.getDate()))
                .collect(Collectors.toList());
    }

    private Collection<GpwData> distinctLastHalfMonthCompanyData(String name, Integer dayBefore) {
        Date startDate = Date.valueOf(getActualDate(dayBefore));
        Date endDate = Date.valueOf(getActualDate(0));
        Collection<GpwData> lastMonthCompanyData = gpwDataDAO.findGpwDataByDateBetweenAndNameOrderByDateDescTimeDesc(
                startDate, endDate, name);

        return lastMonthCompanyData.stream()
                .filter(distinctByKey(GpwData::getDate))
                .sorted((date1, date2) -> compareTo(date1.getDate(), date2.getDate()))
                .collect(Collectors.toList());
    }

    private Collection<GpwDataWithoutPercent> sortGpwDataWithoutPercent(Date date) {
        return mapGwpDataToGpwDataWithoutPercent(date).stream()
                .filter(distinctByKey(GpwDataWithoutPercent::getName))
                .sorted(Comparator.comparingDouble(GpwDataWithoutPercent::getChangePercent))
                .collect(Collectors.toList());
    }

    private Collection<GpwDataWithoutPercent> mapGwpDataToGpwDataWithoutPercent(Date date) {
        Collection<GpwData> gpwDataByDate = gpwDataDAO.findAllByDate(date);
        Set<GpwDataWithoutPercent> gpwDataWithoutPercents = new HashSet<>();

        for(GpwData gpwData : gpwDataByDate) {
            gpwDataWithoutPercents.add(GpwDataWithoutPercent.builder()
                    .id_gpw_data(gpwData.getId_gpw_data())
                    .name(gpwData.getName())
                    .exchange(gpwData.getExchange())
                    .changes(gpwData.getChanges())
                    .changePercent(convertStringToDoublePercent(gpwData.getChangePercent()))
                    .numberOfTransaction(gpwData.getNumberOfTransaction())
                    .turnover(gpwData.getTurnover())
                    .openPrice(gpwData.getOpenPrice())
                    .maxPrice(gpwData.getMaxPrice())
                    .minPrice(gpwData.getMinPrice())
                    .time(gpwData.getTime())
                    .date(gpwData.getDate())
                    .build());
        }
        return gpwDataWithoutPercents;
    }

    private Double convertStringToDoublePercent(String sPercent) {
        return Double.valueOf(sPercent.substring(0, sPercent.length()-1).replace(",", "."));
    }

    private int compareTo(Date date1, Date date2) {
        return date1.compareTo(date2);
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
