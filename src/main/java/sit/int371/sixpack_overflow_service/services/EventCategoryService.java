package sit.int371.sixpack_overflow_service.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int371.sixpack_overflow_service.dtos.SimpleEventCategoriesDTO;
import sit.int371.sixpack_overflow_service.entities.EventCategory;
import sit.int371.sixpack_overflow_service.repositories.EventCategoryRepository;

@Service
public class EventCategoryService {
    @Autowired
    private EventCategoryRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;


    public SimpleEventCategoriesDTO getSimpleEventCategoryById(Integer eventCategoryId) {
        EventCategory eventCategory = repository.findById(eventCategoryId).
                orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, eventCategoryId + "Does not exist."));

        return modelMapper.map(eventCategory, SimpleEventCategoriesDTO.class);
    }

//    public EventCategory save(NewEventDTO newEvent) {
//
//        Event event = modelMapper.map(newEvent, Event.class);
//        return repository.saveAndFlush(event);
//    }


}
