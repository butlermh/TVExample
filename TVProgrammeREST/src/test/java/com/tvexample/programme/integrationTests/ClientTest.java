package com.tvexample.programme.integrationTests;

import org.junit.Before;

import com.tvexample.programme.rest.ProgrammeResource;
import com.tvexample.programme.rest.ProgrammeResourceImplTest;

public class ClientTest extends ProgrammeResourceImplTest {

	@Override
	@Before
	public void setUp() throws Exception {
		createProgrammes();
	}
	
	@Override
	protected ProgrammeResource programmeResource(int i) {
		return new ClientProgrammeProxy(i);
	}
}
