package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.FlightFilterViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {
    private final FlightFacade flightFacade;

    @GetMapping
    public Mono<String> getMarketPlaceReturnCouponPage(
            final Model model,
            @ModelAttribute FlightFilterViewModel flightFilterViewModel
    ) {
        model.addAttribute("flights", flightFacade.getFlights(flightFilterViewModel));
        model.addAttribute("filters", flightFilterViewModel);
        return Mono.just("pages/index");
    }

    @GetMapping("/createFlight")
    public Mono<String> getCreateFlightPage(final Model model) {
        model.addAttribute("flight", new FlightViewModel());
        return Mono.just("pages/createFlight");
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
}
