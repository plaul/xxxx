package kea.sem3.jwtdemo.configuration;

import kea.sem3.jwtdemo.entity.*;
import kea.sem3.jwtdemo.repositories.CarRepository;
import kea.sem3.jwtdemo.repositories.MemberRepository;
import kea.sem3.jwtdemo.repositories.ReservationRepository;
import kea.sem3.jwtdemo.security.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.Month;

@Controller
@Profile("!test")
public class MakeTestData implements ApplicationRunner {


    UserRepository userRepository;
    MemberRepository memberRepository;
    CarRepository carRepository;
    ReservationRepository reservationRepository;

    public MakeTestData(UserRepository userRepository, MemberRepository memberRepository, CarRepository carRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    public  void makePlainUsers(){
        BaseUser user = new BaseUser("user", "user@a.dk", "test12");
        user.addRole(Role.USER);
        BaseUser admin = new BaseUser("admin", "admin@a.dk", "test12");
        admin.addRole(Role.ADMIN);
        BaseUser both = new BaseUser("user_admin", "both@a.dk", "test12");
        both.addRole(Role.USER);
        both.addRole(Role.ADMIN);

        userRepository.save(user);
        userRepository.save(admin);
        userRepository.save(both);


        Member m1 = new Member("kw","kw@a.dk","test12","Kurt","Wonnegut","Lyngbyvje 34","Lyngby","2800");
        m1.addRole(Role.USER);
        Member m2 = new Member("hw","hw@a.dk","test12","Hanne","Wonnegut","Lyngbyvje 34","Lyngby","2800");
        m2.addRole(Role.USER);
        memberRepository.save(m1);
        memberRepository.save(m2);

        Car carVolvo = carRepository.save(new Car("Volvo", "C40", 560,10));
        carRepository.save(new Car("Volvo", "V70", 500,10));
        carRepository.save(new Car("Volvo", "V49", 400,10));
        carRepository.save(new Car("Suzuki", "Vitara", 500,14));
        carRepository.save(new Car("Suzuki", "Vitara", 500,14));
        carRepository.save(new Car("Suzuki", "S-Cross", 500,14));

        //Create a Reservation
        Reservation res1 = new Reservation(LocalDate.of(2022,3,1),carVolvo,m1);
        reservationRepository.save(res1);
        Reservation res = reservationRepository.
                findReservationByReservedCar_IdAndRentalDate(carVolvo.getId(),(LocalDate.of(2022,3,1)));
        if(res == null) {

            Reservation res2 = new Reservation(LocalDate.of(2022, 3, 1), carVolvo, m2);
            reservationRepository.save(res2);
        } else{
            System.out.println("Car is reserved this day");
        }




        System.out.println(carVolvo.getReservations().size());

        System.out.println("########################################################################################");
        System.out.println("########################################################################################");
        System.out.println("#################################### WARNING ! #########################################");
        System.out.println("## This part breaks a fundamental security rule -> NEVER ship code with default users ##");
        System.out.println("########################################################################################");
        System.out.println("########################  REMOVE BEFORE DEPLOYMENT  ####################################");
        System.out.println("########################################################################################");
        System.out.println("########################################################################################");
        System.out.println("Created TEST Users");

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        userRepository.deleteAll();

        makePlainUsers();


    }
}
