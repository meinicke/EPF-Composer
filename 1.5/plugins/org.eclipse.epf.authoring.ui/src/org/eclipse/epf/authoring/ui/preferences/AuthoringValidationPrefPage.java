/*******************************************************************************
 * Copyright (c) 2005, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial implementation
 *******************************************************************************/
package org.eclipse.epf.authoring.ui.preferences;

import org.eclipse.epf.authoring.ui.AuthoringUIResources;
import org.eclipse.epf.common.ui.PreferenceStoreWrapper;
import org.eclipse.epf.library.ui.LibraryUIPlugin;
import org.eclipse.epf.library.ui.preferences.LibraryUIPreferences;
import org.eclipse.epf.ui.preferences.BasePreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The supporiting plugins Preference page
 * 
 * @author Weiping Lu
 * @since 1.5.1.2
 */
public class AuthoringValidationPrefPage extends BasePreferencePage
		implements IWorkbenchPreferencePage, SelectionListener {

	private Button button1;
	private Button button2;
	private Button button3;

	/**
	 * 
	 */
	public AuthoringValidationPrefPage() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		Group layoutGroup = createGridLayoutGroup(composite,
				"Validation Types", 1);
		layoutGroup.setLayout(new GridLayout());
		layoutGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		button1 = createCheckbox(
				layoutGroup,
				"Check names", 1);
		
		button2 = createCheckbox(
				layoutGroup,
				"Check circular dependency", 1);

		button3 = createCheckbox(
				layoutGroup,
				"Check un-declared plug-in dependency", 1);
		
		// Initialize values
		initializeValues();

		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epf.ui.preferences.BasePreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		super.performDefaults();
		button1.setSelection(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	public boolean performOk() {
		LibraryUIPreferences.setIncludeDescriptors(getIncludeDescriptors());
		return true;
	}

	private boolean getIncludeDescriptors() {
		return button1.getSelection();
	}

	/**
	 * Initializes states of the controls from the preference store.
	 */
	private void initializeValues() {
		button1.setSelection(LibraryUIPreferences.getIncludeDescriptors());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#doGetPreferenceStore()
	 */
	protected IPreferenceStore doGetPreferenceStore() {
		IPreferenceStore wrapper = LibraryUIPlugin.getDefault()
				.getPreferenceStore();

		if (wrapper instanceof PreferenceStoreWrapper) {
			return ((PreferenceStoreWrapper) wrapper).getStore();
		}
		return null;
	}

}
