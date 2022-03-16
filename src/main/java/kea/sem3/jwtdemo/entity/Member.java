package kea.sem3.jwtdemo.entity;

import kea.sem3.jwtdemo.dto.MemberRequest;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Member extends BaseUser {

    //Make sure you understand why there is no @Id annotation in this class, and the SINGLE table created in the database

    @Column(length = 40)
    String firstName;

    @Column(length = 60)
    String lastName;

    @Column(length = 60)
    String street;

    @Column(length = 30)
    String city;

    @Column(length = 4)
    String zip;

    @CreationTimestamp
    LocalDateTime created;

    @UpdateTimestamp
    LocalDateTime edited;

    boolean isApproved;

    //Number between 0 and 10, ranking the customer
    byte ranking;

    @OneToMany(mappedBy = "reservedTo")
    private Set<Reservation> reservations = new HashSet<>();

    public void addReservation(Reservation res){
        reservations.add(res);
    }

    public Member(String username, String email, String password, String firstName, String lastName, String street, String city, String zip) {
        super(username, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.zip = zip;
        ranking = 5; //Initial ranking
        isApproved = false;
    }

    public Member(MemberRequest body) {
        //Just call the constructor above
        this(body.getUsername(),body.getEmail(),body.getPassword(),body.getFirstName(),body.getLastName(),body.getStreet(),body.getCity(),body.getZip());
    }

    public Member() {}

}
