package technical.test.api.representation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AirportRepresentation {
    private String iata;
    private String name;
    private String country;
}
