package gpwdata;

import gpwdata.service.GpwNameService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;


public class GpwNameServiceTest extends SpringBootTestBasedAbstract {
    @Autowired GpwNameService gpwNameService;

    @Test
    public void shouldListSizeIs10() {
        Collection<String> gpwNames = gpwNameService.getAllName();
        Assert.assertEquals(10, gpwNames.size());
    }
}
