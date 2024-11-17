package springboot.chatapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import springboot.chatapp.entity.User;

import java.util.List;

public interface UserService
{
    public boolean addUser(User user);
    public User getUser(int id);
    public List<User> getAllUsers();
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
