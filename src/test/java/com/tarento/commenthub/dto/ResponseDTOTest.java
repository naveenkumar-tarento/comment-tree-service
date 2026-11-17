package com.tarento.commenthub.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.tarento.commenthub.entity.CommentTree;
import org.junit.jupiter.api.Test;

class ResponseDTOTest {

  @Test
  void noArgConstructorLeavesCommentTreeNull() {
    ResponseDTO dto = new ResponseDTO();
    assertNull(dto.getCommentTree());
  }

  @Test
  void allArgsConstructorAndSetterRoundTrip() {
    CommentTree tree = new CommentTree("tree-1", null, "active", null, null);
    ResponseDTO dto = new ResponseDTO(tree);
    assertEquals(tree, dto.getCommentTree());

    CommentTree otherTree = new CommentTree("tree-2", null, "inactive", null, null);
    dto.setCommentTree(otherTree);
    assertEquals(otherTree, dto.getCommentTree());
  }
}
