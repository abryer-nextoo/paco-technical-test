package technical.test.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import technical.test.api.mapper.AirportMapper;
import technical.test.api.representation.AirportRepresentation;
import technical.test.api.services.AirportService;

@Component
@RequiredArgsConstructor
public class AirportFacade {
    private final AirportService airportService;
    private final AirportMapper airportMapper;

    public Flux<AirportRepresentation> getAirportByNameContaining(String name) {
        return airportService.getAirportByNameContaining(name)
                .map(airportMapper::convert);
    }
}
