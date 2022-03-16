package kea.sem3.jwtdemo.repositories;

import kea.sem3.jwtdemo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    Reservation findReservationByReservedCar_IdAndRentalDate(int id, LocalDate date);



}