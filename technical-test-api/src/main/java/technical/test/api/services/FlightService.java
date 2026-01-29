package technical.test.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.record.FlightRecord;
import technical.test.api.repository.FlightRepository;
import technical.test.api.representation.FlightFilterRepresentation;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public Flux<FlightRecord> getAllFlights(FlightFilterRepresentation flightFilterRepresentation) {
        return flightRepository.findFlightsWithCriteria(flightFilterRepresentation);
    }

    public Mono<FlightRecord> createFlight(FlightRecord flightRecord) {
        flightRecord.setId(UUID.randomUUID());
        return flightRepository.insert(flightRecord);
    }
}
