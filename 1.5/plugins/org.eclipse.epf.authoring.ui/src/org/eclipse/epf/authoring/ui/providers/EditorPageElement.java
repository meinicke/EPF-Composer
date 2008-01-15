//------------------------------------------------------------------------------
// Copyright (c) 2004, 2006 IBM Corporation.  All Rights Reserved.
//------------------------------------------------------------------------------
package org.eclipse.epf.authoring.ui.providers;

import org.osgi.framework.Bundle;


/**
 * Models a <editorPage> element in the "org.eclipse.epf.authoring.ui.EditorPageProvider"
 * extension point.
 * 
 * @author Shilpa Toraskar
 * @since 1.0
 */
public class EditorPageElement {

	// plugin bundle
	Bundle bundle;
	
	// The page ID.
	private String id;
	
	// The page name.
	private String name;

	// The contributor class for the page.
	private String contributorClass;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param bundle
	 * 			  Installed Bundle
	 * @param id
	 *            The page ID.
	 * @param name
	 *            The page name.            
	 * @param contributorClass
	 *            The page contributor class.
	 */
	public EditorPageElement(Bundle bundle, String id, String name, String contributorClass) {
		this.bundle = bundle;
		this.id = id;
		this.name = name;
		this.contributorClass = contributorClass;
	}

	/**
	 * Returns the page ID.
	 * 
	 * @return The page ID.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Returns the page name.
	 * 
	 * @return The page name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the page contributor class.
	 * 
	 * @return The page contributor class.
	 */
	public Object getContributorClass() throws Exception {
		
		Class clazz = bundle.loadClass(contributorClass);
		return clazz.newInstance();	
	}

}