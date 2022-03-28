package com.oleh.chui.model.service;

import com.oleh.chui.model.dto.UserDto;
import com.oleh.chui.model.entity.Role;
import com.oleh.chui.model.entity.User;
import com.oleh.chui.model.exception.user.UsernameIsReservedException;
import com.oleh.chui.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        return userOptional.orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByRoleValue(Role.RoleEnum.USER);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void registerNewAccount(UserDto userDto) throws UsernameIsReservedException {
        checkUsernameIsUnique(userDto.getUsername());

        User user = new User(userDto);
        user.setRole(roleService.findByValue(user.getRole().getValue()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public void blockById(Long id) {
        User user = userRepository.getById(id);
        user.setBlocked(true);
        userRepository.save(user);
    }

    public void unblockById(Long id) {
        User user = userRepository.getById(id);
        user.setBlocked(false);
        userRepository.save(user);
    }

    private void checkUsernameIsUnique(String username)throws UsernameIsReservedException {
        if (userRepository.existsByUsername(username)) throw new UsernameIsReservedException();
    }

}
