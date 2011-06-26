package com.tvexample.programme.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.restlet.Application;
import org.restlet.resource.ResourceException;

import com.tvexample.programme.xml.Programme;

public class ProgrammeResourceImplTest {
	
	static ProgrammeStore store = new ProgrammeStore();
	
	protected Programme programme = new Programme();
	
	protected Programme newProgramme = new Programme();
	
	protected void createProgrammes() {
		programme.setId(4);
		programme.setTitle("Strictly Come Dancing");
		
		newProgramme.setId(2);
		newProgramme.setTitle("Doctor Who");
	}

	@Before
	public void setUp() throws Exception {
		createProgrammes();
		TVProgrammeMetadataServer mockServer = mock(TVProgrammeMetadataServer.class);
		when(mockServer.getProgrammeStore()).thenReturn(store);
		Application.setCurrent(mockServer);
	}
	
	protected ProgrammeResource programmeResource(int i) {
		return new TestProgrammeProxy(i);
	}
		
	@Test
	public void testCreate() throws Exception {
		programmeResource(4).create(programme);
		Programme result = programmeResource(4).retrieve();
		assertEquals(programme.getId(), result.getId());
		assertEquals(programme.getTitle(), result.getTitle());
	}
	
	@Test(expected=ResourceException.class) 
	public void testCreateException() throws Exception {
		programmeResource(4).create(programme);
	}

	@Test(expected=ResourceException.class)
	public void retrieveResourceException() throws Exception {
		programmeResource(1).retrieve();
	}

	@Test
	public void testUpdate() throws Exception {
		String description = "It's a dancing extravaganza tonight";
		programme.setDescription(description);
		programmeResource(4).update(programme);
		assertEquals(programme.getDescription(), programmeResource(4).retrieve().getDescription());
	}
	
	@Test(expected=ResourceException.class) 
	public void testUpdateException() throws Exception {
		programmeResource(2).update(newProgramme);
	}

	@Test
	public void testRemove() throws Exception {
		programmeResource(4).remove();
	}
	
	@Test(expected=ResourceException.class)
	public void testRemoveSuccessful() throws Exception {
		programmeResource(4).retrieve();
	}
}
