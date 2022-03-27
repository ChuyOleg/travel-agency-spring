package com.oleh.chui.model.service;

import com.oleh.chui.model.entity.Status;
import com.oleh.chui.model.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    public Status getByValue(Status.StatusEnum value) {
        return statusRepository.findByValue(value).orElseThrow(RuntimeException::new);
    }

}
