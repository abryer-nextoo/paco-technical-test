package technical.test.renderer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import technical.test.renderer.clients.TechnicalApiClient;
import technical.test.renderer.viewmodels.AirportViewModel;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final TechnicalApiClient technicalApiClient;

    public Flux<AirportViewModel> getAirportByNameContaining(String name) {
        return technicalApiClient.getAirportByNameContaining(name);
    }
}
