package org.ccode.asset.ohp.allg.bch.models;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.hyperledger.fabric.shim.ChaincodeException;

@DataType()
public class Condition extends BaseModel {

  @Property()
  private final String[] identifier;
  @Property()
  @Required()
  private final String clinicalStatus;
  @Property()
  @Required()
  private final String verificationStatus;
  @Property()
  @Required()
  private final String severity;
  @Property()
  @Required()
  private final String category;
  @Property()
  private final String code;
  @Property()
  private final String bodySite;
  @Property()
  private final Reference subject;
  @Property()
  private final Reference encounter;
  @Property()
  private final Onset onset;
  @Property()
  private final Abatement abatement;
  @Property()
  private final String recordedDate;
  @Property()
  private final Participant participant;
  @Property()
  private final Stage Stage;
  @Property()
  private final String evidence;
  @Property()
  private final String note;

  public String[] getIdentifier() {
    return identifier;
  }
  public String getClinicalStatus() {
    return clinicalStatus;
  }
  public String getVerificationStatus() {
    return verificationStatus;
  }
  public String getSeverity() {
    return severity;
  }
  public String getCategory() {
    return category;
  }
  public String getCode() {
    return code;
  }
  public String getBodySite() {
    return bodySite;
  }
  public Reference getSubject() {
    return subject;
  }
  public Reference getEncounter() {
    return encounter;
  }
  public Onset getOnset() {
    return onset;
  }
  public Abatement getAbatement() {
    return abatement;
  }
  public String getRecordedDate() {
    return recordedDate;
  }
  public Participant getParticipant() {
    return participant;
  }
  public Stage getStage() {
    return Stage;
  }
  public String getEvidence() {
    return evidence;
  }
  public String getNote() {
    return note;
  }

  public Condition(
    @JsonProperty("primaryIdentifier")
    String primaryIdentifier,
    @JsonProperty("dataHash")
    String dataHash,
    @JsonProperty("modifiedBy")
    String modifiedBy,

    @JsonProperty("identifier")
    String[] identifier,
    @JsonProperty("clinicalStatus")
    String clinicalStatus,
    @JsonProperty("verificationStatus")
    String verificationStatus,
    @JsonProperty("severity")
    String severity,
    @JsonProperty("category")
    String category,
    @JsonProperty("code")
    String code,
    @JsonProperty("bodySite")
    String bodySite,
    @JsonProperty("subject")
    Reference subject,
    @JsonProperty("encounter")
    Reference encounter,
    @JsonProperty("onset")
    Onset onset,
    @JsonProperty("abatement")
    Abatement abatement,
    @JsonProperty("recordedDate")
    String recordedDate,
    @JsonProperty("participant")
    Participant participant,
    @JsonProperty("Stage")
    Stage Stage,
    @JsonProperty("evidence")
    String evidence,
    @JsonProperty("note")
    String note
  ) throws ChaincodeException {
    super(DocumentType.CONDITION, primaryIdentifier, dataHash, modifiedBy);


    this.identifier = identifier;
    if (clinicalStatus == null) {
      throw new ChaincodeException("Missing field - Condition.clinicalStatus");
    }

    this.clinicalStatus = clinicalStatus;
    if (verificationStatus == null) {
      throw new ChaincodeException("Missing field - Condition.verificationStatus");
    }

    this.verificationStatus = verificationStatus;
    if (severity == null) {
      throw new ChaincodeException("Missing field - Condition.severity");
    }

    this.severity = severity;
    if (category == null) {
      throw new ChaincodeException("Missing field - Condition.category");
    }

    this.category = category;

    this.code = code;

    this.bodySite = bodySite;

    this.subject = subject;

    this.encounter = encounter;

    this.onset = onset;

    this.abatement = abatement;

    this.recordedDate = recordedDate;

    this.participant = participant;

    this.Stage = Stage;

    this.evidence = evidence;

    this.note = note;
  }

  private Condition() throws ChaincodeException {
    super(DocumentType.CONDITION, "ident", "hash", "url://updatedAt");

    this.identifier = null;
    this.clinicalStatus = "active";
    this.verificationStatus = "unconfirmed";
    this.severity = "severe";
    this.category = "problem-list-item";
    this.code = null;
    this.bodySite = null;
    this.subject = null;
    this.encounter = null;
    this.onset = null;
    this.abatement = null;
    this.recordedDate = null;
    this.participant = null;
    this.Stage = null;
    this.evidence = null;
    this.note = null;
  }

  public static String getTemplate() throws ChaincodeException {
    return (new Genson()).serialize(new Condition());
  }
}
