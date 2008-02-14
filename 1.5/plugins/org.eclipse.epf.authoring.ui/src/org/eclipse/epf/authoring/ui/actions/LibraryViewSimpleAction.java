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
package org.eclipse.epf.authoring.ui.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.epf.authoring.ui.views.LibraryView;
import org.eclipse.epf.library.edit.util.TngUtil;
import org.eclipse.epf.library.ui.actions.LibraryLockingOperationRunner;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Base for simple library action classes
 * @author Weiping Lu
 * @since  1.5
 */
public abstract class LibraryViewSimpleAction extends Action {

	private LibraryView libraryView;
	
	/**
	 * @param libView
	 * @param text
	 */
	public LibraryViewSimpleAction(LibraryView libView, String text) {
		super(text);
		libraryView = libView;
	}
	
	protected abstract void doRun();

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		LibraryLockingOperationRunner runner = new LibraryLockingOperationRunner();
		runner.run(new IRunnableWithProgress() {

			public void run(IProgressMonitor monitor)
					throws InvocationTargetException,
					InterruptedException {
				doRun();
			}

		});
	}
	
	/**
	 * @param selection
	 * @return
	 */
	public boolean updateSelection(IStructuredSelection selection) {
		return true;
	}

	protected LibraryView getLibraryView() {
		return libraryView;
	}
	
	protected Object getSelectionParentObject() {
		TreeItem[] selectedItems = getLibraryView().getTreeViewer().getTree().getSelection();
		if (selectedItems == null || selectedItems.length == 0) {
			return null;
		}
		TreeItem item = selectedItems[0].getParentItem();
		return TngUtil.unwrap(item.getData());
	}

}
