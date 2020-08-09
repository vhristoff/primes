package org.hristov.primes.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class ApiError {
  private int status;
  private String detail;
}
