package technical.test.api.repository.filterflight;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FlightFilterRepresentation;

import java.util.regex.Pattern;

@Repository
@RequiredArgsConstructor
public class FilterFlightRepositoryImpl implements FilterFlightRepository {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Value("${pagination.size}")
    private Integer size;

    @Override
    public Flux<FlightRecord> findFlightsWithCriteria(FlightFilterRepresentation flightFilterRepresentation) {
        Query query = new Query();

        flightFilterRepresentation.origin().ifPresent(
                origin -> query.addCriteria(Criteria.where("origin")
                        .regex("^"+ Pattern.quote(origin), "i"))
        );

        flightFilterRepresentation.destination().ifPresent(
                destination -> query.addCriteria(Criteria.where("destination")
                        .regex("^"+ Pattern.quote(destination), "i"))
        );

        if (flightFilterRepresentation.minPrice().isPresent() || flightFilterRepresentation.maxPrice().isPresent()) {
            Criteria priceCriteria = Criteria.where("price");
            flightFilterRepresentation.minPrice().ifPresent(priceCriteria::gte);
            flightFilterRepresentation.maxPrice().ifPresent(priceCriteria::lte);
            query.addCriteria(priceCriteria);
        }

        flightFilterRepresentation.sortBy().ifPresent(sortField -> {
            Sort.Direction direction = flightFilterRepresentation.sortDirection()
                    .filter(dir -> dir.equalsIgnoreCase("DESC"))
                    .map(dir -> Sort.Direction.DESC)
                    .orElse(Sort.Direction.ASC);
            query.with(Sort.by(direction, sortField));
        });

        flightFilterRepresentation.page().ifPresent(
                page -> query.skip((long) page * size)
        );

        query.limit(size);

        return reactiveMongoTemplate.find(query, FlightRecord.class);
    }
}
