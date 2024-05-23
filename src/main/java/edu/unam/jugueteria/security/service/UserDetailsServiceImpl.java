package edu.unam.jugueteria.security.service;

import edu.unam.jugueteria.auth.model.Usuario;
import edu.unam.jugueteria.auth.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioRepository userInfoRepository;

    @Autowired
    public UserDetailsServiceImpl(UsuarioRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Security - UserDetailsServiceImpl.loadUserByUsername {}", username);
        Usuario userInfo = Optional.ofNullable(userInfoRepository.findByEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database"));
        String userName = userInfo.getEmail();
        String password = userInfo.getContrasena();
        List<GrantedAuthority> authorities = userInfo.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
        return new User(userName, password, authorities);
    }
}
