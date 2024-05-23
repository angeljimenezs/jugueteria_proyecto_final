package edu.unam.jugueteria.security.model;

import edu.unam.jugueteria.auth.model.Usuario;
import edu.unam.jugueteria.auth.model.Rol;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private Integer id;
    private String name;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private Usuario userInfo;

    public UserDetailsImpl(Usuario userInfo) {
        this.userInfo = userInfo;
    }

    public UserDetailsImpl(Integer id, String name, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Usuario user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getNombre())
        ).collect(Collectors.toList());
        return new UserDetailsImpl(
                user.getIdUsuario(),
                user.getFullName(),
                user.getEmail(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (null == userInfo.getRoles()) {
            return Collections.emptySet();
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Rol role : userInfo.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getNombre()));
        }
        return grantedAuthorities;
    }

    /**
     * getUsername
     * @return username
     */
    @Override
    public String getUsername() {
        return userInfo.getEmail();
    }

    /**
     * getPassword (OTP)
     * @return password
     */
    @Override
    public String getPassword() {
        return userInfo.getContrasena();
    }

    /**
     * getName
     * @return name
     */
    public String getName() {
        return userInfo.getNombre();
    }

    /**
     * getEmail
     * @return email
     */
    public String getEmail() {
        return userInfo.getEmail();
    }

    /**
     * isEnabled
     * @return if user is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * isAccountNonLocked
     * @return if user is locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * isAccountNonExpired
     * @return if account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * isCredentialsNonExpired
     * @return if credential is not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
