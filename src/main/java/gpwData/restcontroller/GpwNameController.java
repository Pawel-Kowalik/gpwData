package gpwData.restcontroller;

import gpwData.service.GpwNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/")
public class GpwNameController {

    private final GpwNameService gpwNameService;

    @Autowired
    public GpwNameController(GpwNameService gpwNameService) {
        this.gpwNameService = gpwNameService;
    }

    @GetMapping(path = "/allName")
    public Collection<String> getAllGpwName(){
        return gpwNameService.getAllName();
    }
}
