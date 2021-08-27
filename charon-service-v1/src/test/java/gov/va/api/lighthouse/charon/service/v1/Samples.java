package gov.va.api.lighthouse.charon.service.v1;

import gov.va.api.lighthouse.charon.api.ConnectionDetails;
import gov.va.api.lighthouse.charon.api.v1.RpcPrincipalV1;

public class Samples {
  static ConnectionDetails connectionDetail(int n) {
    return ConnectionDetails.builder()
        .name("v" + n)
        .host("v" + n + ".com")
        .port(8000 + n)
        .divisionIen("" + n)
        .build();
  }

  static RpcPrincipalV1 principal(String tag) {
    return RpcPrincipalV1.builder().accessCode(tag + "+ac").verifyCode(tag + "+vc").build();
  }
}
