package org.ccode.asset.ohp.allg.bch.models;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.hyperledger.fabric.shim.ChaincodeException;
import java.time.*;
@Datatype()
public class DatePeriod {

    @Property()
    private final LocalDate  start; // as yyyy-MM-dd
    @Property()
    private final LocalDate end;
    @Property
    private final Period DaysInBetween;
    public DatePeriod(
            @JsonProperty("start")
            String  start,
            @JsonProperty("end")
            String  end
    ) {
        this.start = LocalDate.parse(start);
        this.end = LocalDate.parse(end);

        DaysInBetween = java.time.Period.between(this.start,this.end);
    }

}
