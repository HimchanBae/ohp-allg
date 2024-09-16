package org.ccode.asset.ohp.allg.bch.models;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.hyperledger.fabric.shim.ChaincodeException;

@DataType()
public class Abatement {

  @Property()
  private final String dateTime;
  @Property()
  private final Integer age;
  @Property()
  private final DatePeriod period;
  @Property()
  private final String range;
  @Property()
  private final String string;

  public String getDateTime() {
    return dateTime;
  }
  public Integer getAge() {
    return age;
  }
  public DatePeriod getPeriod() {
    return period;
  }
  public String getRange() {
    return range;
  }
  public String getString() {
    return string;
  }

  public Abatement(
    @JsonProperty("dateTime")
    String dateTime,
    @JsonProperty("age")
    Integer age,
    @JsonProperty("period")
    DatePeriod period,
    @JsonProperty("range")
    String range,
    @JsonProperty("string")
    String string
  ) throws ChaincodeException {

    this.dateTime = dateTime;

    this.age = age;

    this.period = period;

    this.range = range;

    this.string = string;
  }
}
