package gpwdata;

import gpwdata.service.GpwNameService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;


public class GpwNameServiceTest extends SpringBootTestBasedAbstract {
   @Autowired GpwNameService gpwNameService;

    @Test
    public void shouldNameListIsNotEmpty() {
        Collection<String> gpwNames = gpwNameService.getAllName();

        Assert.assertNotNull(gpwNames);
    }
}
