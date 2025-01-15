package BoardsFunctionality;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

public class BoardAPITesting {
    @Test
    public void
    lotto_resource_returns_200_with_expected_id_and_winners() {

        when().
                get("http://localhost:5000/",5).
                then().
                statusCode(200).
                body("lotto.lottoId", equalTo(5),
                        "lotto.winners.winnerId", hasItems(23, 54));

    }
}
