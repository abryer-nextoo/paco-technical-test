package technical.test.api.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.facade.FlightFacade;
import technical.test.api.representation.FlightFilterRepresentation;
import technical.test.api.representation.FlightRepresentation;

import java.util.UUID;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightEndpoint {
    private final FlightFacade flightFacade;

    @GetMapping
    public Flux<FlightRepresentation> getAllFlights(FlightFilterRepresentation flightFilterRepresentation) {
        return flightFacade.getAllFlights(flightFilterRepresentation);
    }

    @GetMapping("/{id}")
    public Mono<FlightRepresentation> getFlightById(@PathVariable UUID id) {
        return flightFacade.getFlightById(id);
    }

    @PostMapping
    public Mono<FlightRepresentation> createFlight(@RequestBody FlightRepresentation flightRepresentation) {
        return flightFacade.createFlight(flightRepresentation);
    }
}
