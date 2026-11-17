package com.tarento.commenthub.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ApiRespParamTest {

  @Test
  void noArgConstructorLeavesFieldsNull() {
    ApiRespParam params = new ApiRespParam();
    assertNull(params.getResMsgId());
    assertNull(params.getMsgId());
    assertNull(params.getErr());
    assertNull(params.getStatus());
    assertNull(params.getErrMsg());
  }

  @Test
  void idConstructorSetsResMsgIdAndMsgId() {
    ApiRespParam params = new ApiRespParam("req-1");
    assertEquals("req-1", params.getResMsgId());
    assertEquals("req-1", params.getMsgId());
  }

  @Test
  void settersAndGettersRoundTrip() {
    ApiRespParam params = new ApiRespParam();
    params.setResMsgId("res-1");
    params.setMsgId("msg-1");
    params.setErr("err-1");
    params.setStatus("status-1");
    params.setErrMsg("errMsg-1");

    assertEquals("res-1", params.getResMsgId());
    assertEquals("msg-1", params.getMsgId());
    assertEquals("err-1", params.getErr());
    assertEquals("status-1", params.getStatus());
    assertEquals("errMsg-1", params.getErrMsg());
  }
}
