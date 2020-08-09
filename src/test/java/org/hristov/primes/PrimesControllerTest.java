package org.hristov.primes;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hristov.primes.api.PrimesController;
import org.hristov.primes.service.PrimesService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PrimesController.class)
class PrimesControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private PrimesService service;

  @Test
  void test_checkWithOutOfRangeNumber_shouldReturn400() throws Exception {
    String outOfRangeNumber = "1234556712313425465462341342";
    this.mockMvc
        .perform(
            post("/primes/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\": " + outOfRangeNumber + "}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(
            jsonPath(
                "$.detail",
                startsWith(
                    "JSON parse error: Numeric value ("
                        + outOfRangeNumber
                        + ") out of range of long")));
  }

  @Test
  void test_nextWithOutOfRangeNumber_shouldReturn400() throws Exception {
    String outOfRangeNumber = "1234556712313425465462341342";
    this.mockMvc
        .perform(
            post("/primes/next")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\": " + outOfRangeNumber + "}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(
            jsonPath(
                "$.detail",
                startsWith(
                    "JSON parse error: Numeric value ("
                        + outOfRangeNumber
                        + ") out of range of long")));
  }

  @Test
  void test_checkWithAlphabeticInput_shouldReturn400() throws Exception {
    String string = "not a number";
    this.mockMvc
        .perform(
            post("/primes/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\": " + string + "}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.detail", startsWith("JSON parse error: Unrecognized token")));
  }

  @Test
  void test_nextWithAlphabeticInput_shouldReturn400() throws Exception {
    String string = "not a number";
    this.mockMvc
        .perform(
            post("/primes/next")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\": " + string + "}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.detail", startsWith("JSON parse error: Unrecognized token")));
  }

  @Test
  void test_checkWithOverMaxValue_shouldReturn400() throws Exception {
    long overMax = 1000000000000000001L;
    this.mockMvc
        .perform(
            post("/primes/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\": " + overMax + "}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(
            jsonPath("$.detail", is("number: must be less than or equal to 1000000000000000000")));
  }

  @Test
  void test_nextWithOverMaxValue_shouldReturn400() throws Exception {
    long overMax = 1000000000000000001L;
    this.mockMvc
        .perform(
            post("/primes/next")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\": " + overMax + "}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(
            jsonPath("$.detail", is("number: must be less than or equal to 1000000000000000000")));
  }

  @Test
  void test_checkWithUnderMinValue_shouldReturn400() throws Exception {
    long underMin = 0;
    this.mockMvc
        .perform(
            post("/primes/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\": " + underMin + "}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.detail", is("number: must be greater than or equal to 1")));
  }

  @Test
  void test_nextWithUnderMinValue_shouldReturn400() throws Exception {
    long underMin = 0;
    this.mockMvc
        .perform(
            post("/primes/next")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\": " + underMin + "}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.detail", is("number: must be greater than or equal to 1")));
  }

  @Test
  void test_check_shouldReturn200() throws Exception {
    long input = 11;

    BDDMockito.given(this.service.checkNumber(input)).willReturn(true);

    this.mockMvc
        .perform(
            post("/primes/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\": " + input + "}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.number", is((int) input)))
        .andExpect(jsonPath("$.isPrime", is(true)));
  }

  @Test
  void test_next_shouldReturn200() throws Exception {
    long input = 11;
    long output = 13;

    BDDMockito.given(this.service.nextPrime(input)).willReturn(output);

    this.mockMvc
        .perform(
            post("/primes/next")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"number\": " + input + "}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.number", is((int) input)))
        .andExpect(jsonPath("$.nextPrime", is((int) output)));
  }
}
