package gpwdata;

import gpwdata.model.GpwData;
import gpwdata.model.GpwDataWithoutPercent;
import gpwdata.service.ActualDateService;
import gpwdata.service.GpwDataService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;

public class GpwDataServiceTest extends SpringBootTestBasedAbstract {
    @Autowired ActualDateService actualDateService;
    @Autowired GpwDataService gpwDataService;

    private ArrayList<GpwDataWithoutPercent> highestGpwDataWithoutPercents = new ArrayList<>();
    private ArrayList<GpwDataWithoutPercent> lowestGpwDataWithoutPercents = new ArrayList<>();
    private ArrayList<GpwData> historyGpwData = new ArrayList<>();
    private BigDecimal actualCompanyExchange;
    private LocalDate actualEnableDate = LocalDate.of(2018, 9, 6);

    @Before
    public void fillData() {
        highestGpwDataWithoutPercents.addAll(gpwDataService.getHighestCompaniesDataOfDay(Date.valueOf(actualEnableDate)));
        lowestGpwDataWithoutPercents.addAll(gpwDataService.getLowestCompaniesDataOfDay(Date.valueOf(actualEnableDate)));
        historyGpwData.addAll(gpwDataService.getHistoryCompanyExchange("LPP", Date.valueOf(actualEnableDate), 1));
        actualCompanyExchange = gpwDataService.getActualExchangeByName("LPP");
    }

    @Test
    public void shouldGpwDataIsNotNull() {
        Assert.assertNotNull(gpwDataService.getActualDataByName("LPP"));
    }

    @Test
    public void shouldGpwDataIsActual() {
        Date gpwDate = gpwDataService.getActualDataByName("LPP").getDate();
        Assert.assertEquals(Date.valueOf(actualEnableDate), gpwDate);
    }

    @Test
    public void  shouldActualGpwDataExchangeIsNotNull() {
        Assert.assertNotNull(actualCompanyExchange);
    }

    @Test
    public void shouldActualExchangeHasCorrectValue() {
        Assert.assertEquals(new BigDecimal("8985.00"), actualCompanyExchange);
    }

    @Test
    public void shouldHighestGpwDataListSizeIs5() {
        Assert.assertEquals(5, highestGpwDataWithoutPercents.size());
    }

    @Test
    public void shouldHighestGpwDataListSortedDescByPercent() {
        Assert.assertTrue(isListSortedDesc(highestGpwDataWithoutPercents));
    }

    @Test
    public void shouldLowestGpwDataListSizeIs5() {
        Assert.assertEquals(5, lowestGpwDataWithoutPercents.size());
    }

    @Test
    public void shouldLowestGpwDataListSortedAscByPercent(){
        Assert.assertTrue(!isListSortedDesc(lowestGpwDataWithoutPercents));
    }

    @Test
    public void shouldHistoryGpwDataListSizeIs2() {
        Assert.assertEquals(2, historyGpwData.size());
    }

    @Test
    public void shouldHistoryGpwDataListHasCorrectData() {
        GpwData gpwData1 = historyGpwData.get(0);
        GpwData gpwData2 = historyGpwData.get(1);

        Assert.assertThat(gpwData1.getName(), is("LPP"));
        Assert.assertThat(gpwData1.getExchange(), is(new BigDecimal("9000.00")));
        Assert.assertThat(gpwData1.getChanges(), is(201.0));
        Assert.assertThat(gpwData1.getChangePercent(), is("2,42%"));
        Assert.assertThat(gpwData1.getNumberOfTransaction(), is(223));
        Assert.assertThat(gpwData1.getTurnover(), is(123456989));
        Assert.assertThat(gpwData1.getOpenPrice(), is(new BigDecimal("7999.98")));
        Assert.assertThat(gpwData1.getMaxPrice(), is(new BigDecimal("9010.00")));
        Assert.assertThat(gpwData1.getMinPrice(), is(new BigDecimal("7999.97")));
        Assert.assertThat(gpwData1.getTime(), is(Time.valueOf("11:26:00")));
        Assert.assertThat(gpwData1.getDate(), is(Date.valueOf(actualEnableDate.minus(Period.ofDays(1)))));

        Assert.assertThat(gpwData2.getName(), is("LPP"));
        Assert.assertThat(gpwData2.getExchange(), is(new BigDecimal("8985.00")));
        Assert.assertThat(gpwData2.getChanges(), is(135.0));
        Assert.assertThat(gpwData2.getChangePercent(), is("1,53%"));
        Assert.assertThat(gpwData2.getNumberOfTransaction(), is(174));
        Assert.assertThat(gpwData2.getTurnover(), is(4789480));
        Assert.assertThat(gpwData2.getOpenPrice(), is(new BigDecimal("8910.00")));
        Assert.assertThat(gpwData2.getMaxPrice(), is(new BigDecimal("9000.00")));
        Assert.assertThat(gpwData2.getMinPrice(), is(new BigDecimal("8880.00")));
        Assert.assertThat(gpwData2.getTime(), is(Time.valueOf("11:26:24")));
        Assert.assertThat(gpwData2.getDate(), is(Date.valueOf(actualEnableDate)));
    }

    private boolean isListSortedDesc(ArrayList<GpwDataWithoutPercent> gpwDataWithoutPercents) {
        for(int i = 0; i < gpwDataWithoutPercents.size() -1; i++){
            if(gpwDataWithoutPercents.get(i).getChangePercent() < gpwDataWithoutPercents.get(i +1).getChangePercent())
                return false;
        }
        return true;
    }



}
