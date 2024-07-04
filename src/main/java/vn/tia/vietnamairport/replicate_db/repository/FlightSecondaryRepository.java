package vn.tia.vietnamairport.replicate_db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tia.vietnamairport.replicate_db.entity.secondary.FlightEntity;

import java.util.Optional;

//@Repository
public interface FlightSecondaryRepository extends JpaRepository<FlightEntity,Integer> {
     Optional<FlightEntity> getFlightEntityByFlightId(int flightId);
}
