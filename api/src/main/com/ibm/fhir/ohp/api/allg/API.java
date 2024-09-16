package com.ibm.fhir.ohp.api.allg;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.ibm.fhir.model.parser.exception.FHIRParserException;
import com.ibm.fhir.model.resource.Resource;

import static com.ibm.fhir.ohp.api.allg.Utility.getFHIRParamsFromUriInfo;

@Path("/api")
public class API {
	private Processor processor;

	public API() {
		processor = new Processor();
	}

	@GET
	@Path("/")
	public Response status() {
		if (getProcessor() == null) {
			return Response.serverError().entity("API failed to connect to the database, and is offline\n").build();
		} else {
			return Response.ok().entity("API has successfully connected to the database and is online\n").build();
		}
	}

	private Processor getProcessor() {
		return processor;
	}

	@GET
	@Path("/ping")
	public Response ping() {
		return Response.ok().entity("API online\n").build();
	}

	@GET
	@Path("/{resource_type}/{id}")
	public Response getResourceById(@PathParam("resource_type") String resourceType, @PathParam("id") String id) {
		return getProcessor().getResourceById(resourceType, id);
	}

	@GET
	@Path("/{resource_type}/{id}/_history/{version_id}")
	public Response getResourceByVId(@PathParam("resource_type") String resourceType, @PathParam("id") String id,
			@PathParam("version_id") String versionId) {
		return getProcessor().getResourceByVId(resourceType, id, versionId);
	}

	@GET
	@Path("/{resource_type}")
	public Response getResourcesByParams(@PathParam("resource_type") String resourceType, @Context UriInfo uriInfo) {
		return getProcessor().getResourcesByParams(resourceType, getFHIRParamsFromUriInfo(uriInfo));
	}

	@POST
	@Path("/{resource_type}")
	public Response createResourceByJson(@PathParam("resource_type") String resourceType, String json) {
		Resource resource;
		try {
			resource = Utility.readJsonStringResource(json);
		} catch (FHIRParserException e) {
			return Response.status(Status.BAD_REQUEST.getStatusCode()).type(MediaType.TEXT_PLAIN)
					.entity(e.getMessage() + '\n').build();
		}
		return getProcessor().createResource(resource, resourceType, json);
	}

	@PUT
	@Path("/{resource_type}")
	public Response updateResourceByJson(@PathParam("resource_type") String resourceType, String json) {
		Resource resource;
		try {
			resource = Utility.readJsonStringResource(json);
		} catch (FHIRParserException e) {
			return Response.status(Status.BAD_REQUEST.getStatusCode()).type(MediaType.TEXT_PLAIN)
					.entity(e.getMessage() + '\n').build();
		}
		return getProcessor().updateResource(resource, resourceType, json);
	}

	@DELETE
	@Path("/{resource_type}/{id}")
	public Response deleteResourceById(@PathParam("resource_type") String resourceType, @PathParam("id") String id) {
		return getProcessor().deleteResourceById(resourceType, id);
	}

	@GET
	@Path("/{resource_type}/{id}/_history")
	public Response getResourceHistoryById(@PathParam("resource_type") String resourceType, @PathParam("id") String id, @Context UriInfo uriInfo) {
		return getProcessor().getResourceHistoryById(resourceType, id, getFHIRParamsFromUriInfo(uriInfo));
	}

	@GET
	@Path("/_history")
	public Response getHistory(@Context UriInfo uriInfo) {
		return getProcessor().getHistory(getFHIRParamsFromUriInfo(uriInfo));
	}
}