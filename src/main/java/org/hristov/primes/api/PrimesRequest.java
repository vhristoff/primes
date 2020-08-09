package org.hristov.primes.api;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class PrimesRequest {

  @Schema(description = "An input integer", required = true, example = "11")
  @Max(1000000000000000000L)
  @Min(1)
  private long number;
}
