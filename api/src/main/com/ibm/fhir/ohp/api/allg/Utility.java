package com.ibm.fhir.ohp.api.allg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.core.UriInfo;

import com.ibm.fhir.client.FHIRParameters;
import com.ibm.fhir.model.format.Format;
import com.ibm.fhir.model.parser.FHIRParser;
import com.ibm.fhir.model.parser.exception.FHIRParserException;
import com.ibm.fhir.model.resource.Resource;

public class Utility {
    public static InputStream resolveFileLocation(String fileName) throws Exception {
        System.out.println("Resolving file loc: " + fileName);
        var file = new File(fileName);
        if (file.exists()) {
            System.out.println("file found");
            return new FileInputStream(file);
        }

        var resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

        if (resourceStream != null) {
            return resourceStream;
        }

        throw new FileNotFoundException("File '" + fileName + "' was not found.");
    }

    // Can read a json file, and return a FHIR object from the json file.
    public static <T extends Resource> T readLocalResource(String fileName) throws Exception {
        try (Reader reader = new InputStreamReader(resolveFileLocation(fileName), StandardCharsets.UTF_8)) {
            return FHIRParser.parser(Format.JSON).parse(reader);
        }
    }

    public static <T extends Resource> T readJsonStringResource(String json) throws FHIRParserException {
        return FHIRParser.parser(Format.JSON).parse(new StringReader(json));
    }

    public static Path getPathForLocalFile(String fileName) throws URISyntaxException {
        var resourceStream = Thread.currentThread().getContextClassLoader().getResource(fileName);
        System.out.println("Getting path for local file: " + resourceStream);
        if ( resourceStream == null ) {
            System.out.println("Resource file not found: " + fileName);
            return null;
        } else {
            System.out.println("Path: " + Path.of(resourceStream.toURI()));
        }
        return Path.of(resourceStream.toURI());
    }

    public static String getLocationLogicalId(String location) {
        var tokens = location.split("/");
        if (tokens.length < 4) {
            return null;
        }
        return tokens[tokens.length - 3];
    }

    public static FHIRParameters getFHIRParamsFromUriInfo(UriInfo uriInfo) {
		FHIRParameters fparams = new FHIRParameters();
		// move parameters in uriInfo into fparams
		Iterator<Entry<String, List<String>>> mapIter = uriInfo.getQueryParameters().entrySet().iterator();
        while (mapIter.hasNext()) {
            Entry<String, List<String>> entry = mapIter.next();
            String key = entry.getKey();
            List<String> values = entry.getValue();
            for (int i = 0; i < values.size(); i++) {
                fparams.addMultivaluedParameter(key, values.get(i));
            }
        }
        return fparams;
    }
}
