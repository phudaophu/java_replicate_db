package vn.tia.vietnamairport.replicate_db.entity.primary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;


@Entity
@Table(name="Flight")
@Data
public class FlightEntity {
    @Id
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
