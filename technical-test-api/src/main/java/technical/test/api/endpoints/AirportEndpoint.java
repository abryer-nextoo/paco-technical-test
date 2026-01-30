package technical.test.api.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import technical.test.api.facade.AirportFacade;
import technical.test.api.representation.AirportRepresentation;

@RestController
@RequestMapping("/airport")
@RequiredArgsConstructor
public class AirportEndpoint {
    private final AirportFacade airportFacade;

    @CrossOrigin
    @GetMapping("/{name}")
    public Flux<AirportRepresentation> getAirportByNameContaining(@PathVariable String name) {
        return airportFacade.getAirportByNameContaining(name);
    }
}
