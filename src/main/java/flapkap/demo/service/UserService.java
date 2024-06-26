package flapkap.demo.service;

import flapkap.demo.model.User;
import flapkap.demo.repository.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }

    public Optional<User> getUser(Long id){
        return userRepo.findById(id);
    }

    public void deleteUser(Long id){
        userRepo.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public void resetUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) this.loadUserByUsername(authentication.getName());
        user.setDeposit(0);
        userRepo.save(user);
    }
}
