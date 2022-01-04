package com.jeanbarcellos.processmanager.services;

import com.jeanbarcellos.processmanager.domain.entities.User;
import com.jeanbarcellos.processmanager.domain.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Serviço para obtenção do usuário pelo username (email)
 * Utiliada somente pelo Security
 */
@Service
public class AuthenticationUserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationUserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            logger.error("Usuário não encontrado: " + email);
            throw new UsernameNotFoundException("Usuário não enconrado");
        }

        logger.info("Usuário encontrado: " + email);

        return user;
    }

}
