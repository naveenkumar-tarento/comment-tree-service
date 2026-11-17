package com.tarento.commenthub.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import org.junit.jupiter.api.Test;

class CommentTreeTest {

  @Test
  void noArgConstructorLeavesDefaults() {
    CommentTree tree = new CommentTree();
    assertNull(tree.getCommentTreeId());
    assertNull(tree.getCommentTreeData());
    assertNull(tree.getStatus());
    assertNull(tree.getCreatedDate());
    assertNull(tree.getLastUpdatedDate());
  }

  @Test
  void settersRoundTrip() {
    CommentTree tree = new CommentTree();
    Timestamp now = new Timestamp(System.currentTimeMillis());

    tree.setCommentTreeId("tree-1");
    tree.setCommentTreeData(null);
    tree.setStatus("active");
    tree.setCreatedDate(now);
    tree.setLastUpdatedDate(now);

    assertEquals("tree-1", tree.getCommentTreeId());
    assertEquals("active", tree.getStatus());
    assertEquals(now, tree.getCreatedDate());
    assertEquals(now, tree.getLastUpdatedDate());
  }
}
