package kh.edu.istasd.fswdapi.security;

import kh.edu.istasd.fswdapi.domain.User;
import kh.edu.istasd.fswdapi.repository.UserRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        CustomerUserDetails customerUserDetails = new CustomerUserDetails();
        customerUserDetails.setUser(user);
        return customerUserDetails;
    }
}
