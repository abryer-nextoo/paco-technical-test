package technical.test.renderer.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.services.AirportService;
import technical.test.renderer.viewmodels.AirportViewModel;

@Component
@RequiredArgsConstructor
public class AirportFacade {
    private final AirportService airportService;

    public Flux<AirportViewModel> getAirportByNameContaining(String name) {
        return airportService.getAirportByNameContaining(name);
    }

    public Mono<AirportViewModel> getAirportByIata(String iata) {
        return airportService.getAirportByIata(iata);
    }
}
