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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class GpwDataServiceTest extends SpringBootTestBasedAbstract {
    private static final String COMPANY_NAME = "ATMGRUPA";
    @Autowired GpwDataService gpwDataService;
    @Autowired ActualDateService actualDateService;

    private ArrayList<GpwDataWithoutPercent> highestGpwDataWithoutPercents = new ArrayList<>();
    private ArrayList<GpwDataWithoutPercent> lowestGpwDataWithoutPercents = new ArrayList<>();
    private Collection<GpwData> historyGpwData = new ArrayList<>();
    private LocalDate actualEnableDate;

    @Before
    public void fillData() {
        highestGpwDataWithoutPercents.addAll(gpwDataService.getHighestCompaniesDataOfDay());
        lowestGpwDataWithoutPercents.addAll(gpwDataService.getLowestCompaniesDataOfDay());
        historyGpwData.addAll(gpwDataService.getHistoryCompanyExchange(COMPANY_NAME, 30));
        actualEnableDate = actualDateService.getActualDate();
    }

    @Test
    public void shouldGpwDataIsNotNull() {
        Assert.assertNotNull(gpwDataService.getActualDataByName(COMPANY_NAME));
    }

    @Test
    public void shouldGpwDataIsActual() {
        Date gpwDate = gpwDataService.getActualDataByName(COMPANY_NAME).getDate();
        Assert.assertEquals(gpwDate, Date.valueOf(actualEnableDate));
    }

    @Test
    public void  shouldActualGpwDataExchangeIsNotNull() {
        BigDecimal actualExchange = gpwDataService.getActualExchangeByName(COMPANY_NAME);
        Assert.assertNotNull(actualExchange);
    }

    @Test
    public void shouldHighestGpwDataListIsNotNull() {
        Assert.assertNotNull(highestGpwDataWithoutPercents);
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
    public void shouldLowestGpwDataListIsNotNull() {
        Assert.assertNotNull(lowestGpwDataWithoutPercents);
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
    public void shouldHistoryGpwDataListIsNotNull() {
        Assert.assertNotNull(historyGpwData);
    }

    private boolean isListSortedDesc(ArrayList<GpwDataWithoutPercent> gpwDataWithoutPercents) {
        for(int i = 0; i < gpwDataWithoutPercents.size() -1; i++){
            if(gpwDataWithoutPercents.get(i).getChangePercent() < gpwDataWithoutPercents.get(i +1).getChangePercent())
                return false;
        }
        return true;
    }



}
