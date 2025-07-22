package kh.edu.istasd.fswdapi.security;

import jakarta.annotation.PostConstruct;
import kh.edu.istasd.fswdapi.domain.Role;
import kh.edu.istasd.fswdapi.domain.User;
import kh.edu.istasd.fswdapi.repository.RoleRepository;
import kh.edu.istasd.fswdapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class SecurityInit {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    void init() {
        Role defaultRole = new Role();
        defaultRole.setRole("USER");
        Role admin = new Role();
        admin.setRole("ADMIN");
        Role customer = new Role();
        customer.setRole("CUSTOMER");
        Role staff = new Role();
        staff.setRole("STAFF");
        if (roleRepository.count()==0){
            roleRepository.saveAll(List.of(defaultRole,admin,customer, staff));
        }

        if(userRepository.count()==0){
            User userAdmin = new User();
            userAdmin.setUsername("ADMIN");
            userAdmin.setPassword(passwordEncoder.encode("pwd@123"));
            userAdmin.setIsDeleted(false);
            userAdmin.setIsEnabled(true);
            userAdmin.setRoles(List.of(defaultRole, admin));

            User userStaff = new User();
            userStaff.setUsername("STAFF");
            userStaff.setPassword(passwordEncoder.encode("pwd@123"));
            userStaff.setIsDeleted(false);
            userStaff.setIsEnabled(true);
            userStaff.setRoles(List.of(defaultRole, staff));

            User userCustomer = new User();
            userCustomer.setUsername("CUSTOMER");
            userCustomer.setPassword(passwordEncoder.encode("pwd@123"));
            userCustomer.setIsDeleted(false);
            userCustomer.setIsEnabled(true);
            userCustomer.setRoles(List.of(defaultRole, customer));
            userRepository.saveAll(List.of(userAdmin,userStaff,userCustomer));
        }


    }
}
