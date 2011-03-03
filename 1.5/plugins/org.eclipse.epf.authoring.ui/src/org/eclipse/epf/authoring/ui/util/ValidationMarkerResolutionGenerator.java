//------------------------------------------------------------------------------
// Copyright (c) 2005, 2008 IBM Corporation and others.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
// IBM Corporation - initial implementation
//------------------------------------------------------------------------------
package org.eclipse.epf.authoring.ui.util;

import java.util.ArrayList;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.epf.authoring.ui.AuthoringUIPlugin;
import org.eclipse.epf.authoring.ui.AuthoringUIResources;
import org.eclipse.epf.library.edit.util.LibraryEditUtil;
import org.eclipse.epf.library.edit.validation.IValidationManager;
import org.eclipse.epf.library.validation.ValidationManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

/**
 * Generates ValidationMarkerResolution's
 * @author Weiping Lu
 * @since  1.5.1.2
 */
public class ValidationMarkerResolutionGenerator implements IMarkerResolutionGenerator {
	
	private static final IMarkerResolution[] EMPTY_RESOLUTIONS = new IMarkerResolution[0];

	public IMarkerResolution[] getResolutions(IMarker marker) {
		try {
			if (marker.getType() == ValidationManager.MARKER_ID) {
				return new IMarkerResolution[] {
						new AddPluginResolution(marker),
						new RemoveUndeclaredResolution(marker),
						};
			}
		} catch (Exception e) {
			AuthoringUIPlugin.getDefault().getLogger().logError(e);
		}
		return EMPTY_RESOLUTIONS;
	}

	public static abstract class ValidationMarkerResolution extends WorkbenchMarkerResolution {
		private IMarker currentMarker;

		public ValidationMarkerResolution(IMarker currentMarker) {
			this.currentMarker = currentMarker;
		}

		@Override
		public IMarker[] findOtherMarkers(IMarker[] markers) {
			ArrayList<IMarker> similarMarkerList = new ArrayList<IMarker>();
			IMarker[] similarMarkers = new IMarker[similarMarkerList.size()];
			similarMarkerList.toArray(similarMarkers);
			return similarMarkers;
		}
		
		protected boolean acceptedType(IMarker marker) {
			boolean ret = false;
			try {
				ret = ValidationManager.MARKER_ID.equals(marker.getType());
			} catch (CoreException e) {
				AuthoringUIPlugin.getDefault().getLogger().logError(e);
			}
			
			return ret;
		}

		public String getDescription() {
			// TODO provide detailed description
			return null;
		}

		public Image getImage() {
			// TODO provide image
			return null;
		}
		
	}
			
	private static class AddPluginResolution extends ValidationMarkerResolution {
		
		public AddPluginResolution(IMarker currentMarker) {
			super(currentMarker);
		}
		
		/**
		 * @see org.eclipse.ui.IMarkerResolution#getLabel()
		 */
		public String getLabel() {
			return "Add reference plug-in";
		}

		public void run(IMarker marker) {			
			IValidationManager mgr = LibraryEditUtil.getInstance().getValidationManager();
			String msg = ((ValidationManager) mgr).UndeclaredDependencyCheckAddPluginFix(marker);
			if (msg != null && msg.length() > 0) {
				AuthoringUIPlugin.getDefault().getMsgDialog()
				.displayWarning(
						"AddPluginResolution fix", 
						msg);
			}
		}

		@Override
		protected boolean acceptedType(IMarker marker) {
			boolean ret = super.acceptedType(marker);
			return ret;
		}
		
	}	
	
	private static class RemoveUndeclaredResolution extends ValidationMarkerResolution {
		
		public RemoveUndeclaredResolution(IMarker currentMarker) {
			super(currentMarker);
		}
		
		/**
		 * @see org.eclipse.ui.IMarkerResolution#getLabel()
		 */
		public String getLabel() {
			return "Remove undeclared references";
		}

		public void run(IMarker marker) {			
			IValidationManager mgr = LibraryEditUtil.getInstance().getValidationManager();
			((ValidationManager) mgr).UndeclaredDependencyCheckRemoveReferenceFix(marker);
		}

		@Override
		protected boolean acceptedType(IMarker marker) {
			boolean ret = super.acceptedType(marker);
			return ret;
		}
		
		
	}
	
}
