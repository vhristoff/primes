package org.hristov.primes.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrimesService {

  public boolean checkNumber(final long number) {
    if (number <= 3) {
      return number > 1;
    } else if (number % 2 == 0 || number % 3 == 0) {
      return false;
    }
    for (long i = 6; i * i <= number; i = i + 6) {
      if (number % (i + 1) == 0 || number % (i - 1) == 0) {
        return false;
      }
    }
    return true;
  }

  public long nextPrime(final long number) {
    if (number < 2) return 2;
    if (number == 2) return 3;
    long start = number % 6 == 0 ? number : number + (6 - number % 6);
    for (long i = start; i > 0; i = i + 6) {

      if (i - 1 > number && checkNumber(i - 1)) return i - 1;
      if (checkNumber(i + 1)) return i + 1;
    }
    throw new RuntimeException("Could not find next prime number");
  }
}
