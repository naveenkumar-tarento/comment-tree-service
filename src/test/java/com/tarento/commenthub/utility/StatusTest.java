package com.tarento.commenthub.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class StatusTest {

  @Test
  void valuesContainsActiveAndInactive() {
    assertArrayEquals(new Status[] {Status.ACTIVE, Status.INACTIVE}, Status.values());
  }

  @Test
  void valueOfResolvesEnumConstants() {
    assertEquals(Status.ACTIVE, Status.valueOf("ACTIVE"));
    assertEquals(Status.INACTIVE, Status.valueOf("INACTIVE"));
  }
}
