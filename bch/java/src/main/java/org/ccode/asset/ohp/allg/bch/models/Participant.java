package org.ccode.asset.ohp.allg.bch.models;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.hyperledger.fabric.shim.ChaincodeException;

@DataType()
public class Participant {

    public enum ParticipantType {
        Patient, Group, Practitioner, PractitionerRole, CareTeam, RelatedPerson, Device, HealthcareService, Location
    }

    @Property()
    private final ParticipantType[] type;

    @Property()
    private final Reference actor;

    @Property()
    @Required()
    private final String status;

    @Property()
    private final DatePeriod period;

    public ParticipantType[] getType() {
        return type;
    }

    public Reference getActor() {
        return actor;
    }

    public String getStatus() {
        return status;
    }

    public DatePeriod getPeriod() {
        return period;
    }

    public Participant(
            @JsonProperty("type") ParticipantType[] type,
            @JsonProperty("actor") Reference actor,
            @JsonProperty("status") String status,
            @JsonProperty("period") DatePeriod period) throws ChaincodeException {
        this.type = type;
        this.actor = actor;
        if (status == null) {
            throw new ChaincodeException("Missing field - Participant.status");
        }
        this.status = status;
        this.period = period;
    }
}

