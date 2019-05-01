package gpwdata.service;


import gpwdata.dao.GpwDataDAO;
import gpwdata.model.GpwData;
import gpwdata.model.GpwDataWithoutPercent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
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
    private final ActualDateService actualDateService;

    public GpwData getActualDataByName(String name){
        return gpwDataDAO.findFirstByNameOrderByDateDescTimeDesc(name.toUpperCase());
    }

    public BigDecimal getActualExchangeByName(String name){
        GpwData gpwData = gpwDataDAO.findFirstByNameOrderByDateDescTimeDesc(name);
        return gpwData.getExchange();
    }

    public Collection<GpwDataWithoutPercent> getHighestCompaniesDataOfDay(Date actualDate){
         long count = (long) sortGpwDataWithoutPercent(actualDate).size();
         Collection<GpwDataWithoutPercent> gpwDataWithoutPercents = sortGpwDataWithoutPercent(actualDate).stream()
                 .skip(count - 5)
                 .collect(Collectors.toList());

         Collections.reverse((List<?>) gpwDataWithoutPercents);

         return gpwDataWithoutPercents;
    }

    public Collection<GpwDataWithoutPercent> getLowestCompaniesDataOfDay(Date actualDate){
        return sortGpwDataWithoutPercent(actualDate).stream()
                .limit(5)
                .collect(Collectors.toList());
    }

    public Collection<GpwData> getHistoryCompanyExchange(String name, Date actualDate, Integer dayBefore){
        return distinctLastCompanyData(name,actualDate, dayBefore);
    }

    private Collection<GpwData> distinctLastCompanyData(String name, Date actualDate, Integer dayBefore) {
        Date startDate = Date.valueOf(actualDate.toLocalDate().minus(Period.ofDays(dayBefore)));
        Collection<GpwData> lastMonthCompanyData = gpwDataDAO.findGpwDataByDateBetweenAndNameOrderByDateDescTimeDesc(
                startDate, actualDate, name);
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
                    .idGpwData(gpwData.getIdGpwData())
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

}
