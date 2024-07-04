package vn.tia.vietnamairport.replicate_db.entity.secondary;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Table(name="Flight")
@Entity
@Data
public class FlightEntity {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightId;

    @Column(name="FlightNo")
    private String flightNo;

    @Column(name="FlightDate")
    private Date flightDate;

    @Column(name = "Route")
    private String route;

    @Column(name = "LinkFlight")
    private String linkFlight;
}
