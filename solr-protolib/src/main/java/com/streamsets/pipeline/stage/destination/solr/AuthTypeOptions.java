package com.streamsets.pipeline.stage.destination.solr;

import com.streamsets.pipeline.api.GenerateResourceBundle;
import com.streamsets.pipeline.api.Label;

@GenerateResourceBundle
public enum AuthTypeOptions implements Label {
  NONE("None", AuthType.NONE),
  KERBEROS("Kerberos",  AuthType.KERBEROS),
  BASIC_AUTH("Basic Auth",  AuthType.BASIC_AUTH),
  ;


  private final String label;
  private AuthType authType;

  AuthTypeOptions(String label, AuthType authType) {
    this.label = label;
    this.authType = authType;
  }

  @Override
  public String getLabel() {
    return label;
  }

  public AuthType getAuthType() {
    return authType;
  }

}
