package gpwdata.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.Calendar;

@Service
public class ActualDateService {

    public LocalDate getActualDate(){
        Calendar calendar = Calendar.getInstance();
        return checkWhichDateIsAllowed(calendar);
    }

    LocalDate getActualDate(Integer dayBefore){
        return LocalDate.now().minus(Period.ofDays(dayBefore));
    }

    private LocalDate checkWhichDateIsAllowed(Calendar calendar) {
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
}
