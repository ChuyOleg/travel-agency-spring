package com.oleh.chui.model.entity;

import com.oleh.chui.model.dto.UserDto;
import com.oleh.chui.model.entity.constant.ColumnName;
import com.oleh.chui.model.entity.constant.SequenceName;
import com.oleh.chui.model.entity.constant.TableName;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = TableName.USER_TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"username", "password"})
public class User implements UserDetails {

    @Id
    @Column(name = ColumnName.USER_ID)
    @SequenceGenerator(
            name = SequenceName.USER_SEQUENCE_NAME,
            sequenceName = SequenceName.USER_SEQUENCE_NAME,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = SequenceName.USER_SEQUENCE_NAME
    )
    private Long id;

    @Column(name = ColumnName.USERNAME)
    private String username;

    @Column(name = ColumnName.PASSWORD)
    private String password;

    @Column(name = ColumnName.FIRST_NAME)
    private String firstName;

    @Column(name = ColumnName.LAST_NAME)
    private String lastName;

    @Column(name = ColumnName.EMAIL)
    private String email;

    @Column(name = ColumnName.MONEY)
    private BigDecimal money;

    @ManyToOne
    @JoinColumn(name = ColumnName.ROLE_ID)
    private Role role;

    @Column(name = ColumnName.IS_BLOCKED)
    private boolean blocked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getValue().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(UserDto userDto) {
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.email = userDto.getEmail();
        this.money = new BigDecimal(0);
        this.role = new Role(Role.RoleEnum.USER);
    }

}
