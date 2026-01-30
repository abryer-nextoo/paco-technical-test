package technical.test.renderer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.clients.TechnicalApiClient;
import technical.test.renderer.viewmodels.FlightFilterViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final TechnicalApiClient technicalApiClient;

    public Flux<FlightViewModel> getFlights(FlightFilterViewModel flightFilterViewModel) {
        return technicalApiClient.getFlights(flightFilterViewModel);
    }

    public Mono<FlightViewModel> getFlightById(UUID id) {
        return technicalApiClient.getFlightById(id);
    }

    public Mono<FlightViewModel> createFlight(FlightViewModel flightViewModel) {
        return technicalApiClient.createFlight(flightViewModel);
    }
}
