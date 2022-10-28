package softtek.ecommerce.users_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.password.PasswordEncoder;
import softtek.ecommerce.users_service.entities.Role;
import softtek.ecommerce.users_service.entities.User;
import softtek.ecommerce.users_service.repositories.interfaces.RolesRepo;
import softtek.ecommerce.users_service.repositories.interfaces.UsersRepo;

@SpringBootApplication
public class UsersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersServiceApplication.class, args);
	}

	/*@Bean
	CommandLineRunner commandLineRunner(UsersRepo users, PasswordEncoder encoder, RolesRepo rolesRepo ) {
		return args -> {
			users.save(new User("TOMAS@TEST.COM",encoder.encode("1234"), rolesRepo.findByName("Administrador")));

		};
	}*/
}
