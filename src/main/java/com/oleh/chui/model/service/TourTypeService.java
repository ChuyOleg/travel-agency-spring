package com.oleh.chui.model.service;

import com.oleh.chui.model.entity.TourType;
import com.oleh.chui.model.repository.TourTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class TourTypeService {

    private final TourTypeRepository tourTypeRepository;

    public TourTypeService(TourTypeRepository tourTypeRepository) {
        this.tourTypeRepository = tourTypeRepository;
    }

    public TourType getByValue(TourType.TourTypeEnum value) {
        return tourTypeRepository.findByValue(value).orElseThrow(RuntimeException::new);
    }
}
