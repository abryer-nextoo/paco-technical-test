package technical.test.renderer.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.services.FlightService;
import technical.test.renderer.viewmodels.FlightFilterViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;

@Component
@RequiredArgsConstructor
public class FlightFacade {
    private final FlightService flightService;

    public Flux<FlightViewModel> getFlights(FlightFilterViewModel flightFilterViewModel) {
        return this.flightService.getFlights(flightFilterViewModel);
    }

    public Mono<FlightViewModel> createFlight(FlightViewModel flightViewModel) {
        return this.flightService.createFlight(flightViewModel);
    }
}
