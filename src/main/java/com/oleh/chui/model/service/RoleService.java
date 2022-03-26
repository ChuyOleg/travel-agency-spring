package com.oleh.chui.model.service;

import com.oleh.chui.model.entity.Role;
import com.oleh.chui.model.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByValue(Role.RoleEnum value) {
        return roleRepository.findByValue(value).orElseThrow(RuntimeException::new);
    }

}
