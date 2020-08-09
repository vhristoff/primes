package org.hristov.primes;

import org.assertj.core.api.Assertions;
import org.hristov.primes.service.PrimesService;
import org.junit.jupiter.api.Test;

class PrimesServiceTest {

  private final PrimesService service = new PrimesService();

  @Test
  void test_checkNumberWithPrimeNumber_shouldReturnTrue() {
    boolean result = this.service.checkNumber(47);

    Assertions.assertThat(result).isTrue();
  }

  @Test
  void test_checkNumberWithNonPrimeNumber_shouldReturnFalse() {
    boolean result = this.service.checkNumber(111);

    Assertions.assertThat(result).isFalse();
  }

  @Test
  void test_nextPrime_shouldSucceed() {
    long prime = this.service.nextPrime(47);

    Assertions.assertThat(prime).isEqualTo(53);
  }
}
