package gpwdata;

import gpwdata.service.ActualDateService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class ActualDateServiceTest extends SpringBootTestBasedAbstract {
    @Autowired ActualDateService actualDateService;

    @Test
    public void shouldReturnTodayDate() {
        LocalDate localDate = LocalDate.of(2019, 4, 29);
        LocalDate actualEnableDate = actualDateService.getActualDate(localDate, LocalTime.now());

        Assert.assertNotEquals(DayOfWeek.SATURDAY, actualEnableDate.getDayOfWeek());
        Assert.assertNotEquals(DayOfWeek.SUNDAY, actualEnableDate.getDayOfWeek());
        Assert.assertEquals(DayOfWeek.MONDAY, actualEnableDate.getDayOfWeek());
    }

    @Test
    public void shouldReturnYesterdayDate() {
        LocalDate localDate = LocalDate.of(2019, 4, 27);
        LocalDate actualEnableDate = actualDateService.getActualDate(localDate, LocalTime.now());

        Assert.assertNotEquals(DayOfWeek.SATURDAY, actualEnableDate.getDayOfWeek());
        Assert.assertEquals(DayOfWeek.FRIDAY, actualEnableDate.getDayOfWeek());
    }

    @Test
    public void shouldReturnDateTwoDaysBeforeDate() {
        LocalDate localDate = LocalDate.of(2019, 4, 28);
        LocalDate actualEnableDate = actualDateService.getActualDate(localDate, LocalTime.now());

        Assert.assertNotEquals(DayOfWeek.SUNDAY, actualEnableDate.getDayOfWeek());
        Assert.assertEquals(DayOfWeek.FRIDAY, actualEnableDate.getDayOfWeek());
    }

    @Test
    public void shouldReturnFridayDate() {
        LocalDate localDate = LocalDate.of(2019, 4, 29);
        LocalTime localTime = LocalTime.of(8, 29);
        LocalDate actualEnableDate = actualDateService.getActualDate(localDate, localTime);

        Assert.assertNotEquals(DayOfWeek.MONDAY, actualEnableDate.getDayOfWeek());
        Assert.assertEquals(DayOfWeek.FRIDAY, actualEnableDate.getDayOfWeek());
    }

    @Test
    public void shouldReturnTodayDateBasedOnTime() {
        LocalDate localDate = LocalDate.of(2019, 4, 29);
        LocalTime localTime = LocalTime.of(14, 14);
        LocalDate actualEnableDate = actualDateService.getActualDate(localDate, localTime);

        Assert.assertEquals(DayOfWeek.MONDAY, actualEnableDate.getDayOfWeek());
    }

    @Test
    public void shouldReturnYesterdayDateBasedOnTime() {
        LocalDate localDate = LocalDate.of(2019, 4, 30);
        LocalTime localTime = LocalTime.of(8, 29);
        LocalDate actualEnableDate = actualDateService.getActualDate(localDate, localTime);

        Assert.assertNotEquals(DayOfWeek.THURSDAY, actualEnableDate.getDayOfWeek());
        Assert.assertEquals(DayOfWeek.MONDAY, actualEnableDate.getDayOfWeek());
    }
}
