package com.oleh.chui.model.service;

import com.oleh.chui.model.entity.HotelType;
import com.oleh.chui.model.repository.HotelTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelTypeService {

    private final HotelTypeRepository hotelTypeRepository;

    public HotelTypeService(HotelTypeRepository hotelTypeRepository) {
        this.hotelTypeRepository = hotelTypeRepository;
    }

    public HotelType getByValue(HotelType.HotelTypeEnum value) {
        return hotelTypeRepository.findByValue(value).orElseThrow(RuntimeException::new);
    }
}
