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
package org.eclipse.epf.authoring.ui.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.edit.command.CopyToClipboardCommand;
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.epf.common.ui.util.ClipboardUtil;
import org.eclipse.epf.common.utils.StrUtil;
import org.eclipse.epf.library.edit.util.TngUtil;
import org.eclipse.epf.library.util.ResourceHelper;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.MethodPlugin;
import org.eclipse.epf.uma.edit.domain.TraceableAdapterFactoryEditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;


/**
 * Copies an element in the Library view.
 * 
 * @author Phong Nguyen Le
 * @since 1.0
 */
public class LibraryViewCopyAction extends CopyAction {

	/**
	 * @see org.eclipse.emf.edit.ui.action.CommandActionHandler#run()
	 */
	public void run() {
		if (command == null)
			return;
		else {
			if (domain instanceof TraceableAdapterFactoryEditingDomain) {
				((TraceableAdapterFactoryEditingDomain) domain).resetCopyMaps();
			}
			if (command instanceof CopyToClipboardCommand) {
				String links = ""; //$NON-NLS-1$
				for (Iterator iter = ((CopyToClipboardCommand)command).getSourceObjects().iterator();iter.hasNext();) {
					Object item = TngUtil.unwrap(iter.next());
					if (item instanceof MethodElement) {
						MethodElement e = (MethodElement)item;
						String href = ResourceHelper.getUrl(e,
								null, "html"); //$NON-NLS-1$
						if (links.length() > 0) {
							links += StrUtil.LINE_FEED;
						}
						links += ResourceHelper.getElementLink(e, true,
								"file://" + href); //$NON-NLS-1$
					}
				}
				if (links.length() > 0) {
					ClipboardUtil.copyTextHTMLToClipboard(links);
				}
			}
			super.run();
		}
	}

	
	/**
	 * @see org.eclipse.emf.edit.ui.action.CommandActionHandler#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public boolean updateSelection(IStructuredSelection selection) {
		if (selection == null || selection.isEmpty())
			return false;

		Collection collection = new ArrayList();
		for (Iterator objects = selection.iterator(); objects.hasNext();) {
			Object element = TngUtil.unwrap(objects.next());
			if (element instanceof MethodElement
					&& !(element instanceof MethodPlugin)
					&& !TngUtil.isPredefined((MethodElement) element)) {
				collection.add(element);
			}

		}

		command = createCommand(collection);

		return command.canExecute();
	}

}
