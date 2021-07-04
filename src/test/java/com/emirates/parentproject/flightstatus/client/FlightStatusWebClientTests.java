package com.emirates.parentproject.flightstatus.client;

import com.emirates.parentproject.flightstatus.bean.FlightStatusDownstreamResponse;
import com.emirates.parentproject.flightstatus.bean.FlightStatusRequest;
import com.emirates.parentproject.flightstatus.bean.ServiceRestBean;
import com.emirates.parentproject.flightstatus.functions.FlightStatusFunctions;
import com.emirates.parentproject.flightstatus.handlers.FlightStatusHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FlightStatusWebClientTests {

    private static MockWebServer mockBackend;

    @Autowired
    private FlightStatusWebClient flightStatusWebClient;

    Randomizer randomizer;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private FlightStatusHandler flightStatusHandler;

    private FlightStatusFunctions flightStatusFunctions;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws IOException {
        mockBackend = new MockWebServer();
        mockBackend.start(9090);
        this.flightStatusWebClient = new FlightStatusWebClient();
        objectMapper = new ObjectMapper();
        flightStatusFunctions = new FlightStatusFunctions();
        randomizer = new Randomizer();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackend.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s",
                mockBackend.getPort());
    }

    static class Randomizer {
        public static int generate(int min, int max) {
            return min + (int) (Math.random() * ((max - min) + 1));
        }
    }

    @Test
    public void testGetFlightInformation() throws Exception {

        FlightStatusRequest request = FlightStatusRequest.builder().flightDate(new Date()).departureAirport("DXB").arrival("LHR").build();
        ServiceRestBean restRequest = ServiceRestBean.builder().data(request).build();

        FlightStatusDownstreamResponse downstreamResponse = FlightStatusDownstreamResponse.builder().flightNumber("DummyFlightNumberFromOkHttpMockServer").build();

        // Getting 5 random response times
        List<Integer> responseTimes = generateRandomResponseTimes.apply(Tuples.of(500, 800));

        // This is a spring boot Warmup to achieve the desired SLA (850 ms)
        warmupSpringBoot.accept(Tuples.of(new ObjectMapper().writeValueAsString(downstreamResponse), responseTimes, restRequest));

        // Setting server responses in the mock server
        setServerResponses.accept(Tuples.of(new ObjectMapper().writeValueAsString(downstreamResponse), responseTimes));

        long startMillis = System.currentTimeMillis();
        webTestClient.post().uri("/flightStatus/ﬂight")
                .body(Mono.just(restRequest), FlightStatusRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON)
                .expectBody()
                .jsonPath("$.flightNumber").isNotEmpty()
                .jsonPath("$.flightNumber").isEqualTo("DummyFlightNumberFromOkHttpMockServer");

        long responseTime = System.currentTimeMillis() - startMillis;
        assertTrue("Total time should be less than or equal to the SLA (850 ms)", responseTime <= 850);
        System.out.println("Total Requests = " + mockBackend.getRequestCount());
    }

    private Function<Tuple2<Integer, Integer>, List<Integer>> generateRandomResponseTimes = (tuple) -> {

        List<Integer> responseTimes = new ArrayList();
        for (int i = 0; i < 5; i++) {
            int randomResponseTime = Randomizer.generate(tuple.getT1(), tuple.getT2());
            responseTimes.add(randomResponseTime);
        }
        return responseTimes;
    };

    private Consumer<Tuple2<String, List<Integer>>> setServerResponses = tuple -> {

        // 5 mock responses for spring boot warmup and
        // 5 mock responses for actual call
        for (int i = 0; i < 5; i++) {
            mockBackend.enqueue(new MockResponse()
                    .setBody(tuple.getT1())
                    .setBodyDelay(tuple.getT2().get(i), TimeUnit.MILLISECONDS)
                    .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            );
        }
    };

    private Consumer<Tuple3<String, List<Integer>, ServiceRestBean>> warmupSpringBoot = tuple -> {

        setServerResponses.accept(Tuples.of(tuple.getT1(),
                tuple.getT2()));

        // ************************ Spring Boot Warmup ************************* //
        // This warmup is introduced in the test just to achieve the required SLA (850 ms)
        webTestClient.post().uri("/flightStatus/ﬂight")
                .body(Mono.just(tuple.getT3()), FlightStatusRequest.class)
                .exchange()
                .expectStatus().isOk();

        // **********************************************************************
    };

}