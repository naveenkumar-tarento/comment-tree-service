package com.tarento.commenthub.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.tarento.commenthub.entity.CommentTree;
import org.junit.jupiter.api.Test;

class CommentsResoponseDTOTest {

  @Test
  void noArgConstructorLeavesDefaults() {
    CommentsResoponseDTO dto = new CommentsResoponseDTO();
    assertNull(dto.getCommentTree());
    assertEquals(0, dto.getCommentCount());
  }

  @Test
  void allArgsConstructorAndSettersRoundTrip() {
    CommentTree tree = new CommentTree("tree-1", null, "active", null, null);
    CommentsResoponseDTO dto = new CommentsResoponseDTO(tree, 5);

    assertEquals(tree, dto.getCommentTree());
    assertEquals(5, dto.getCommentCount());

    dto.setCommentCount(7);
    assertEquals(7, dto.getCommentCount());
  }
}
