package lt.krtrinkunas.twodayinternship.service;

import lt.krtrinkunas.twodayinternship.controller.dto.EnclosureDto;
import lt.krtrinkunas.twodayinternship.controller.dto.EnclosureListDto;
import lt.krtrinkunas.twodayinternship.model.Enclosure;
import lt.krtrinkunas.twodayinternship.model.EnclosureObject;
import lt.krtrinkunas.twodayinternship.repository.EnclosureObjectRepository;
import lt.krtrinkunas.twodayinternship.repository.EnclosureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnclosureService {

    private final EnclosureRepository enclosureRepository;

    private final EnclosureObjectRepository enclosureObjectRepository;

    @Autowired
    public EnclosureService (EnclosureRepository enclosureRepository, EnclosureObjectRepository enclosureObjectRepository) {
        this.enclosureRepository = enclosureRepository;
        this.enclosureObjectRepository = enclosureObjectRepository;
    }
    @Transactional
    public void createEnclosures(EnclosureListDto enclosures) {
        for (EnclosureDto enclosureDto : enclosures.getEnclosures()) {

            Enclosure enclosure = new Enclosure();
            enclosure.setName(enclosureDto.getName());
            enclosure.setSize(enclosureDto.getSize());
            enclosure.setLocation(enclosureDto.getLocation());
            enclosureRepository.save(enclosure);
            for (String enclosureObjectName : enclosureDto.getObjects()) {
                EnclosureObject enclosureObject = new EnclosureObject();
                enclosureObject.setObject(enclosureObjectName);
                enclosureObject.setEnclosure(enclosure);
                enclosureObjectRepository.save(enclosureObject);
            }
        }
    }

    public List<Enclosure> readEnclosures() {
        return enclosureRepository.findAll();
    }
}
