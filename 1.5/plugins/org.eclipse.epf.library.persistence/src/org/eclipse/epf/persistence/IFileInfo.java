//------------------------------------------------------------------------------
// Copyright (c) 2005, 2007 IBM Corporation and others.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
// IBM Corporation - initial implementation
//------------------------------------------------------------------------------
package org.eclipse.epf.persistence;

import java.io.File;

/**
 * @author Phong Nguyen Le
 * @sine 1.2
 */
public interface IFileInfo {
	File getFile();
	
	long getModificationStamp();
	
	boolean isSynchronized();
}
