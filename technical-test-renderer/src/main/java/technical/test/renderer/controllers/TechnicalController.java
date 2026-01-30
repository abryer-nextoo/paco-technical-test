package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.facades.AirportFacade;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.AirportViewModel;
import technical.test.renderer.viewmodels.FlightFilterViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;
import technical.test.renderer.viewmodels.PageInfoViewModel;

import java.util.UUID;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {
    private final FlightFacade flightFacade;
    private final AirportFacade airportFacade;

    @Value("${pagination.size}")
    private Integer size;

    @GetMapping
    public Mono<String> getMarketPlaceReturnCouponPage(
            final Model model,
            @ModelAttribute FlightFilterViewModel flightFilterViewModel
    ) {
        return flightFacade.getFlights(flightFilterViewModel)
                .collectList()
                .map(flights -> {
                    int currentPage = flightFilterViewModel.getPage() != null ? flightFilterViewModel.getPage() : 0;
                    boolean hasNext = flights.size() == size;

                    PageInfoViewModel pageInfo = new PageInfoViewModel(
                            currentPage,
                            -1,
                            flights.size(),
                            hasNext,
                            currentPage > 0
                    );

                    model.addAttribute("flights", flights);
                    model.addAttribute("filters", flightFilterViewModel);
                    model.addAttribute("pageInfo", pageInfo);
                    return "pages/index";
                });
    }

    @GetMapping("/createFlight")
    public Mono<String> getCreateFlightPage(final Model model) {
        model.addAttribute("flight", new FlightViewModel());
        return Mono.just("pages/createFlight");
    }

    @GetMapping("/{id}")
    public Mono<String> getFlightById(
            final Model model,
            @PathVariable UUID id,
            @ModelAttribute FlightFilterViewModel flightFilterViewModel
    ) {
        Mono<FlightViewModel> flight = flightFacade.getFlightById(id);
        model.addAttribute("flight", flight);
        model.addAttribute("filters", flightFilterViewModel);
        return Mono.just("pages/flight");
    }

    @PostMapping
    public Mono<String> createFlight(
            final Model model,
            @ModelAttribute FlightViewModel flightViewModel
    ) {
        Mono<FlightViewModel> createFlight = flightFacade.createFlight(flightViewModel);
        model.addAttribute("flight", createFlight);
        return Mono.just("redirect:/");
    }

    @GetMapping("/airport/{name}")
    @ResponseBody
    public Flux<AirportViewModel> getAirportByNameContaining(@PathVariable String name) {
        return airportFacade.getAirportByNameContaining(name);
    }
}
