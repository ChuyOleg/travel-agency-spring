package com.oleh.chui.model.service;

import com.oleh.chui.model.entity.TourType;
import com.oleh.chui.model.repository.TourTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Manager business logic related with TourType.
 *
 * @author Oleh Chui
 */
@Service
@RequiredArgsConstructor
public class TourTypeService {

    private final TourTypeRepository tourTypeRepository;

    public TourType getByValue(TourType.TourTypeEnum value) {
        return tourTypeRepository.findByValue(value).orElseThrow(RuntimeException::new);
    }
}
