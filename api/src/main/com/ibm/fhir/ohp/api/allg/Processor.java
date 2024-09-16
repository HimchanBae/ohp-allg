package com.ibm.fhir.ohp.api.allg;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import javax.ws.rs.core.Response;

import com.ibm.fhir.client.FHIRClient;
import com.ibm.fhir.client.FHIRParameters;
import com.ibm.fhir.model.resource.Resource;

public class Processor {
	private FHIRClient fhirclient;
	private BlockchainClient blockchainClient;

	public Processor() {
		this.fhirclient = FHIRServerClient.getClient();
		this.blockchainClient = BlockchainClient.getInstance();
	}

	public Processor(FHIRClient client) {
		this.fhirclient = client;
		this.blockchainClient = BlockchainClient.getInstance();
	}

	/**
	 * Get a resource by id
	 * 
	 * @param resourceType
	 * @param id
	 * @return
	 */
	public Response getResourceById(String resourceType, String id) {
		try {
			return fhirclient.read(resourceType, id).getResponse();
		} catch (Exception e) {
			return Response.status(BAD_REQUEST.getStatusCode()).type(APPLICATION_JSON).entity(e.getMessage()).build();
		}
	}

	/**
	 * Get a specific version of a resource by id and versionId
	 * 
	 * @param resourceType
	 * @param id
	 * @param versionId
	 * @return
	 */
	public Response getResourceByVId(String resourceType, String id, String versionId) {
		try {
			return fhirclient.vread(resourceType, id, versionId).getResponse();
		} catch (Exception e) {
			return Response.status(BAD_REQUEST.getStatusCode()).type(APPLICATION_JSON).entity(e.getMessage()).build();
		}
	}

	/**
	 * Get resources by parameters
	 * 
	 * @param resourceType
	 * @param parameters
	 * @return
	 */
	public Response getResourcesByParams(String resourceType, FHIRParameters parameters) {
		try {
			return fhirclient.search(resourceType, parameters).getResponse();
		} catch (Exception e) {
			return Response.status(BAD_REQUEST.getStatusCode()).type(APPLICATION_JSON).entity(e.getMessage()).build();
		}
	}

	/**
	 * Create a resource
	 * 
	 * @param resource
	 * @return Response
	 */
	public Response createResource(Resource resource, String resourceType, String body) {
		try {
			Response fhirResponse = fhirclient.create(resource).getResponse();
			System.out.println("Created a resource");
			String transactionResult = blockchainClient.submitTransaction("create", resourceType, body);
			System.out.println(transactionResult);
			return fhirResponse;
		} catch (Exception e) {
			return Response.status(BAD_REQUEST.getStatusCode()).type(APPLICATION_JSON).entity(e.getMessage()).build();
		}
	}

	public Response updateResource(Resource resource, String resourceType, String body) {
		try {
			Response fhirResponse = fhirclient.update(resource).getResponse();
			System.out.println("Updated a resource:");
			String transactionResult = blockchainClient.submitTransaction("update", resourceType, body);
			System.out.println(transactionResult);
			return fhirResponse;
		} catch (Exception e) {
			return Response.status(BAD_REQUEST.getStatusCode()).type(APPLICATION_JSON).entity(e.getMessage()).build();
		}
	}

	public Response deleteResourceById(String resourceType, String id) {
		try {
			Response fhirResponse = fhirclient.delete(resourceType, id).getResponse();
			System.out.println("Updated a resource with id " + id + " :");
			String transactionResult = blockchainClient.submitTransaction("delete", resourceType, id);
			System.out.println(transactionResult);
			return fhirResponse;
		} catch (Exception e) {
			return Response.status(BAD_REQUEST.getStatusCode()).type(APPLICATION_JSON).entity(e.getMessage()).build();
		}
	}

	public Response getResourceHistoryById(String resourceType, String id, FHIRParameters parameters) {
		try {
			return fhirclient.history(resourceType, id, parameters).getResponse();
		} catch (Exception e) {
			return Response.status(BAD_REQUEST.getStatusCode()).type(APPLICATION_JSON).entity(e.getMessage()).build();
		}
	}

	public Response getHistory(FHIRParameters parameters) {
		try {
			return fhirclient.history(parameters).getResponse();
		} catch (Exception e) {
			return Response.status(BAD_REQUEST.getStatusCode()).type(APPLICATION_JSON).entity(e.getMessage()).build();
		}
	}
}