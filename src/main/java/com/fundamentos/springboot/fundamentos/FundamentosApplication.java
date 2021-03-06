package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.bean.MyOperation;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;
import com.fundamentos.springboot.fundamentos.entitiy.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;


	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean
	, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo,
								  UserRepository userRepository, UserService userService){
		this.componentDependency=componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository=userRepository;
		this.userService=userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//ejemplosAnteriores();
		saveUsersInDataBase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();


	}

	private void saveWithErrorTransactional(){
		User test1 = new User("TestTransactional1", "TestTransactional1@domain.com", LocalDate.now());
		User test2 = new User("TestTransactional2", "TestTransactional2@domain.com", LocalDate.now());
		User test3 = new User("TestTransactional3", "TestTransactional1@domain.com", LocalDate.now());
		User test4 = new User("TestTransactional4", "TestTransactional4@domain.com", LocalDate.now());

		List<User> users = Arrays.asList(test1, test2, test3, test4);
		try {
			userService.saveTransactional(users);
		}catch (Exception e){
			LOGGER.error("Esta es una exception dentro del metodo transaccional "+ e );
		}



		userService.getAllUsers().stream().forEach(user -> LOGGER.info("Este esel usuario dentro delmetodo transactional: " + user));
	}

	private void getInformationJpqlFromUser(){
		/*LOGGER.info("Usuario con el metodo findByUserEmail" + userRepository.findByUserEmail("julie@domian.com")
				.orElseThrow(()-> new RuntimeException("No se encontre el usuario")));
		userRepository.findAndSort("user", Sort.by("id").descending())
		.stream()
		.forEach(user -> LOGGER.info("User with methods:" + user));

		userRepository.findByName("John").stream()
		.forEach(name -> LOGGER.info("Usuario con query method " + name));

		userRepository.findByNameLike("%u%").stream().forEach(user -> LOGGER.info(user + "fue encontrado con nquery methods"));

		userRepository.findByNameOrEmail(null, "user10@domian.com").stream().forEach(user -> LOGGER.info(user + "fue encontrado con algo diferente"));*/

		userRepository.findByBirthDateBetween(LocalDate.of(2021, 3 ,1), LocalDate.of(2021, 4, 2))
				.stream()
				.forEach(user -> LOGGER.info("Usuario con intervalo de fechas "+ user));

		/*LOGGER.info("El usuario a partir del named parameter es: " + userRepository.getAllByBirthDateAndEmail(LocalDate.of(2021, 07, 21), "daniel@domian.com")
				.orElseThrow(() -> new RuntimeException("No se encontro el usuario a partir del named parameter")));*/

		/*LOGGER.info("El usuario a partir del named parameter es: " + userRepository.getAllByBirthDateAndEmail(LocalDate.of(2021, 07, 21), "daniel@domian.com")
				.orElseThrow(() ->new RuntimeException("No se encontro el usuario a partir del named parameter")));*/

		/*LOGGER.info("comentario" + userRepository.getAllByBirthDateAndEmail(LocalDate.of(2021, 07, 21), "daniel@domian.com").orElseThrow(() -> new RuntimeException("juas")));*/

		LOGGER.info("The user from the named parameter is: " + userRepository.getAllByBirthDateAndEmail(LocalDate.of(2021, 07, 21), "daniela@domian.com")
				.orElseThrow(() ->new RuntimeException("Didnt find thr user from the named parameter")));

	}

	private void saveUsersInDataBase(){
		User user1 = new User("John", "john@domian.com", LocalDate.of(2021, 03, 20));
		User user2 = new User("Julie", "julie@domian.com", LocalDate.of(2021, 05, 21));
		User user3 = new User("Daniela", "daniela@domian.com", LocalDate.of(2021, 07, 21));
		User user4 = new User("user4", "user4@domian.com", LocalDate.of(2021, 07, 7));
		User user5 = new User("user5", "user5@domian.com", LocalDate.of(2021, 11, 11));
		User user6 = new User("user6", "user6@domian.com", LocalDate.of(2021, 2, 25));
		User user7 = new User("user7", "user7@domian.com", LocalDate.of(2021, 3, 11));
		User user8 = new User("user8", "user8@domian.com", LocalDate.of(2021, 4, 12));
		User user9 = new User("user9", "user9@domian.com", LocalDate.of(2021, 5, 22));
		User user10 = new User("user10", "user10@domian.com", LocalDate.of(2021, 8, 3));
		User user11 = new User("user11", "user11@domian.com", LocalDate.of(2021, 1, 12));
		User user12 = new User("user12", "user12@domian.com", LocalDate.of(2021, 2, 2));
		List<User> list = Arrays.asList(user1, user2, user3,user4,user5,user6,user7,user8,user9, user10, user11, user12);
		list.stream().forEach(userRepository::save);
	}

	private void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		//System.out.println(myBeanWithProperties.function());
		System.out.println("Y su email es: " + userPojo.getEmail());
		try{
			int value = 10/0;
			LOGGER.debug("Mivalor = " + value);
		}catch (Exception e){
			LOGGER.error("Esto es un error al dividir por cero" + e.getMessage());
		}
	}
}
