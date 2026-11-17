package com.tarento.commenthub.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class ErrorResponseTest {

  @Test
  void builderSetsAllFields() {
    ErrorResponse response = ErrorResponse.builder()
        .code("ERR")
        .message("message")
        .errors(Map.of("field", "invalid"))
        .httpStatusCode(400)
        .build();

    assertEquals("ERR", response.getCode());
    assertEquals("message", response.getMessage());
    assertEquals(Map.of("field", "invalid"), response.getErrors());
    assertEquals(400, response.getHttpStatusCode());
  }

  @Test
  void equalsAndHashCodeConsiderAllFields() {
    ErrorResponse first = ErrorResponse.builder().code("ERR").message("msg").httpStatusCode(400).build();
    ErrorResponse second = ErrorResponse.builder().code("ERR").message("msg").httpStatusCode(400).build();
    ErrorResponse different = ErrorResponse.builder().code("OTHER").message("msg").httpStatusCode(400).build();

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
    assertNotEquals(first, different);
    assertNotEquals(null, first);
    assertNotEquals("not-an-error-response", first);
    assertEquals(first, first);
  }

  @Test
  void toStringContainsFieldValues() {
    ErrorResponse response = ErrorResponse.builder().code("ERR").message("msg").build();
    assertNotNull(response.toString());
  }
}
