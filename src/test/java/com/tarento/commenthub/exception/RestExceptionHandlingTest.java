package com.tarento.commenthub.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class RestExceptionHandlingTest {

  private final RestExceptionHandling handler = new RestExceptionHandling();

  @Test
  void commentExceptionIsMappedToOkWithItsOwnCodeAndMessage() {
    CommentException commentException = new CommentException("NOT_FOUND", "tree missing", 404);

    ResponseEntity<ErrorResponse> response = handler.handleException(commentException);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("NOT_FOUND", response.getBody().getCode());
    assertEquals("tree missing", response.getBody().getMessage());
    assertEquals(404, response.getBody().getHttpStatusCode());
  }

  @Test
  void commentExceptionWithoutHttpStatusCodeDefaultsToOk() {
    CommentException commentException = new CommentException("ERR", "bad input");

    ResponseEntity<ErrorResponse> response = handler.handleException(commentException);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(HttpStatus.OK.value(), response.getBody().getHttpStatusCode());
  }

  @Test
  void genericExceptionIsMappedToInternalServerError() {
    RuntimeException genericException = new RuntimeException("boom");

    ResponseEntity<ErrorResponse> response = handler.handleException(genericException);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("boom", response.getBody().getCode());
  }
}
