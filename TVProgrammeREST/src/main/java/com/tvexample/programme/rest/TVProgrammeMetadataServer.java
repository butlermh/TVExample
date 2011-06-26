package com.tvexample.programme.rest;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import com.tvexample.programme.xml.Programme;

/**
 * Serves Programme metadata.
 */
public class TVProgrammeMetadataServer extends Application {
	
	private ProgrammeStore programmeStore = new ProgrammeStore();
	
	 /**
     * Constructor.
     *
     * @param parentContext - the org.restlet.Context instance
     */
    public TVProgrammeMetadataServer(Context parentContext) {
        super(parentContext);
        addSampleData();
    }
	
	/**
	 * Constructor.
	 */
	TVProgrammeMetadataServer() {
		 super();
		 addSampleData();
	}
	
	/**
	 * Add some sample data.
	 */
	private void addSampleData() {
		// add some sample data for test purposes.
		
		Programme programme = new Programme();
		programme.setId(0);
		programme.setTitle("Merlin");
		programme.setDescription("Fantasy drama based...");
		List<String> sameAs = new ArrayList<String>();
		sameAs.add("http://www.hulu.com/merlin");
		programme.setSameAList(sameAs);
		programme.setUri("http://www.bbc.co.uk/programmes/b00mjlxv");
		programmeStore.add(programme);
		
		getMetadataService().addExtension("xml", 
				  MediaType.APPLICATION_ALL_XML);
	}
	
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/programmes/{id}", ProgrammeResourceImpl.class);
		return router;
	}
	
	public static void main(String[] args) throws Exception {  
	    // Create a new Component.  
	    Component component = new Component();  
	  
	    // Add a new HTTP server listening on port 8182.  
	    component.getServers().add(Protocol.HTTP, 8182);  
	  
	    // Attach the sample application.  
	    component.getDefaultHost().attach("/tv",  
	            new TVProgrammeMetadataServer());  
	  
	    // Start the component.  
	    component.start();  
	}  
	
	/**
	 * @return The Programme metadata store.
	 */
	ProgrammeStore getProgrammeStore() {
		return programmeStore;
	}
}