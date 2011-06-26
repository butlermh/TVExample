package com.tvexample.programme.rest;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;

import com.tvexample.programme.xml.Programme;

/**
 * An interface for a Programme as a REST resource.
 */
public interface ProgrammeResource {

	/**
	 * Retrieve a Programme from the ProgrammeStore.
	 *
	 * @return The Programme.
	 * @throws ResourceException Thrown if the Programme does not exist.
	 */
	@Get("xml")
	public Programme retrieve() throws ResourceException;

	/**
	 * Store a Programme in the ProgrammeStore.
	 *
	 * @param programme The Programme.
	 * @throws ResourceException Thrown if the Programme already exists.
	 */
	@Post("xml")
	public void create(Programme programme) throws ResourceException;

	/**
	 * Update the Programme in the ProgrammeStore.
	 * 
	 * @param The Programme.
	 * @throws ResourceException Thrown if the Programme does not exist.
	 */
	@Put("xml")
	public void update(Programme programme) throws ResourceException;

	/**
	 * Remove the Programme from the ProgrammeStore.
	 * 
	 * @param The Programme.
	 * @throws ResourceException Thrown if the Programme does not exist.
	 */
	@Delete("xml")
	public void remove() throws ResourceException;
}