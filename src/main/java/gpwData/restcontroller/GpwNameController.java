package gpwData.restcontroller;

import gpwData.model.GpwName;
import gpwData.service.GpwNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class GpwNameController {

    @Autowired
    GpwNameService gpwNameService;

    @GetMapping(path = "/allName")
    public Iterable<GpwName> getAllGpwName(){
        return gpwNameService.findAllGpwData();
    }
}
