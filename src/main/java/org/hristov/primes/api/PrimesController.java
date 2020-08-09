package org.hristov.primes.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hristov.primes.error.ApiError;
import org.hristov.primes.service.PrimesService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "/primes",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class PrimesController {

  private final PrimesService service;

  public PrimesController(PrimesService service) {
    this.service = service;
  }

  @Operation(summary = "Checks if a given number is prime.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "The result of the check",
            content = @Content(schema = @Schema(implementation = PrimesCheckResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Error Response",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
      })
  @PostMapping("/check")
  public ResponseEntity<PrimesCheckResponse> check(@RequestBody @Valid PrimesRequest request) {
    PrimesController.log.info("Check prime request: " + request.toString());
    PrimesCheckResponse response =
        PrimesCheckResponse.create(
            request.getNumber(), this.service.checkNumber(request.getNumber()));
    PrimesController.log.info("Check prime request: " + response.toString());

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Returns next prime number.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Next prime number",
            content = @Content(schema = @Schema(implementation = PrimesNextResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Error Response",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
      })
  @PostMapping("/next")
  public ResponseEntity<PrimesNextResponse> next(@RequestBody @Valid PrimesRequest request) {
    PrimesController.log.info("Next prime request: " + request.toString());
    PrimesNextResponse response =
        PrimesNextResponse.create(request.getNumber(), this.service.nextPrime(request.getNumber()));
    PrimesController.log.info("Next prime response: " + response.toString());

    return ResponseEntity.ok(response);
  }
}
