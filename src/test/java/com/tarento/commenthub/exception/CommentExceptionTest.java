package com.tarento.commenthub.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class CommentExceptionTest {

  @Test
  void noArgConstructorLeavesFieldsNull() {
    CommentException exception = new CommentException();
    assertNull(exception.getCode());
    assertNull(exception.getMessage());
    assertNull(exception.getHttpStatusCode());
    assertNull(exception.getErrors());
  }

  @Test
  void codeAndMessageConstructorSetsFields() {
    CommentException exception = new CommentException("ERR_CODE", "something went wrong");
    assertEquals("ERR_CODE", exception.getCode());
    assertEquals("something went wrong", exception.getMessage());
    assertNull(exception.getHttpStatusCode());
  }

  @Test
  void codeMessageAndStatusConstructorSetsFields() {
    CommentException exception = new CommentException("ERR_CODE", "not found", 404);
    assertEquals("ERR_CODE", exception.getCode());
    assertEquals("not found", exception.getMessage());
    assertEquals(404, exception.getHttpStatusCode());
  }

  @Test
  void errorsMapConstructorSetsMessageAndErrors() {
    Map<String, String> errors = Map.of("field", "is required");
    CommentException exception = new CommentException(errors);
    assertEquals(errors, exception.getErrors());
    assertEquals(errors.toString(), exception.getMessage());
  }
}
