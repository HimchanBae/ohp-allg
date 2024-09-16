package org.ccode.asset.ohp.allg.bch.models;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.hyperledger.fabric.shim.ChaincodeException;
import java.time.Period;

@Datatype()
public class Onset{

    @Property()
    private final String onsetDateTime;
    @Property()
    private final Integer onsetAge;
    @Property()
    private final DatePeriod onsetPeriod;
    @Property()
    private final String onsetRange;
    @Property()
    private final String onsetString;

    public Onset(
            @JsonProperty("onsetDateTime")
            String onsetDateTime,
            @JsonProperty("onsetAge")
            Integer onsetAge,
            @JsonProperty("onsetPeriod")
            DatePeriod onsetPeriod,
            @JsonProperty("onsetRange")
            String onsetRange,
            @JsonProperty("onsetString")
            String onsetString
    ) {
        this.onsetDateTime  = onsetDateTime;
        this.onsetAge = onsetAge;
        this.onsetPeriod = onsetPeriod;
        this.onsetRange = onsetRange;
        this.onsetString = onsetString;

    }
}
