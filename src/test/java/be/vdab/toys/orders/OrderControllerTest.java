package be.vdab.toys.orders;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class OrderControllerTest {

    public static final String ORDERS_TABLE = "orders";
    private final MockMvcTester mockMvcTester;
    private final JdbcClient jdbc;

    public OrderControllerTest(MockMvcTester mockMvcTester, JdbcClient jdbc) {
        this.mockMvcTester = mockMvcTester;
        this.jdbc = jdbc;
    }

    @Test
    void findAllUnshippedHasCorrectSize() {
        assertThat(mockMvcTester.get().uri("/orders/unshipped"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("length()")
                .isEqualTo(JdbcTestUtils.countRowsInTableWhere(this.jdbc, ORDERS_TABLE, "status not in ('SHIPPED', 'CANCELLED')"));
    }

    @Test
    void findByNonExistingNumberFails() {
        assertThat(mockMvcTester.get().uri("/orders/" + Long.MAX_VALUE))
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void shipNonExistingOrderFails() {
        assertThat(mockMvcTester.post().uri("/orders/" + Long.MAX_VALUE + "/shippings"))
                .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    @Sql("/orders.sql")
    void shipCancelledOrderFails() {
        var id = findIdOfCancelledOrder();
        assertThat(mockMvcTester.post().uri("/orders/"+ id + "/shippings"))
                .hasStatus(HttpStatus.CONFLICT);
    }

    private long findIdOfCancelledOrder() {
        return jdbc.sql("select id from orders where status = 'CANCELLED'")
                .query(Integer.class)
                .single();
    }
}
