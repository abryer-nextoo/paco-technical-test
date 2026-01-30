package technical.test.api.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.record.AirportRecord;
import technical.test.api.repository.AirportRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirportService {
    private final AirportRepository airportRepository;

    public Mono<AirportRecord> findByIataCode(final String iataCode) {
        log.debug("finding by iata code: {}", iataCode);
        return airportRepository.findAirportRecordByIata(iataCode);
    }

    public Flux<AirportRecord> getAirportByNameContaining(String name) {
        return airportRepository.findAirportRecordByNameContainingIgnoreCase(name);
    }
}
