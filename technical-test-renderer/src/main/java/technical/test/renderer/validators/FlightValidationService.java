package technical.test.renderer.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import technical.test.renderer.facades.AirportFacade;
import technical.test.renderer.viewmodels.FlightViewModel;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FlightValidationService {
    private final AirportFacade airportFacade;

    public Mono<ValidationResult> validate(FlightViewModel flight) {
        Map<String, String> fieldErrors = new HashMap<>();

        validateDates(flight, fieldErrors);

        return validateAirportsExist(flight)
                .map(airportErrors -> {
                    fieldErrors.putAll(airportErrors);
                    return new ValidationResult(fieldErrors);
                });
    }

    private void validateDates(FlightViewModel flight, Map<String, String> errors) {
        LocalDateTime departure = flight.getDeparture();
        LocalDateTime arrival = flight.getArrival();
        LocalDateTime now = LocalDateTime.now();

        if (!departure.isBefore(arrival)) {
            errors.put("arrival", "La date d'arrivée doit être après la date de départ");
        }

        if(departure.isBefore(now)) {
            errors.put("departure", "La date de départ doit être après la date du jour");
        }

        if(arrival.isBefore(now)) {
            errors.put("arrival", "La date d'arrivée doit être après la date du jour");
        }
    }

    private Mono<Map<String, String>> validateAirportsExist(FlightViewModel flight) {
        Mono<Map<String, String>> originValidation = validateAirportExists(
                flight.getOrigin().getIata(),
                "origin.iata",
                "L'aéroport de départ n'existe pas"
        );

        Mono<Map<String, String>> destinationValidation = validateAirportExists(
                flight.getDestination().getIata(),
                "destination.iata",
                "L'aéroport d'arrivée n'existe pas"
        );

        return Mono.zip(originValidation, destinationValidation)
                .map(tuple -> {
                    Map<String, String> errors = new HashMap<>();
                    errors.putAll(tuple.getT1());
                    errors.putAll(tuple.getT2());
                    return errors;
                });
    }

    private Mono<Map<String, String>> validateAirportExists(String iata, String fieldName, String errorMessage) {
        return airportFacade.getAirportByIata(iata)
                .map(airport -> Map.<String, String>of())
                .switchIfEmpty(Mono.just(createErrorMap(fieldName, errorMessage)))
                .onErrorResume(error -> Mono.just(createErrorMap(fieldName, errorMessage)));
    }

    private Map<String, String> createErrorMap(String fieldName, String errorMessage) {
        return Map.of(fieldName, errorMessage);
    }

}