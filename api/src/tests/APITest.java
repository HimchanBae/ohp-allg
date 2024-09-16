import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.ibm.fhir.ohp.api.allg.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class APITest {

    private static final String API_STATUS_URI = "http://localhost:9080/allg/api";
    private static final String API_PING_URI = "http://localhost:9080/allg/api/ping";
    private static final String RESOURCE_URI = "http://localhost:9080/allg/api/Patient";

    @Test
    public void testStatus() {
        System.out.println("Testing if the API is up and running");

        HttpResponse<String> response = sendRequest(API_STATUS_URI, "GET");

        assertNotEquals("Failed to send request", null, response);
        assertEquals("Wrong response status code", 200, response.statusCode());
        assertEquals("Wrong response body",
                "API has successfully connected to the database and is online\n",
                response.body());
    }

    @Test
    public void testPing() {
        System.out.println("Testing if the API can be pinged");

        HttpResponse<String> response = sendRequest(API_PING_URI, "GET");

        assertNotEquals("Failed to send request", null, response);
        assertEquals("Wrong response status code", 200, response.statusCode());
        assertEquals("Wrong response body", "API online\n", response.body());
    }

    @Test
    public void testPostAndGetResource() throws Exception {
        System.out.println("Testing if the API can view and create resources correctly");

        // Read test resource
        JSONObject patient = readTestFile("patient1.json");
        assertNotEquals("Failed to read test file", null, patient);

        // Delete all similar resources
        deleteSimilar(RESOURCE_URI, patient);

        // View resources before adding test resource
        HttpResponse<String> response = sendRequest(RESOURCE_URI, "GET");
        assertNotEquals("Failed to send GET request", null, response);
        assertEquals("Wrong response status code", 200, response.statusCode());
        JSONObject before = new JSONObject(response.body());
        // Total number of matched entries before adding should be 0
        assertEquals("Failed to view resources correctly", 0, (int) before.opt("total"));

        // Add test resource
        response = sendRequest(RESOURCE_URI, "POST", patient.toString());
        assertNotEquals("Failed to send POST request", null, response);
        assertEquals("Wrong response status code", 201, response.statusCode());

        // View resources after adding test resource
        response = sendRequest(RESOURCE_URI, "GET");
        assertNotEquals("Failed to send GET request", null, response);
        assertEquals("Wrong response status code", 200, response.statusCode());
        JSONObject after = new JSONObject(response.body());
        // Total number of matched entries after adding should be 1
        assertEquals("Failed to view added resource", 1, (int) after.opt("total"));

        // Extract resource from the response entry field
        JSONObject resource = getEntryResource(after.optString("entry"), 0);
        // Remove keys added by the server
        resource.remove("id");
        resource.remove("meta");
        assertEquals("Failed to create or view resource correctly", true, patient.similar(resource));
    }

    @Test
    public void testGetResourceId() {
        System.out.println("Testing if the API can view resource by id correctly");

        JSONObject patient = readTestFile("patient1.json");
        deleteSimilar(RESOURCE_URI, patient);
        HttpResponse<String> response = sendRequest(RESOURCE_URI, "POST", patient.toString());

        // Get added resource id through normal GET request
        response = sendRequest(RESOURCE_URI, "GET");
        JSONObject responseBody = new JSONObject(response.body());
        JSONObject resource = getEntryResource(responseBody.optString("entry"), 0);
        String id = resource.optString("id");

        // View test resource by id
        response = sendRequest(RESOURCE_URI + "/" + id, "GET");
        assertNotEquals("Failed to send GET request", null, response);
        assertEquals("Wrong response status code", 200, response.statusCode());
        // Response body should be the resource
        resource = new JSONObject(response.body());
        // Remove keys added by the server
        resource.remove("id");
        resource.remove("meta");

        assertEquals("Failed to create or view resource correctly", true, patient.similar(resource));
    }

    @Test
    public void testPatchAndGetResourceVid() throws IOException, InterruptedException, Exception {
        System.out.println("Testing if the API can patch resource and view resource by vid correctly");

        JSONObject patient = readTestFile("patient1.json");
        deleteSimilar(RESOURCE_URI, patient);
        HttpResponse<String> response = sendRequest(RESOURCE_URI, "POST", patient.toString());

        response = sendRequest(RESOURCE_URI, "GET");
        JSONObject responseBody = new JSONObject(response.body());
        JSONObject resource = getEntryResource(responseBody.optString("entry"), 0);
        String id = resource.optString("id");

        // Append id to test resource
        patient.put("id", id);
        // Create a version two of test resource
        JSONObject patientV2 = new JSONObject(patient.toString());
        patientV2.put("gender", "female");

        // Update to version 2
        response = sendRequest(RESOURCE_URI, "PUT", patientV2.toString());
        assertNotEquals("Failed to send PUT request", null, response);
        assertEquals("Wrong response status code", 200, response.statusCode());

        // Get version 1
        response = sendRequest(RESOURCE_URI + "/" + id + "/_history/1", "GET");
        assertNotEquals("Failed to send GET request", null, response);
        assertEquals("Wrong response status code", 200, response.statusCode());
        // Response body should be the resource
        resource = new JSONObject(response.body());
        // Remove unnecessary keys
        resource.remove("meta");
        assertEquals("Failed to view resourceby vid correctly", true, patient.similar(resource));

        // Get version 2
        response = sendRequest(RESOURCE_URI + "/" + id + "/_history/2", "GET");
        assertNotEquals("Failed to send GET request", null, response);
        assertEquals("Wrong response status code", 200, response.statusCode());
        // Response body should be the resource
        resource = new JSONObject(response.body());
        // Remove unnecessary keys
        resource.remove("meta");
        assertEquals("Failed to patch resource or view resourceby vid correctly", true, patientV2.similar(resource));
    }

    @Test
    public void testDeleteResource() {
        System.out.println("Testing if the API can delete resource correctly");

        JSONObject patient = readTestFile("patient1.json");
        deleteSimilar(RESOURCE_URI, patient);
        HttpResponse<String> response = sendRequest(RESOURCE_URI, "POST", patient.toString());

        response = sendRequest(RESOURCE_URI, "GET");
        JSONObject responseBody = new JSONObject(response.body());
        JSONObject resource = getEntryResource(responseBody.optString("entry"), 0);
        String id = resource.optString("id");

        // Delete added resource
        response = sendRequest(RESOURCE_URI + "/" + id, "DELETE");
        assertNotEquals("Failed to send DELETE request", null, response);
        assertEquals("Wrong response status code", 200, response.statusCode());

        response = sendRequest(RESOURCE_URI, "GET");
        responseBody = new JSONObject(response.body());
        // Total number of matched entries after deleting should be 0
        assertEquals("Failed to view added resource", 0, (int) responseBody.opt("total"));
    }

    // ----------------------------- Helper methods -----------------------------

    // Read a local json test file and create JSONObject
    private JSONObject readTestFile(String fileName) {
        try {
            InputStream inputStream = Utility.resolveFileLocation(fileName);
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            JSONTokener jsonTokener = new JSONTokener(reader);
            return new JSONObject(jsonTokener);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Create a query parameter string corresponding to the input JSONObject
    private String createQueryParameters(JSONObject jsonObject) {
        if (jsonObject == null) {
            return "";
        }

        String queryParameters = "?";
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (key == "ResourceType") {
                continue;
            }
            try {
                queryParameters += "&" + key.toLowerCase() + URLEncoder.encode(jsonObject.opt(key).toString(), "UTF8");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return queryParameters;
    }

    // Delete all entries similar to input JSONObject
    private void deleteSimilar(String uri, JSONObject jsonObject) {
        // Get similar entries
        String queryParameters = createQueryParameters(jsonObject);
        HttpResponse<String> response = sendRequest(RESOURCE_URI + queryParameters, "GET");
        JSONObject responsObject = new JSONObject(response.body());

        // Delete similar entries
        while ((int) responsObject.opt("total") != 0) {
            JSONArray ja = new JSONArray(responsObject.optString("entry"));
            for (int i = 0; i < ja.length(); i++) {
                JSONObject entry = ja.getJSONObject(i);
                JSONObject resource = new JSONObject(entry.optString("resource"));
                String id = resource.optString("id");
                sendRequest(RESOURCE_URI + "/" + id, "DELETE");
            }
            response = sendRequest(RESOURCE_URI + queryParameters, "GET");
            responsObject = new JSONObject(response.body());
        }
    }

    // Delete all entries of a type
    private void deleteSimilar(String uri) {
        deleteSimilar(uri, null);
    }

    // Get a resource from an entry array returned after a GET request
    private JSONObject getEntryResource(String entry, int index) {
        JSONArray entryArray = new JSONArray(entry);
        JSONObject entryObject = entryArray.getJSONObject(index);
        JSONObject resource = new JSONObject(entryObject.optString("resource"));

        // Remove keys added by the server
        return resource;
    }

    // Create basic authentication header
    private static final String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    // Send request with json body
    private HttpResponse<String> sendRequest(String uri, String requestType, String body) {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(
                URI.create(uri))
                .method(requestType, HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .header("Authorization", getBasicAuthenticationHeader("fhiruser", "change-password"))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        try {
            return client.send(request, BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private HttpResponse<String> sendRequest(String uri, String requestType) {
        return sendRequest(uri, requestType, "");
    }
}