package lt.krtrinkunas.twodayinternship.controller;

import lt.krtrinkunas.twodayinternship.controller.dto.EnclosureListDto;
import lt.krtrinkunas.twodayinternship.model.Enclosure;
import lt.krtrinkunas.twodayinternship.service.EnclosureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EnclosureController {
    private final EnclosureService enclosureService;

    @Autowired
    public EnclosureController(EnclosureService enclosureService) {
        this.enclosureService = enclosureService;
    }
    @PostMapping("/enclosures")
    public void loadEnclosures(@RequestBody EnclosureListDto enclosures) {
        System.out.println(enclosures.getEnclosures().get(0).getName());
        enclosureService.createEnclosures(enclosures);
    }
}
