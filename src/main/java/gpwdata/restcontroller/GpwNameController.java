package gpwdata.restcontroller;

import gpwdata.service.GpwNameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/gpw-name")
public class GpwNameController {

    private final GpwNameService gpwNameService;

    @GetMapping(path = "/all")
    public Collection<String> getAllGpwName(){
        return gpwNameService.getAllName();
    }
}
