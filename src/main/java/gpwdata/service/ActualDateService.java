package gpwdata.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

@Service
public class ActualDateService {

    public LocalDate getActualDate(LocalDate date, LocalTime time){
        return checkWhichDateIsAllowed(date, time);
    }

    private LocalDate checkWhichDateIsAllowed(LocalDate date, LocalTime time) {
        if(date.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
            return getActualDate(date,1);
        }

        if(date.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            return getActualDate(date,2);
        }

        if(date.getDayOfWeek().equals(DayOfWeek.MONDAY) && isBeforeHourWhenStockIsOpen(time)) {
            return getActualDate(date,3);
        }

        if(isBeforeHourWhenStockIsOpen(time)){
            return getActualDate(date,1);
        }

        return getActualDate(date,0);
    }

    private LocalDate getActualDate(LocalDate date, Integer dayBefore){
        return date.minus(Period.ofDays(dayBefore));
    }

    private boolean isBeforeHourWhenStockIsOpen(LocalTime time) {
        return time.isBefore(LocalTime.of(9, 30));
    }
}
