package org.hristov.primes.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class PrimesCheckResponse {
  @Schema(description = "The input integer", example = "11")
  private long number;

  @Schema(description = "Whether the input integer is a prime", example = "true")
  @JsonProperty("isPrime")
  private boolean isPrime;
}
