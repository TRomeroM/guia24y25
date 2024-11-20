package com.tromero.guia24.service;
import com.tromero.guia24.exception.UserAlreadyExistsException;
import com.tromero.guia24.model.Role;
import com.tromero.guia24.model.User;
import com.tromero.guia24.repository.RoleRepository;
import com.tromero.guia24.repository.UserRepository;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class UserService implements UserDetailsService {

    private static Logger logger = (Logger)

            LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws

            UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.info(() -> "User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user) // Ensure this method correctly returns authorities
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        logger.info(user::toString);
// Implement authority retrieval logic (roles)
        return user.getRoles().stream()

                .map(role -> new SimpleGrantedAuthority("ROLE_" +

                        role.getName()))

                .collect(Collectors.toList());

    }

    // Optional: Method to register a new user
    public void registerUser(User user) {
// Verifica si el usuario ya existe por correo electrónico
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("User with email " +
                    user.getEmail() + " already exists.");
        }
// Codifica la contraseña
        user.setPassword(passwordEncoder().encode(user.getPassword()));

// Define roles para el usuario
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("USER")); // Asegúrate de que esterol exista en la base de datos
        user.setRoles(roles);
// Guarda el usuario en la base de datos
        userRepository.save(user);
    }
}