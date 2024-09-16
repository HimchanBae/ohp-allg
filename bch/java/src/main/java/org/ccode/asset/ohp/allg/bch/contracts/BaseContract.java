package org.ccode.asset.ohp.allg.bch.contracts;

import org.ccode.asset.ohp.allg.bch.models.BaseModel;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import com.owlike.genson.JsonBindingException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseContract<Model extends BaseModel> implements ContractInterface {

    protected Class<Model> getModelClass() {
        return ((Class<Model>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    protected String getModelName() {
        return getModelClass().getSimpleName();
    }

    protected final Genson genson = (new GensonBuilder())
            .setSkipNull(false)
            .create();

    /**
     * Retrieves a car with the specified key from the ledger.
     *
     * @param ctx               the transaction context
     * @param primaryIdentifier the key
     * @return the Car found on the ledger if there was one
     */
    @Transaction()
    public String find(final Context ctx, final String primaryIdentifier) {
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(primaryIdentifier);

        if (state.isEmpty()) {
            String errorMessage = String.format(getModelName() + " %s does not exist",
                    primaryIdentifier);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, "Missing model - " + getModelName());
        }

        return genson.serialize(genson.deserialize(state, getModelClass()));
    }

    @Transaction()
    public String getTemplate(final Context ctx)
            throws ChaincodeException {
        try {
            return (String) getModelClass().getMethod("getTemplate").invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ChaincodeException("An unknown error has occurred of type " + e.getClass().getSimpleName());
        }
    }

    @Transaction()
    public String create(final Context ctx, String data) {
        ChaincodeStub stub = ctx.getStub();

        Model model;
        try {
            model = genson.deserialize(data, getModelClass());
        } catch (JsonBindingException e) {
            throw new ChaincodeException("Please enter a valid " + getModelName() + " JSON string");
        }

        String state = stub.getStringState(model.getPrimaryIdentifier());
        if (!state.isEmpty()) {
            String errorMessage = String.format(getModelName() + " %s already exists",
                    model.getPrimaryIdentifier());
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, "Duplicate model - " + getModelName());
        }

        state = genson.serialize(model);
        stub.putStringState(model.getPrimaryIdentifier(), state);

        return state;
    }

    @Transaction
    public String query(final Context ctx, String selector)
    {
        ChaincodeStub stub = ctx.getStub();

        Map<String, Object> queryMap = new HashMap<String, Object>() {{
            put("selector", genson.deserialize(selector, HashMap.class));
        }};

        String queryString = genson.serialize(queryMap);

        List<Model> queryResults = new ArrayList<>();
        try (QueryResultsIterator<KeyValue> results = stub.getQueryResult(queryString)) {
            for (KeyValue result : results) {
                if (result.getStringValue() == null || result.getStringValue().length() == 0) {
                    System.err.printf("Invalid Invalid " + getModelName() + " JSON json: %s\n", result.getStringValue());
                    continue;
                }
                Model model = genson.deserialize(result.getStringValue(), getModelClass());
                queryResults.add(model);
                System.out.println("QueryResult: " + model.toString());
            }
        } catch (Exception e) {
            throw new ChaincodeException(e.getClass() + ": " + e.getMessage());
        }
        return genson.serialize(queryResults.toArray());
       // return genson.serialize(queryResults.toArray((Model[]) Array.newInstance(getModelClass())));
    }

    @Transaction
    public String ping(final Context ctx) {
        return "Pong! From " + getModelName();
    }
}