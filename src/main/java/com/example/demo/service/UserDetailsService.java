//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import com.example.demo.repository.UserRepository;
//
//@Bean
//public UserDetailsService userDetailsService() {
//    return username -> {
//        User user = UserRepository.findByUserName(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        return org.springframework.security.core.userdetails.User
//                .withUsername(user.getUsername())
//                .password(user.getPassword())
//                .roles(user.getRoles().toArray(new String[0]))
//                .build();
//    };
//}
