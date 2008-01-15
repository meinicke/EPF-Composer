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
package org.eclipse.epf.publishing.ui.wizards;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.epf.authoring.ui.AuthoringUIText;
import org.eclipse.epf.common.ui.util.CommonPreferences;
import org.eclipse.epf.common.utils.StrUtil;
import org.eclipse.epf.publishing.ui.PublishingUIPlugin;
import org.eclipse.epf.publishing.ui.PublishingUIResources;
import org.eclipse.epf.publishing.ui.preferences.PublishingUIPreferences;
import org.eclipse.epf.ui.wizards.BaseWizardPage;
import org.eclipse.epf.uma.MethodConfiguration;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * A wizard page that prompts the user to select the destination directory and
 * format for the published web site.
 * 
 * @author Kelvin Low
 * @since 1.2
 */
public class SelectDestinationPage extends BaseWizardPage {

	public static final String PAGE_NAME = SelectDestinationPage.class
			.getName();

	public static final String PREFERENCE_DELIMITER = "|"; //$NON-NLS-1$

	public static final String SPLIT_PREFERENCE_DELIMITER = "\\" + PREFERENCE_DELIMITER;//$NON-NLS-1$

	private String defaultPath = PublishingUIPreferences
			.getDefaultPublishPath();

	protected Shell shell;

	protected Combo dirCombo;

	protected Button browseButton;

	protected Group webAppGroup;

	protected Button staticWebSiteRadioButton;

	protected Composite staticWebSiteComposite;

	protected Button javaWebAppRadioButton;

	protected Composite javaWebAppComposite;

	protected Label webAppNameLabel;

	protected Combo webAppNameCombo;

	protected Button includeSearchCheckbox;

	protected MethodConfiguration config;

	protected List<MethodConfiguration> selectedConfigs = new ArrayList<MethodConfiguration>();

	/**
	 * Creates a new instance.
	 */
	public SelectDestinationPage(String pageName) {
		super(pageName,
				PublishingUIResources.selectDestinationWizardPage_title,
				PublishingUIResources.selectDestinationWizardPage_text,
				PublishingUIPlugin.getDefault().getImageDescriptor(
						"full/wizban/PublishConfiguration.gif")); //$NON-NLS-1$
	}

	/**
	 * Creates a new instance.
	 */
	public SelectDestinationPage() {
		this(PAGE_NAME);
	}

	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		shell = parent.getShell();

		Composite composite = createGridLayoutComposite(parent, 1);

		Composite dirComposite = createGridLayoutComposite(composite, 3);

		createLabel(dirComposite, PublishingUIResources.dirLabel_text);

		dirCombo = createCombobox(dirComposite);

		browseButton = createButton(dirComposite,
				AuthoringUIText.BROWSE_BUTTON_TEXT);

		webAppGroup = super.createGridLayoutGroup(composite,
				PublishingUIResources.webAppGroup_text, 1);

		staticWebSiteRadioButton = createRadioButton(webAppGroup,
				PublishingUIResources.staticWebSiteRadioButton_text);

		staticWebSiteComposite = createChildGridLayoutComposite(webAppGroup, 1);

		javaWebAppRadioButton = createRadioButton(webAppGroup,
				PublishingUIResources.dynamicWebAppRadioButton_text);

		javaWebAppComposite = createChildGridLayoutComposite(webAppGroup, 3);

		includeSearchCheckbox = createCheckbox(javaWebAppComposite,
				PublishingUIResources.includeSearchCheckbox_text, 3);

		webAppNameLabel = createLabel(javaWebAppComposite,
				PublishingUIResources.webApplicationNameLabel_text);

		webAppNameCombo = createCombobox(javaWebAppComposite, 2);

		initControls();

		addListeners();

		setControl(composite);
	}

	/**
	 * Initializes the wizard page controls with data.
	 */
	protected void initControls() {
		String configId = config != null ? config.getGuid() : ""; //$NON-NLS-1$

		String dir = PublishingUIPreferences.getPublishPath(configId);
		if (dir != null && dir.length() > 0) {
			dirCombo.setItems(subArray(coverStringToArray(dir)));
			dirCombo.select(0);
		}

		boolean publishStaticWebSite = PublishingUIPreferences
				.getPublishStaticWebSite(configId);
		staticWebSiteRadioButton.setSelection(publishStaticWebSite);
		javaWebAppRadioButton.setSelection(!publishStaticWebSite);

		includeSearchCheckbox.setSelection(PublishingUIPreferences
				.getIncludeSearch(configId));

		String webAppName = PublishingUIPreferences.getWebAppName(configId);
		if (webAppName != null && webAppName.length() > 0) {
			webAppNameCombo.setItems(new String[] { webAppName });
			webAppNameCombo.setText(webAppName);
		}

		updateControls();
	}

	/**
	 * Adds event handlers to the wizard page controls.
	 */
	protected void addListeners() {
		dirCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(isPageComplete());
			}
		});

		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				try {
					DirectoryDialog dialog = new DirectoryDialog(shell,
							SWT.NONE);
					String selectedDir = dialog.open();
					if (selectedDir != null) {
						dirCombo.setText(selectedDir);
					}
				} catch (Exception e) {
					PublishingUIPlugin.getDefault().getLogger().logError(e);
				}
			}
		});

		staticWebSiteRadioButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				updateControls();
			}
		});

		javaWebAppRadioButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				updateControls();
			}
		});

		webAppNameCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(isPageComplete());
			}
		});
	}

	protected String[] subArray(String[] arr) {
		int ll = CommonPreferences.getPreferenceHistorySize();
		if (ll < 1)
			ll = CommonPreferences.getDefaultPreferenceHistorySize();

		int arrLength = arr.length;

		if (arrLength < ll && arr[0].equals(defaultPath))
			return arr;

		ll = arrLength >= ll ? ll : arrLength + 1;
		String[] result = new String[ll];
		for (int i = 0; i < ll - 1; i++) {
			result[i] = arr[i];
		}
		result[ll - 1] = arr[0].equals(defaultPath) ? arr[ll] : defaultPath;

		return result;
	}

	protected String[] coverStringToArray(String preferenceStr) {
		return preferenceStr.split(SPLIT_PREFERENCE_DELIMITER);
	}

	protected String convertArrayToString(String[] arr) {
		StringBuffer sBuffer = new StringBuffer();
		if (arr == null || arr.length == 0)
			return sBuffer.toString();
		sBuffer.append(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			sBuffer.append(PREFERENCE_DELIMITER);
			sBuffer.append(arr[i]);
		}
		return sBuffer.toString();
	}

	/**
	 * Updates the control.
	 */
	protected void updateControls() {
		boolean publishStaticWebSite = getStaticWebSiteSelection();
		webAppNameLabel.setEnabled(!publishStaticWebSite);
		webAppNameCombo.setEnabled(!publishStaticWebSite);
		includeSearchCheckbox.setEnabled(!publishStaticWebSite);
		setPageComplete(isPageComplete());
	}

	/**
	 * @see org.eclipse.epf.ui.wizards.BaseWizardPage#onEnterPage(Object)
	 */
	public void onEnterPage(Object obj) {
		if (obj != null && obj instanceof MethodConfiguration) {
			config = (MethodConfiguration) obj;
			if (!selectedConfigs.contains(config)) {
				selectedConfigs.add(config);
				initControls();
			}
		}
	}

	/**
	 * @see org.eclipse.jface.wizard.WizardPage#isCompleted()
	 */
	public boolean isPageComplete() {
		String path = getPublishDirectory();
		IPath ecPath = Path.fromOSString(path);
		boolean isValid = ecPath.isValidPath(path);
		if (!isValid) {
			setErrorMessage(PublishingUIResources.invalidPathError_msg);
			return false;
		} else if (!StrUtil.isValidPublishPath(path)) {
			setErrorMessage(PublishingUIResources.invalidPathCharsError_msg);
			return false;
		} else {
			setErrorMessage(null);
		}
		if (path.length() == 0) {
			return false;
		}

		if (!getStaticWebSiteSelection()) {
			String webAppName = getWebAppName();
			if (webAppName.length() == 0) {
				setErrorMessage(PublishingUIResources.webAppNameError_msg);
				return false;
			} else if (!StrUtil.isValidName(webAppName)) {
				setErrorMessage(PublishingUIResources.webAppNameCharsError_msg);
				return false;
			} else {
				setErrorMessage(null);
			}
		}

		return true;
	}

	/**
	 * Gets the user specified publish directory.
	 */
	public String getPublishDirectory() {
		String dirText = dirCombo.getText().trim();
		if (dirText.length() > 0) {
			File dir = new File(dirCombo.getText().trim());
			return dir.getAbsolutePath();
		}
		return dirText;
	}

	protected String[] getPublishDirectoryArray() {
		String selectedtext = dirCombo.getText();

		if (dirCombo.indexOf(selectedtext) > 0) {
			dirCombo.remove(selectedtext);
		}

		if (dirCombo.indexOf(selectedtext) < 0) {
			dirCombo.add(selectedtext, 0);
		}

		if (dirCombo.indexOf(defaultPath) > 0) {
			dirCombo.remove(defaultPath);
		}

		return dirCombo.getItems();
	}

	/**
	 * Gets the user specified static web site selection.
	 */
	public boolean getStaticWebSiteSelection() {
		return staticWebSiteRadioButton.getSelection();
	}

	/**
	 * Gets the user specified include search selection.
	 */
	public boolean getIncludeSearchSelection() {
		return includeSearchCheckbox.getSelection();
	}

	/**
	 * Gets the web aplication name.
	 */
	public String getWebAppName() {
		return webAppNameCombo.getText().trim();
	}

	/**
	 * Saves the user selections in this wizard page to preference store.
	 */
	public void savePreferences() {
		if (config != null) {
			String configId = config.getGuid();
			PublishingUIPreferences.setPublishPath(configId,
					convertArrayToString(getPublishDirectoryArray()));
			PublishingUIPreferences.setPublishStaticWebSite(configId,
					getStaticWebSiteSelection());
			PublishingUIPreferences.setIncludeSearch(configId,
					getIncludeSearchSelection());
			PublishingUIPreferences.setWebAppName(configId, getWebAppName());
		}
	}

}