package technical.test.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.mapper.AirportMapper;
import technical.test.api.mapper.FlightMapper;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.services.AirportService;
import technical.test.api.services.FlightService;

@Component
@RequiredArgsConstructor
public class FlightFacade {
    private final FlightService flightService;
    private final AirportService airportService;
    private final FlightMapper flightMapper;
    private final AirportMapper airportMapper;

    public Flux<FlightRepresentation> getAllFlights() {
        return flightService.getAllFlights()
                .flatMap(this::toRepresentation);
    }

    public Mono<FlightRepresentation> createFlight(FlightRepresentation flightRepresentation) {
        FlightRecord flightRecord = flightMapper.convert(flightRepresentation);
        return flightService.createFlight(flightRecord)
                .flatMap(this::toRepresentation);
    }

    public Mono<FlightRepresentation> toRepresentation(FlightRecord flightRecord) {
        return Mono.zip(
                airportService.findByIataCode(flightRecord.getOrigin()),
                airportService.findByIataCode((flightRecord.getDestination()))
        ).map(tuple -> {
            FlightRepresentation flight = flightMapper.convert(flightRecord);
            flight.setOrigin(airportMapper.convert(tuple.getT1()));
            flight.setDestination(airportMapper.convert(tuple.getT2()));
            return flight;
        });
    }
}
