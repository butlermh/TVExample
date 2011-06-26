package com.tvexample.programme.rest;

import java.util.HashMap;
import java.util.Map;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.ResourceException;

import com.tvexample.programme.xml.Programme;

public class TestProgrammeProxy implements ProgrammeResource {

	ProgrammeResourceImpl resource;

	TestProgrammeProxy(int i) {
		Request request = new Request();
		Map<String, Object> requestAttributes = new HashMap<String, Object>();
		requestAttributes.put("id", String.format("%d", i));
		request.setAttributes(requestAttributes);

		resource = new ProgrammeResourceImpl();
		resource.init(new Context(), request, new Response(request));
	}

	@Override
	public Programme retrieve() throws ResourceException {
		return resource.retrieve();
	}

	@Override
	public void create(Programme programme) throws ResourceException {
		resource.create(programme);
	}

	@Override
	public void update(Programme programme) throws ResourceException {
		resource.update(programme);
	}

	@Override
	public void remove() throws ResourceException {
		resource.remove();
	}

}
