package vn.tia.vietnamairport.replicate_db.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tia.vietnamairport.replicate_db.entity.secondary.FlightEntity;
import vn.tia.vietnamairport.replicate_db.repository.FlightSecondaryRepository;
//import vn.tia.vietnamairport.replicate_db.service.imp.FlightSecondaryServiceImp;

import java.util.Optional;

@Service
public class FlightSecondaryService  {

    @Autowired
    private FlightSecondaryRepository flightSecondaryRepository;
    @PersistenceContext(unitName = "secondaryEntityManagerFactory")
    private EntityManager secondaryEntityManager;

    public FlightEntity getFlightSecondaryByFlightId(int id) {
        Optional<FlightEntity> flightOptional = flightSecondaryRepository.getFlightEntityByFlightId(id);
        return flightOptional.orElse(null);
    }

    @Transactional("secondaryTransactionManager")
    public void saveFlightSecondary(FlightEntity flight) {
        // cannot affect
 //       secondaryEntityManager.createNativeQuery("SET IDENTITY_INSERT Flight ON").executeUpdate();
 //       flightSecondaryRepository.save(flight);
        secondaryEntityManager.createNativeQuery("SET IDENTITY_INSERT Flight ON ; INSERT INTO Flight (FlightId, FlightDate, FlightNo, LinkFlight, Route) VALUES (?, ?, ?, ?, ?); SET IDENTITY_INSERT Flight OFF ")
                .setParameter(1, flight.getFlightId())
                .setParameter(2, flight.getFlightDate())
                .setParameter(3, flight.getFlightNo())
                .setParameter(4, flight.getLinkFlight())
                .setParameter(5, flight.getRoute())
                .executeUpdate();
        //secondaryEntityManager.createNativeQuery("SET IDENTITY_INSERT Flight OFF").executeUpdate();
    }
}
