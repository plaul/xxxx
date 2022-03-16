package kea.sem3.jwtdemo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.sem3.jwtdemo.TestUtils;
import kea.sem3.jwtdemo.dto.CarRequest;
import kea.sem3.jwtdemo.dto.CarResponse;
import kea.sem3.jwtdemo.entity.BaseUser;
import kea.sem3.jwtdemo.entity.Car;
import kea.sem3.jwtdemo.entity.Role;
import kea.sem3.jwtdemo.repositories.CarRepository;
import kea.sem3.jwtdemo.security.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CarControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    static int carFordId, carSuzukiId;

    BaseUser admin;
    @BeforeEach
    public void setup() throws Exception {
        carRepository.deleteAll();
        carFordId = carRepository.save(new Car("Ford", "Focus", 400, 10)).getId();
        carSuzukiId = carRepository.save(new Car("Suzuki", "Vitara", 500, 14)).getId();

        //Create user(s) needed to login to get a token for protected endpoints
        userRepository.deleteAll();
        admin = new BaseUser("xxx-user","a@b.dk","test12");
        admin.addRole(Role.ADMIN);
        userRepository.save(admin);
    }

    @Test
    void getCars() {
    }

    @Test
    public void testCarById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/cars/" + carFordId)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(carFordId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Focus"));

    }


    @Test
    public void testAllCars() throws Exception {
        String model = "$[?(@.model == '%s')]";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/cars")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                //One way of testing this
                .andExpect(MockMvcResultMatchers.jsonPath(model, "Focus").exists())
                .andExpect(MockMvcResultMatchers.jsonPath(model, "Vitara").exists())
                //Another way
                .andExpect(MockMvcResultMatchers.content().string(containsString("Focus")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("Vitara")));

    }

    @Test
    public void testAddCar() throws Exception {
        CarRequest newCar = new CarRequest("WW", "Polo", 200, 10);
        //Login and get the token
        String adminToken = TestUtils.login("xxx-user","test12",mockMvc);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cars")
                        .contentType("application/json")
                        .accept("application/json")
                        .header("Authorization","Bearer "+adminToken) // //Add token to the request
                        .content(objectMapper.writeValueAsString(newCar)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        //Verify that it actually ended in the database
        assertEquals(3, carRepository.count());

    }

    // @Test
    public void editCar() throws Exception {}

    @Test
    void deleteCar() throws Exception {}
}
