package org.ccode.asset.ohp.allg.bch.models;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.Property;

public class Reference {

    public String getType() {
        return type;
    }

    public String getReference() {
        return reference;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Property
    private final String type;

    @Property
    private final String reference;

    @Property
    private final String identifier;

    public Reference(
            @JsonProperty("type")
            String type,
            @JsonProperty("reference")
            String reference,
            @JsonProperty("identifier")
            String identifier
    ) {
        this.type = type;
        this.reference = reference;
        this.identifier = identifier;
    }
}