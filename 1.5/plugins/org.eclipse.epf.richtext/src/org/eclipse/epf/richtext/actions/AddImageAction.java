/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
//------------------------------------------------------------------------------
// Copyright (c) 2005, 2006 IBM Corporation and others.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
// IBM Corporation - initial implementation
//------------------------------------------------------------------------------
package org.eclipse.epf.richtext.actions;

import org.eclipse.epf.richtext.IRichText;
import org.eclipse.epf.richtext.RichTextCommand;
import org.eclipse.epf.richtext.RichTextImages;
import org.eclipse.epf.richtext.RichTextResources;
import org.eclipse.epf.richtext.dialogs.AddImageDialog;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

/**
 * Adds an image to a rich text control.
 * 
 * @author Kelvin Low
 * @since 1.0
 */
public class AddImageAction extends RichTextAction {

	/**
	 * Creates a new instance.
	 */
	public AddImageAction(IRichText richText) {
		super(richText, IAction.AS_PUSH_BUTTON);
		setImageDescriptor(RichTextImages.IMG_DESC_ADD_IMAGE);
		setDisabledImageDescriptor(RichTextImages.DISABLED_IMG_DESC_ADD_IMAGE);
		setToolTipText(RichTextResources.addImageAction_toolTipText); 
	}

	/**
	 * Executes the action.
	 * 
	 * @param richText
	 *            a rich text control
	 */
	public void execute(IRichText richText) {
		if (richText != null) {
			AddImageDialog dialog = new AddImageDialog(Display.getCurrent()
					.getActiveShell());
			dialog.open();
			if (dialog.getReturnCode() == Window.OK) {
				String imageURL = dialog.getImage().getURL();
				if (imageURL.length() > 0) {
					richText
							.executeCommand(RichTextCommand.ADD_IMAGE, imageURL);
				}
			}
		}
	}
	
	public boolean disableInSourceMode() {
		return false;
	}


}