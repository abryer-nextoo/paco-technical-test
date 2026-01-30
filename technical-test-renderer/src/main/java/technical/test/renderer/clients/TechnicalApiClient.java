package technical.test.renderer.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.builder.QueryParamBuilder;
import technical.test.renderer.properties.TechnicalApiProperties;
import technical.test.renderer.viewmodels.FlightFilterViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;

import java.util.UUID;

@Component
@Slf4j
public class TechnicalApiClient {
    private final TechnicalApiProperties technicalApiProperties;
    private final WebClient webClient;

    public TechnicalApiClient(TechnicalApiProperties technicalApiProperties, final WebClient.Builder webClientBuilder) {
        this.technicalApiProperties = technicalApiProperties;
        this.webClient = webClientBuilder
                .baseUrl(technicalApiProperties.getUrl())
                .build();
    }

    public Flux<FlightViewModel> getFlights(FlightFilterViewModel flightFilterViewModel) {
        return webClient
                .get()
                .uri(uriBuilder ->
                        QueryParamBuilder.from(uriBuilder)
                                .path(technicalApiProperties.getFlightPath())
                                .addIfNotNull("minPrice", flightFilterViewModel.getMinPrice())
                                .addIfNotNull("maxPrice", flightFilterViewModel.getMaxPrice())
                                .addIfNotBlank("origin", flightFilterViewModel.getOrigin())
                                .addIfNotBlank("destination", flightFilterViewModel.getDestination())
                                .addIfNotBlank("sortBy", flightFilterViewModel.getSortBy())
                                .addIfNotBlank("sortDirection", flightFilterViewModel.getSortDirection())
                                .addIfNotNull("page", flightFilterViewModel.getPage())
                                .build()
                )
                .retrieve()
                .bodyToFlux(FlightViewModel.class);
    }

    public Mono<FlightViewModel> getFlightById(UUID id) {
        return webClient
                .get()
                .uri(technicalApiProperties.getFlightPath()+'/'+id)
                .retrieve()
                .bodyToMono(FlightViewModel.class);
    }

    public Mono<FlightViewModel> createFlight(FlightViewModel flightViewModel) {
        return webClient
                .post()
                .uri(technicalApiProperties.getFlightPath())
                .body(Mono.just(flightViewModel), FlightViewModel.class)
                .retrieve()
                .bodyToMono(FlightViewModel.class);
    }
}
