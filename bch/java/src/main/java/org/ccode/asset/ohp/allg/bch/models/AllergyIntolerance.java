package org.ccode.asset.ohp.allg.bch.models;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.hyperledger.fabric.shim.ChaincodeException;

public class AllergyIntolerance extends BaseModel {

    @Property()
    private final String[] identifier;
    @Property()
    @Required()
    private final String[] clinicalStatus;
    @Property()
    @Required()
    private final String verificationStatus;
    @Property()
    private final String[] type; //array of strings specifying underlying mechanisms for intolerance
                                //allergy | intolerance - mechanism | Not known
    @Property()
    @Required()
    private final String category; //category of allergy: food | medication | environment | biologic
    @Property()
    @Required()
    private final String criticality; // low | high | unable-to-process
    @Property()
    private final String code; //code for type of allergyintolerance
    @Property()
    private final Reference patient;
    @Property()
    private final Reference encounter;
    @Property()
    private final Onset onset;
    @Property()
    private final String recordedDate;
    @Property()
    private final String lastOccurence;
    @Property()
    private final Participant[] participant;
    @Property()
    private final Reaction reaction;

    public String[] getIdentifier() {
        return identifier;
    }
    public String[] getClinicalStatus() {
        return clinicalStatus;
    }
    public String getVerificationStatus() {
        return verificationStatus;
    }
    public String[] getType() {
        return type;
    }
    public String getCategory() {
        return category;
    }
    public String getCriticality() {
        return criticality;
    }
    public String getCode() {
        return code;
    }
    public Reference getPatient(){
        return patient;
    }
    public Reference getEncounter() {
        return encounter;
    }
    public Onset getOnset() {
        return onset;
    }
    public String getRecordedDate() {
        return recordedDate;
    }
    public String getLastOccurence() {
        return lastOccurence;
    }
    public Participant[] getParticipant() {
        return participant;
    }
    public Reaction getReaction() {
        return reaction;
    }

    public AllergyIntolerance(
            @JsonProperty("primaryIdentifier")
            String primaryIdentifier,
            @JsonProperty("dataHash")
            String dataHash,
            @JsonProperty("modifiedBy")
            String modifiedBy,

            @JsonProperty("identifier")
            String[] identifier,
            @JsonProperty("clinicalStatus")
            String[] clinicalStatus,
            @JsonProperty("verificationStatus")
            String verificationStatus,
            @JsonProperty("type")
            String[] type,
            @JsonProperty("category")
            String category,
            @JsonProperty("criticality")
            String criticality,
            @JsonProperty("code")
            String code,
            @JsonProperty("patient")
            Reference patient,
            @JsonProperty("encounter")
            Reference encounter,
            @JsonProperty("onset")
            Onset onset,
            @JsonProperty("recordedDate")
            String recordedDate,
            @JsonProperty("participant")
            Participant[] participant,
            @JsonProperty("lastOccurence")
            String lastOccurence,
            @JsonProperty("reaction")
            Reaction reaction
    ) throws ChaincodeException {
        super(DocumentType.ALLERGYINTOLERANCE, primaryIdentifier, dataHash, modifiedBy);

        this.identifier = identifier;

        if (clinicalStatus == null) {
            throw new ChaincodeException("Missing field - Allergy.clinicalStatus");
        }
        this.clinicalStatus = clinicalStatus;

        if (verificationStatus == null) {
            throw new ChaincodeException("Missing field - Allergy.verificationStatus");
        }
        this.verificationStatus = verificationStatus;

        this.type = type;

        if (category == null) {
            throw new ChaincodeException("Missing field - Allergy.category");
        }
        this.category = category;

        if (criticality == null) {
            throw new ChaincodeException("Missing field - Allergy.criticality");
        }

        this.criticality = criticality;

        this.code = code;

        this.patient = patient;

        this.encounter = encounter;

        this.onset = onset;

        this.recordedDate = recordedDate;

        this.participant = participant;

        this.lastOccurence = lastOccurence;

        this.reaction = reaction;
    }

    private AllergyIntolerance() throws ChaincodeException {
        super(DocumentType.ALLERGYINTOLERANCE, "ident", "hash", "url://updatedAt");

        this.identifier = null;
        this.clinicalStatus = null;
        this.verificationStatus = null;
        this.type = null;
        this.category = null;
        this.criticality = null;
        this.code = null;
        this.patient = null;
        this.encounter = null;
        this.onset = null;
        this.recordedDate = null;
        this.participant = null;
        this.lastOccurence = null;
        this.reaction = null;
    }

    public static String getTemplate() throws ChaincodeException {
        return (new Genson()).serialize(new AllergyIntolerance());
    }
}
