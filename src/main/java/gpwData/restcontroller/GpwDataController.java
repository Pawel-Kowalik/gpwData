package gpwData.restcontroller;

import gpwData.model.GpwData;
import gpwData.service.GpwDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class GpwDataController {

    @Autowired
    GpwDataService gpwDataService;

    @GetMapping(path = "/allData")
    public Iterable<GpwData> getAllGpwData(){
        return gpwDataService.findAllGpwData();
    }
}
