package com.tarento.commenthub.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class SearchCriteriaTest {

  @Test
  void noArgConstructorLeavesDefaults() {
    SearchCriteria criteria = new SearchCriteria();
    assertNull(criteria.getLimit());
    assertNull(criteria.getOffset());
    assertNull(criteria.getCommentTreeId());
    assertNull(criteria.getEntityType());
    assertNull(criteria.getEntityId());
    assertNull(criteria.getWorkflow());
    assertFalse(criteria.isOverrideCache());
    assertFalse(criteria.isEnrichedUser());
  }

  @Test
  void allArgsConstructorAndSettersRoundTrip() {
    SearchCriteria criteria = new SearchCriteria(10, 0, "tree-1", "type-1", "entity-1", "workflow-1", true, true);

    assertEquals(10, criteria.getLimit());
    assertEquals(0, criteria.getOffset());
    assertEquals("tree-1", criteria.getCommentTreeId());
    assertEquals("type-1", criteria.getEntityType());
    assertEquals("entity-1", criteria.getEntityId());
    assertEquals("workflow-1", criteria.getWorkflow());
    assertEquals(true, criteria.isOverrideCache());
    assertEquals(true, criteria.isEnrichedUser());

    criteria.setLimit(20);
    criteria.setOffset(5);
    criteria.setCommentTreeId("tree-2");
    criteria.setEntityType("type-2");
    criteria.setEntityId("entity-2");
    criteria.setWorkflow("workflow-2");
    criteria.setOverrideCache(false);
    criteria.setEnrichedUser(false);

    assertEquals(20, criteria.getLimit());
    assertEquals(5, criteria.getOffset());
    assertEquals("tree-2", criteria.getCommentTreeId());
    assertEquals("type-2", criteria.getEntityType());
    assertEquals("entity-2", criteria.getEntityId());
    assertEquals("workflow-2", criteria.getWorkflow());
    assertFalse(criteria.isOverrideCache());
    assertFalse(criteria.isEnrichedUser());
  }
}
