package technical.test.renderer.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlightFilterViewModel {
    private Double minPrice;
    private Double maxPrice;
    private String origin;
    private String destination;
    private String sortBy;
    private String sortDirection;
    private Integer page;
}
