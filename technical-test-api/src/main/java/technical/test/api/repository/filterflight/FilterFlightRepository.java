package technical.test.api.repository.filterflight;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import reactor.core.publisher.Flux;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FlightFilterRepresentation;

public interface FilterFlightRepository {
    Flux<FlightRecord> findFlightsWithCriteria(FlightFilterRepresentation flightFilterRepresentation);
}
