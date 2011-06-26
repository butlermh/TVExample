package com.tvexample.programme.rest;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.tvexample.programme.xml.Programme;

/**
 * A Programme as a REST resource implementation.
 */
public class ProgrammeResourceImpl extends ServerResource implements ProgrammeResource {

	private int id;

	@Override
	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);
		id = Integer.parseInt((String) request.getAttributes().get("id"));
	}

	@Override
	@Get
	public Programme retrieve() throws ResourceException {
		if (getProgrammeStore().containsKey(id)) {
			return getProgrammeStore().get(id);
		} else {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, String.format(
					"Programme with ID %d does not exist", id));
		}
	}

	@Override
	@Post
	public void create(Programme programme) throws ResourceException {
		if (getProgrammeStore().containsKey(id)) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, String.format(
					"Programme with ID %d already exists", id));
		}
		getProgrammeStore().add(programme);
	}

	@Override
	@Put
	public void update(Programme programme) throws ResourceException {
		if (getProgrammeStore().containsKey(id)) {
			getProgrammeStore().add(programme);
		} else {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, String.format(
					"Programme with ID %d does not exist", id));
		}
	}

	@Override
	@Delete
	public void remove() throws ResourceException {
		if (getProgrammeStore().containsKey(id)) {
			getProgrammeStore().remove(id);
		} else {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, String.format(
					"Programme with ID %d does not exist", id));
		}
	}

	/**
	 * Get the ProgrammeStore.
	 * 
	 * @return The ProgrammeStore holding Programme metadata.
	 */
	ProgrammeStore getProgrammeStore() {
		return ((TVProgrammeMetadataServer) getApplication()).getProgrammeStore();
	}
}