package vn.tia.vietnamairport.replicate_db.processor;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import vn.tia.vietnamairport.replicate_db.entity.primary.FlightEntity;
import vn.tia.vietnamairport.replicate_db.helper.GeneralHelper;
import vn.tia.vietnamairport.replicate_db.service.FlightSecondaryService;
//import vn.tia.vietnamairport.replicate_db.service.imp.FlightSecondaryServiceImp;

@Component
public class FlightProcessor implements ItemProcessor<FlightEntity, vn.tia.vietnamairport.replicate_db.entity.secondary.FlightEntity> {
//    @Autowired
//    private FlightSecondaryServiceImp flightSecondaryServiceImp;

    @Autowired
    private FlightSecondaryService flightSecondaryService;



    private final GeneralHelper generalHelper = new GeneralHelper();

    @Override
    public vn.tia.vietnamairport.replicate_db.entity.secondary.FlightEntity process(FlightEntity flightPrimary) throws Exception {
        vn.tia.vietnamairport.replicate_db.entity.secondary.FlightEntity flightSecondary;
        flightSecondary = flightSecondaryService.getFlightSecondaryByFlightId(flightPrimary.getFlightId());
        if(flightSecondary==null){
            // create new flight secondary
            vn.tia.vietnamairport.replicate_db.entity.secondary.FlightEntity newFlightSecondary ;
            newFlightSecondary  = new vn.tia.vietnamairport.replicate_db.entity.secondary.FlightEntity();
            newFlightSecondary.setFlightId(flightPrimary.getFlightId());
            newFlightSecondary.setFlightNo(flightPrimary.getFlightNo());
            newFlightSecondary.setRoute(flightPrimary.getRoute());
            newFlightSecondary.setFlightDate(flightPrimary.getFlightDate());
            newFlightSecondary.setLinkFlight(flightPrimary.getLinkFlight());

            flightSecondaryService.saveFlightSecondary(newFlightSecondary);

            System.out.println("Create Flight Id = "+newFlightSecondary.getFlightId());
            return newFlightSecondary;
        }else{
            // Check flightPrimary and flightSecondary is changed?
            if(generalHelper.flightEntityIsChanged(flightPrimary,flightSecondary)){
                // update flight
                flightSecondary.setFlightNo(flightPrimary.getFlightNo());
                flightSecondary.setRoute(flightPrimary.getRoute());
                flightSecondary.setFlightDate(flightPrimary.getFlightDate());
                flightSecondary.setLinkFlight(flightPrimary.getLinkFlight());
                flightSecondaryService.saveFlightSecondary(flightSecondary);
                System.out.println("Update Flight Id = "+flightSecondary.getFlightId());
            }
            return flightSecondary;
        }
    }




}
