package com.tarento.commenthub.utility;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class CommentsUtilityTest {

  @Test
  void containsNullReturnsTrueForNullList() {
    assertTrue(CommentsUtility.containsNull(null));
  }

  @Test
  void containsNullReturnsTrueWhenListHasNullElement() {
    assertTrue(CommentsUtility.containsNull(Arrays.asList("a", null, "b")));
  }

  @Test
  void containsNullReturnsFalseWhenNoNullElement() {
    assertFalse(CommentsUtility.containsNull(Arrays.asList("a", "b")));
  }

  @Test
  void containsNullReturnsFalseForEmptyList() {
    assertFalse(CommentsUtility.containsNull(Collections.emptyList()));
  }
}
