package com.tvexample.programme.integrationTests	;

import java.io.StringWriter;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import com.tvexample.programme.rest.ProgrammeResource;
import com.tvexample.programme.xml.Programme;

public class ClientProgrammeProxy implements ProgrammeResource {

	String APPLICATION_URI = "http://localhost:8182/tv/programmes/";

	ProgrammeResource resource;

	ClientProgrammeProxy(int i) {
		ClientResource cr = new ClientResource(APPLICATION_URI + i);
		resource = cr.wrap(ProgrammeResource.class);
	}

	@Override
	public Programme retrieve() throws ResourceException {
		return resource.retrieve();
	}

	@Override
	public void create(Programme programme) throws ResourceException {
		sendProgramme(Method.POST, programme);
//		resource.create(programme);
	}

	@Override
	public void update(Programme programme) throws ResourceException {
		sendProgramme(Method.PUT, programme);
//		resource.update(programme);
	}

	@Override
	public void remove() throws ResourceException {
		resource.remove();
	}

//	 This method was necessary to overcome what appears to be a bug in Restlet.
//	 When using ClientResource.wrap() to send XML created by JiBX it was sending
//	 a request like this:

//	 POST /tv/programmes/4 HTTP/1.1
//   Transfer-Encoding: chunked
//   Date: Sun, 26 Jun 2011 12:33:20 GMT
//   Content-Type: application/octet-stream; charset=UTF-8
//   Accept: */*
//	 Host: localhost:8181
//	 User-Agent: Restlet-Framework/2.1m3
//
//	 0045
//	 <programme><id>4</id><title>Strictly Come Dancing</title></programme>
//	 0000

//	 so getting a response like this

//	 HTTP/1.1 415 Unsupported Media Type
//	 Content-Type: text/html; charset=UTF-8
//	 Date: Sun, 26 Jun 2011 12:33:20 GMT
//	 Accept-Ranges: bytes
//	 Server: Restlet-Framework/2.1m3
//	 Content-Length: 554
//
//	 <html>
//	 <head>
//   	<title>Status page</title>
//	 </head>
//	 <body style="font-family: sans-serif;">
//	 <p style="font-size: 1.2em;font-weight: bold;margin: 1em 0px;">Unsupported Media Type</p>
//	 <p>The server is refusing to service the request because the entity of the request is in a format not supported by the requested resource for the requested method</p>
//	 <p>You can get technical details <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16">here</a>.<br>
//	 Please continue your visit at our <a href="/">home page</a>.
//	 </p>
//	 </body>
//	 </html>
	
//   Instead the client needs to send requests like this
	
//   PUT /tv/programmes/2 HTTP/1.1
//	 Date: Sun, 26 Jun 2011 12:37:32 GMT
//	 Content-Length: 96
//	 Content-Type: application/xml; charset=UTF-8
//	 Accept: */*
//	 Host: localhost:8181
//   User-Agent: Restlet-Framework/2.1m3
//
//   <?xml version="1.0" encoding="UTF-8"?><programme><id>2</id><title>Doctor Who</title></programme>
		
	private void sendProgramme(Method method, Programme p) throws ResourceException {
		try {
			IBindingFactory bfact = BindingDirectory.getFactory(Programme.class);
			IMarshallingContext mctx = bfact.createMarshallingContext();
			StringWriter sw = new StringWriter();
			mctx.marshalDocument(p, "UTF-8", null, sw);
			StringRepresentation rep = new StringRepresentation(sw.toString(), MediaType.APPLICATION_XML);
			Client cl = new Client(Protocol.HTTP);
			Request request = new Request(method, APPLICATION_URI + p.getId(), rep);
			Response response = cl.handle(request);
			Status status = response.getStatus(); 
			if (status.equals(Status.CLIENT_ERROR_BAD_REQUEST) ||
					status.equals(Status.CLIENT_ERROR_NOT_FOUND)) {
				throw new ResourceException(response.getStatus());
			}
		} catch (JiBXException je) {
			je.printStackTrace();
			System.exit(0);
		}
	}
}
