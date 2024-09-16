package org.ccode.asset.ohp.allg.bch.models;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.hyperledger.fabric.shim.ChaincodeException;

@Datatype()
public class Reaction {

    @Property()
    private final String substance;
    @Property()
    private final String manifestation;
    @Property()
    private final String description;
    @Property()
    //subject to change
    private final String onset; //DateTime as ISO-8601
    @Property()
    @Required()
    private final String severity;
    @Property()
    private final String exposureRoute;
    @Property()
    private final String note;

    public Reaction(
            @JsonProperty("substance")
            String substance,
            @JsonProperty("manifestation")
            String manifestation,
            @JsonProperty("description")
            String description,
            @JsonProperty("onset")
            String onset,
            @JsonProperty("severity")
            String severity,
            @JsonProperty("exposureRoute")
            String exposureRoute,
            @JsonProperty("note")
            String note
    ) {
        this.substance = substance;
        this.manifestation = manifestation;
        this.description = description;
        this.onset = onset;
        this.severity = severity;
        this.exposureRoute = exposureRoute;
        this.note = note;

    }
}
