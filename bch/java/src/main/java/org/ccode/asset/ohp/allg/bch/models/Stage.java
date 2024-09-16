package org.ccode.asset.ohp.allg.bch.models;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.hyperledger.fabric.shim.ChaincodeException;

@DataType()
public class Stage {

  @Property()
  private final String summary;
  @Property()
  private final Reference assessment;
  @Property()
  private final String type;

  public String getSummary() {
    return summary;
  }
  public Reference getAssessment() {
    return assessment;
  }
  public String getType() {
    return type;
  }

  public Stage(
    @JsonProperty("summary")
    String summary,
    @JsonProperty("assessment")
    Reference assessment,
    @JsonProperty("type")
    String type
  ) throws ChaincodeException {

    this.summary = summary;

    this.assessment = assessment;

    this.type = type;
  }
}
