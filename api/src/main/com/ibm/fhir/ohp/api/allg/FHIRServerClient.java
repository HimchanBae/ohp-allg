package com.ibm.fhir.ohp.api.allg;

import com.ibm.fhir.client.FHIRClient;
import com.ibm.fhir.client.FHIRClientFactory;

import java.io.InputStream;
import java.util.Properties;

public class FHIRServerClient {
    private static FHIRClient client;
    private static final String APP_PROP_PATH = "Application.properties";

    public static FHIRClient getClient() {
        if (client != null) {
            return client;
        }

        var props = new Properties();

        try (InputStream is = Utility.resolveFileLocation(APP_PROP_PATH)) {
            props.load(is);
        } catch (Exception e) {
            System.out.println("Failed to find the application properties which are required for connecting to the FHIR, \"" + e.getMessage() + "\"");
        }

        try {
            client = FHIRClientFactory.getClient(props);
        } catch (Exception e) {
            System.out.println("Failed to connect to concrete FHIR implementation, \"" + e.getMessage() + "\"");
        }

        if (client != null) {
            System.out.println(
                    "-----------------------------------------------------------------------------------------");
            System.out.println(
                    "                    Successfully connected to FHIR database                              ");
            System.out.println(
                    "-----------------------------------------------------------------------------------------");
        }
        return client;
    }
}
