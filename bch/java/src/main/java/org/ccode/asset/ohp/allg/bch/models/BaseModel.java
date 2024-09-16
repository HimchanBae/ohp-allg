package org.ccode.asset.ohp.allg.bch.models;

import org.hyperledger.fabric.contract.annotation.Property;
import org.hyperledger.fabric.shim.ChaincodeException;

public abstract class BaseModel {
    @Property
    protected final DocumentType documentType;

    @Property
    protected final String primaryIdentifier;

    @Property
    protected final String dataHash;

    public DocumentType getDocumentType() {
        return documentType;
    }

    public String getPrimaryIdentifier() {
        return primaryIdentifier;
    }

    public String getDataHash() {
        return dataHash;
    }

    public String getUpdatedByUri() {
        return updatedByUri;
    }

    @Property
    protected final String updatedByUri;

    protected BaseModel(
            DocumentType documentType,
            String primaryIdentifier,
            String dataHash
    ) throws ChaincodeException {
        this(documentType, primaryIdentifier, dataHash, null);
    }

    protected BaseModel(
            DocumentType documentType,
            String primaryIdentifier,
            String dataHash,
            String updatedByUri
    ) throws ChaincodeException {
        if (documentType == null) {
            throw new ChaincodeException("Missing field - documentType");
        }
        this.documentType = documentType;

        if (primaryIdentifier == null) {
            throw new ChaincodeException("Missing field - primaryIdentifier");
        }
        this.primaryIdentifier = primaryIdentifier;

        if (dataHash == null) {
            throw new ChaincodeException("Missing field - dataHash");
        }
        this.dataHash = dataHash;

        this.updatedByUri = updatedByUri;
    }
}