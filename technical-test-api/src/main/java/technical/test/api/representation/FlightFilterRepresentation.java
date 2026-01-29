package technical.test.api.representation;

import java.util.Optional;

public record FlightFilterRepresentation(
        Optional<Double> minPrice,
        Optional<Double> maxPrice,
        Optional<String> origin,
        Optional<String> destination,
        Optional<String> sortBy,
        Optional<String> sortDirection,
        Optional<Integer> page
) { }
