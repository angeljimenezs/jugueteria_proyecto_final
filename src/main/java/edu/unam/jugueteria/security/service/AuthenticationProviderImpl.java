package edu.unam.jugueteria.security.service;

import edu.unam.jugueteria.auth.model.Usuario;
import edu.unam.jugueteria.auth.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AuthenticationProviderImpl implements AuthenticationProvider {
    private final UsuarioRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Usuario userAdmin = Optional.ofNullable(userInfoRepository.findByEmail(username))
                .orElseThrow(() -> new BadCredentialsException("User not found in database"));
        if (passwordEncoder.matches(pwd, userAdmin.getContrasena())) {
            List<GrantedAuthority> authorities = userAdmin.getRoles().stream().map(role ->
                    new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
        } else {
            throw new BadCredentialsException("Invalid password!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
