package com.tarento.commenthub.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class CommentTreeIdentifierDTOTest {

  @Test
  void noArgConstructorLeavesDefaults() {
    CommentTreeIdentifierDTO dto = new CommentTreeIdentifierDTO();
    assertNull(dto.getEntityType());
    assertNull(dto.getEntityId());
    assertNull(dto.getWorkflow());
  }

  @Test
  void settersRoundTrip() {
    CommentTreeIdentifierDTO dto = new CommentTreeIdentifierDTO();
    dto.setEntityType("type-1");
    dto.setEntityId("entity-1");
    dto.setWorkflow("workflow-1");

    assertEquals("type-1", dto.getEntityType());
    assertEquals("entity-1", dto.getEntityId());
    assertEquals("workflow-1", dto.getWorkflow());
  }
}
