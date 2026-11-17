package com.tarento.commenthub.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.tarento.commenthub.constant.Constants;
import com.tarento.commenthub.dto.ApiResponse;
import com.tarento.commenthub.dto.CommentTreeIdentifierDTO;
import com.tarento.commenthub.service.CommentTreeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CommentTreeControllerTest {

  @Mock
  private CommentTreeService commentTreeService;

  private CommentTreeController controller;

  @BeforeEach
  void setUp() {
    controller = new CommentTreeController(commentTreeService);
  }

  @Test
  void healthCheckReturnsSuccessString() {
    assertEquals(Constants.SUCCESS_STRING, controller.healthCheck());
  }

  @Test
  void searchReturnsOkWithEmptyBodyWhenNotFoundAndEmptyResult() {
    CommentTreeIdentifierDTO dto = new CommentTreeIdentifierDTO("type-1", "entity-1", "workflow-1");
    ApiResponse serviceResponse = new ApiResponse();
    serviceResponse.setResponseCode(HttpStatus.NOT_FOUND);
    when(commentTreeService.getCommentTree(dto)).thenReturn(serviceResponse);

    ResponseEntity<ApiResponse> result = controller.search(dto);

    assertEquals(HttpStatus.OK, result.getStatusCode());
  }

  @Test
  void searchReturnsServiceResponseCodeWhenResultPresent() {
    CommentTreeIdentifierDTO dto = new CommentTreeIdentifierDTO("type-1", "entity-1", "workflow-1");
    ApiResponse serviceResponse = new ApiResponse();
    serviceResponse.setResponseCode(HttpStatus.OK);
    serviceResponse.put("key", "value");
    when(commentTreeService.getCommentTree(dto)).thenReturn(serviceResponse);

    ResponseEntity<ApiResponse> result = controller.search(dto);

    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(serviceResponse, result.getBody());
  }
}
