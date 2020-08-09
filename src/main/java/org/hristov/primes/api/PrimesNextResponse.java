package org.hristov.primes.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class PrimesNextResponse {
  @Schema(description = "The input integer", example = "11")
  private long number;

  @Schema(description = "The smallest prime greater than the input integer", example = "13")
  private long nextPrime;
}
