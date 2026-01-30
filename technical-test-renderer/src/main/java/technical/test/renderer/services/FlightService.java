package technical.test.renderer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.clients.TechnicalApiClient;
import technical.test.renderer.viewmodels.FlightFilterViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final TechnicalApiClient technicalApiClient;

    public Flux<FlightViewModel> getFlights(FlightFilterViewModel flightFilterViewModel) {
        return this.technicalApiClient.getFlights(flightFilterViewModel);
    }

    public Mono<FlightViewModel> createFlight(FlightViewModel flightViewModel) {
        return this.technicalApiClient.createFlight(flightViewModel);
    }
}
