package com.tarento.commenthub.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarento.commenthub.constant.Constants;
import com.tarento.commenthub.dto.ApiResponse;
import com.tarento.commenthub.dto.CommentTreeIdentifierDTO;
import com.tarento.commenthub.entity.CommentTree;
import com.tarento.commenthub.exception.CommentException;
import com.tarento.commenthub.repository.CommentTreeRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class CommentTreeServiceImplTest {

  @Mock
  private ObjectMapper objectMapper;

  @Mock
  private CommentTreeRepository commentTreeRepository;

  private CommentTreeServiceImpl commentTreeService;

  @BeforeEach
  void setUp() {
    commentTreeService = new CommentTreeServiceImpl(objectMapper, commentTreeRepository);
    ReflectionTestUtils.setField(commentTreeService, "jwtSecretKey", "test-secret-key");
  }

  @Test
  void getCommentTreeByIdReturnsTreeWhenPresent() {
    CommentTree tree = new CommentTree("tree-1", null, "active", null, null);
    when(commentTreeRepository.findById("tree-1")).thenReturn(Optional.of(tree));

    CommentTree result = commentTreeService.getCommentTreeById("tree-1");

    assertEquals("tree-1", result.getCommentTreeId());
  }

  @Test
  void getCommentTreeByIdThrowsWhenMissing() {
    when(commentTreeRepository.findById("missing")).thenReturn(Optional.empty());

    CommentException exception = assertThrows(CommentException.class,
        () -> commentTreeService.getCommentTreeById("missing"));

    assertEquals(Constants.ERROR, exception.getCode());
    assertEquals(HttpStatus.OK.value(), exception.getHttpStatusCode());
  }

  @Test
  void generateJwtTokenKeyThrowsWhenFieldsBlank() {
    CommentTreeIdentifierDTO dto = new CommentTreeIdentifierDTO("", "entity-1", "workflow-1");

    CommentException exception = assertThrows(CommentException.class,
        () -> commentTreeService.generateJwtTokenKey(dto));

    assertEquals(Constants.ERROR, exception.getCode());
  }

  @Test
  void generateJwtTokenKeyReturnsSignedTokenWhenFieldsPresent() {
    CommentTreeIdentifierDTO dto = new CommentTreeIdentifierDTO("type-1", "entity-1", "workflow-1");

    String token = commentTreeService.generateJwtTokenKey(dto);

    assertNotNull(token);
    assertEquals(3, token.split("\\.").length);
  }

  @Test
  void getCommentTreeReturnsResultWhenTreeFound() {
    CommentTreeIdentifierDTO dto = new CommentTreeIdentifierDTO("type-1", "entity-1", "workflow-1");
    CommentTree tree = new CommentTree("tree-1", null, "active", null, null);
    Map<String, Object> converted = new HashMap<>();
    converted.put("status", "active");

    when(commentTreeRepository.findById(anyString())).thenReturn(Optional.of(tree));
    when(objectMapper.convertValue(any(), eq(Map.class))).thenReturn(converted);

    ApiResponse response = commentTreeService.getCommentTree(dto);

    assertEquals(HttpStatus.OK, response.getResponseCode());
    assertEquals(converted, response.getResult());
  }

  @Test
  void getCommentTreeReturnsNotFoundWhenTreeMissing() {
    CommentTreeIdentifierDTO dto = new CommentTreeIdentifierDTO("type-1", "entity-1", "workflow-1");
    when(commentTreeRepository.findById(anyString())).thenReturn(Optional.empty());

    ApiResponse response = commentTreeService.getCommentTree(dto);

    assertEquals(HttpStatus.NOT_FOUND, response.getResponseCode());
    assertEquals("Comment Tree not found", response.get("message"));
  }

  @Test
  void getCommentTreeReturnsInternalServerErrorWhenMandatoryFieldsMissing() {
    CommentTreeIdentifierDTO dto = new CommentTreeIdentifierDTO("", "entity-1", "workflow-1");

    ApiResponse response = commentTreeService.getCommentTree(dto);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getResponseCode());
    assertNotNull(response.get("error"));
  }

  @Test
  void getCommentTreeIdentifierDTOMapsJsonFields() {
    ObjectMapper realMapper = new ObjectMapper();
    JsonNode node = realMapper.createObjectNode()
        .put(Constants.ENTITY_TYPE, "type-1")
        .put(Constants.ENTITY_ID, "entity-1")
        .put(Constants.WORKFLOW, "workflow-1");

    CommentTreeIdentifierDTO dto = commentTreeService.getCommentTreeIdentifierDTO(node);

    assertEquals("type-1", dto.getEntityType());
    assertEquals("entity-1", dto.getEntityId());
    assertEquals("workflow-1", dto.getWorkflow());
  }

  @Test
  void findTargetNodeReturnsCurrentNodeWhenIndexExceedsPath() {
    JsonNode currentNode = new ObjectMapper().createObjectNode();
    JsonNode result = CommentTreeServiceImpl.findTargetNode(currentNode, new String[0], 0);
    assertEquals(currentNode, result);
  }

  @Test
  void findTargetNodeDescendsThroughNestedChildrenToLeafNode() throws Exception {
    ObjectMapper realMapper = new ObjectMapper();
    JsonNode root = realMapper.readTree(
        "[{\"commentId\":\"c1\",\"children\":[{\"commentId\":\"c2\"}]}]");

    JsonNode result = CommentTreeServiceImpl.findTargetNode(root, new String[] {"c1", "c2"}, 0);

    assertNotNull(result);
    assertEquals("c2", result.get(Constants.COMMENT_ID).asText());
  }

  @Test
  void findTargetNodeReturnsNullWhenNoChildMatches() throws Exception {
    ObjectMapper realMapper = new ObjectMapper();
    JsonNode root = realMapper.readTree("[{\"commentId\":\"other\"}]");

    JsonNode result = CommentTreeServiceImpl.findTargetNode(root, new String[] {"c1"}, 0);

    assertNull(result);
  }

  @Test
  void findTargetNodeReturnsNullWhenCurrentNodeIsNotArray() throws Exception {
    ObjectMapper realMapper = new ObjectMapper();
    JsonNode root = realMapper.readTree("{\"commentId\":\"c1\"}");

    JsonNode result = CommentTreeServiceImpl.findTargetNode(root, new String[] {"c1"}, 0);

    assertNull(result);
  }
}
