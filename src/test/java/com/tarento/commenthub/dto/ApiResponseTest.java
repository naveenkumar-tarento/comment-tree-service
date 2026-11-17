package com.tarento.commenthub.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiResponseTest {

  @Test
  void defaultConstructorInitializesVersionTimestampAndParams() {
    ApiResponse response = new ApiResponse();
    assertEquals("v1", response.getVer());
    assertNotNull(response.getTs());
    assertNotNull(response.getParams());
  }

  @Test
  void idConstructorSetsId() {
    ApiResponse response = new ApiResponse("req-1");
    assertEquals("req-1", response.getId());
  }

  @Test
  void settersAndGettersRoundTrip() {
    ApiResponse response = new ApiResponse();
    response.setId("id-1");
    response.setVer("v2");
    response.setTs("ts-1");
    ApiRespParam params = new ApiRespParam("msg-1");
    response.setParams(params);
    response.setResponseCode(HttpStatus.OK);

    assertEquals("id-1", response.getId());
    assertEquals("v2", response.getVer());
    assertEquals("ts-1", response.getTs());
    assertEquals(params, response.getParams());
    assertEquals(HttpStatus.OK, response.getResponseCode());
  }

  @Test
  void resultMapHelpersManipulateUnderlyingMap() {
    ApiResponse response = new ApiResponse();
    response.put("key1", "value1");
    assertTrue(response.containsKey("key1"));
    assertEquals("value1", response.get("key1"));

    Map<String, Object> extra = new HashMap<>();
    extra.put("key2", "value2");
    response.putAll(extra);
    assertTrue(response.containsKey("key2"));

    Map<String, Object> replacement = new HashMap<>();
    replacement.put("key3", "value3");
    response.setResult(replacement);
    assertEquals(replacement, response.getResult());
    assertFalse(response.containsKey("key1"));
  }
}
