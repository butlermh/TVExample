package com.tvexample.programme.rest;

import java.util.HashMap;

import com.tvexample.programme.xml.Programme;

/**
 * A simple in memory store of programmes.
 */
public class ProgrammeStore extends HashMap<Integer, Programme>{
	
	private static final long serialVersionUID = -2914327733573273704L;

	/**
	 * Add a programme to the store indexed by its ID.
	 * 
	 * @param programme The programme.
	 */
	public void add(Programme programme) {
		put(programme.getId(), programme);
	}
}
