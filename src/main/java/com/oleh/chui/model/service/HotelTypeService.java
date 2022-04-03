package com.oleh.chui.model.service;

import com.oleh.chui.model.entity.HotelType;
import com.oleh.chui.model.repository.HotelTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Manager business logic related with HotelType.
 *
 * @author Oleh Chui
 */
@Service
@RequiredArgsConstructor
public class HotelTypeService {

    private final HotelTypeRepository hotelTypeRepository;

    public HotelType getByValue(HotelType.HotelTypeEnum value) {
        return hotelTypeRepository.findByValue(value).orElseThrow(RuntimeException::new);
    }
}
