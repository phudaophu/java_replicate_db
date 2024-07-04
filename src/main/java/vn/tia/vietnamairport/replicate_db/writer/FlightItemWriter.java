package vn.tia.vietnamairport.replicate_db.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import vn.tia.vietnamairport.replicate_db.entity.secondary.FlightEntity;

@Component
public class FlightItemWriter implements ItemWriter<FlightEntity> {
    @Override
    public void write(Chunk<? extends FlightEntity> chunk) throws Exception {
        System.out.println("In Postgres Flight Item Writer: ");
        chunk.forEach(flightSecondary -> {
            System.out.println("flightSecondaryEntity "+flightSecondary.toString());
        });
    }
}
