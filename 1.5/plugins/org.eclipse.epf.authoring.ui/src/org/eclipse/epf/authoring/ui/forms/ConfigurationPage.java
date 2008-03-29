package org.eclipse.epf.authoring.ui.forms;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.epf.authoring.ui.AuthoringUIHelpContexts;
import org.eclipse.epf.authoring.ui.AuthoringUIImages;
import org.eclipse.epf.authoring.ui.AuthoringUIPlugin;
import org.eclipse.epf.authoring.ui.AuthoringUIResources;
import org.eclipse.epf.authoring.ui.editors.ConfigurationEditor;
import org.eclipse.epf.authoring.ui.editors.ConfigurationEditorInput;
import org.eclipse.epf.authoring.ui.providers.CategoryContentProvider;
import org.eclipse.epf.authoring.ui.providers.CategoryLabelProvider;
import org.eclipse.epf.authoring.ui.providers.ConfigPackageContentProvider;
import org.eclipse.epf.authoring.ui.providers.ConfigPackageLabelProvider;
import org.eclipse.epf.authoring.ui.providers.HideUncheckedViewerFilter;
import org.eclipse.epf.authoring.ui.util.AuthoringAccessibleListener;
import org.eclipse.epf.authoring.ui.util.ConfigurationMarkerHelper;
import org.eclipse.epf.authoring.ui.views.MethodContainerCheckedTreeViewer;
import org.eclipse.epf.authoring.ui.views.MethodContainerCheckedTreeViewer2;
import org.eclipse.epf.library.IConfigurationManager;
import org.eclipse.epf.library.ILibraryManager;
import org.eclipse.epf.library.LibraryService;
import org.eclipse.epf.library.configuration.ConfigurationData;
import org.eclipse.epf.library.configuration.closure.ClosureListener;
import org.eclipse.epf.library.configuration.closure.ConfigurationClosure;
import org.eclipse.epf.library.configuration.closure.IConfigurationError;
import org.eclipse.epf.library.edit.IPluginUIPackageContextChangedListener;
import org.eclipse.epf.library.edit.PluginUIPackageContext;
import org.eclipse.epf.library.edit.TngAdapterFactory;
import org.eclipse.epf.library.edit.command.IActionManager;
import org.eclipse.epf.library.edit.command.MethodElementSetPropertyCommand;
import org.eclipse.epf.library.edit.ui.UserInteractionHelper;
import org.eclipse.epf.library.edit.util.ConfigurationUtil;
import org.eclipse.epf.library.edit.util.MethodElementUtil;
import org.eclipse.epf.library.edit.util.TngUtil;
import org.eclipse.epf.library.events.ILibraryChangeListener;
import org.eclipse.epf.library.util.LibraryUtil;
import org.eclipse.epf.uma.ContentCategory;
import org.eclipse.epf.uma.CustomCategory;
import org.eclipse.epf.uma.MethodConfiguration;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.MethodPackage;
import org.eclipse.epf.uma.MethodPlugin;
import org.eclipse.epf.uma.ProcessComponent;
import org.eclipse.epf.uma.util.UmaUtil;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.ide.IGotoMarker;

public class ConfigurationPage extends FormPage implements IGotoMarker {
	
	public static final String TOUCHED_BY_CONFIG_EDITOR = "TouchedByConfigEditor";
	
	private String formPrefix = AuthoringUIResources.ConfigurationPage_FormPrefix; 

	private MethodConfiguration config = null;
	private ConfigurationClosure closure = null;
	
	private MethodContainerCheckedTreeViewer configViewer;
	private ConfigPackageContentProvider contProvider;
	private ConfigPackageLabelProvider labelProvider;

	private MethodContainerCheckedTreeViewer2 addCategoryViewer;
	private MethodContainerCheckedTreeViewer2 subCategoryViewer;
	
	private HideUncheckedViewerFilter configViewerHideUncheckedFilter;
	private HideUncheckedViewerFilter addCategoryHideUncheckedFilter;
	private HideUncheckedViewerFilter subCategoryHideUncheckedFilter;
	

	private ILibraryChangeListener libListener = null;
	
	private static final ConfigurationMarkerHelper markerHelper = ConfigurationMarkerHelper.INSTANCE;
	boolean isDirty = false;
	protected ISelection currentSelection = StructuredSelection.EMPTY;

	private IActionManager actionMgr;
	ScrolledForm form = null;
	private Button updateOnClick;
	private Button noUpdateOnClick;
	private Button closureButton;
	private Button fixWarningButton;
	private Button refreshButton;
	private Button hideButton;
	private Text elemDespContentText;


	protected ISelectionChangedListener treeSelectionListener = new ISelectionChangedListener() {
		/**
		 *	Handles the selection of an item in the tree viewer
		 *
		 *	@param event ISelection
		 */
		public void selectionChanged(final SelectionChangedEvent event) {
			BusyIndicator.showWhile(ConfigurationPage.this.getSite().getShell().getDisplay(), new Runnable() {
				public void run() {
					IStructuredSelection selection= (IStructuredSelection) event.getSelection();
					Object selectedElement= selection.getFirstElement();
					selectedElement = TngUtil.unwrap(selectedElement);
					if (selectedElement == null) {
						return;
					} else if (elemDespContentText != null && selectedElement instanceof MethodElement) {
						// display selected element's description
						String briefDesc = ((MethodElement) selectedElement).getBriefDescription();
						elemDespContentText.setText(briefDesc != null ? briefDesc : ""); //$NON-NLS-1$
					}
				}
			});
		}
	};

	private static final ClosureListener closureListener = new ClosureListener() {
		@Override
		public void errorAdded(MethodConfiguration config, IConfigurationError error) {
			IConfigurationManager configMgr = LibraryService.getInstance().getConfigurationManager(config);
			if (configMgr.getConfigurationProperties().toHide(error)) {
				return;
			}
			markerHelper.createMarker(config, error);
		}

		@Override
		public void errorRemoved(MethodConfiguration config, IConfigurationError error) {
			markerHelper.deleteMarker(config, error);
		}

		@Override
		public void errorUpdated(MethodConfiguration config, IConfigurationError error) {
			markerHelper.adjustMarker(null, config, error);
		}
	};

	private IPluginUIPackageContextChangedListener layoutListener = new IPluginUIPackageContextChangedListener() {
		public void layoutChanged(boolean isFlat) {
			refreshViewers();
		}
	};

	/**
	 * Creates an instance
	 * @param editor
	 */
	public ConfigurationPage(FormEditor editor) {
		super(editor, AuthoringUIResources.ConfigurationPage_Description1, 
				AuthoringUIResources.ConfigurationPage_Description2); 
	}

	/**
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	protected void createFormContent(IManagedForm managedForm) {
		// create form toolkit
		form = managedForm.getForm();
		form.setText(formPrefix + config.getName());
		FormToolkit toolkit = managedForm.getToolkit();

		TableWrapLayout layout = new TableWrapLayout();
		form.getBody().setLayout(layout);

		Section treeSection = toolkit.createSection(form.getBody(),
				Section.DESCRIPTION | Section.TWISTIE | Section.EXPANDED
						| Section.TITLE_BAR);
		treeSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		treeSection.setText(AuthoringUIResources.ConfigurationPage_ConfigContent); 
		treeSection.setDescription(AuthoringUIResources.ConfigurationPage_ConfigContentDescription); 
		treeSection.setLayout(new GridLayout());
		
		createContent(toolkit, treeSection);
		
		addListeners();

		// finally set the input.
		setInput(LibraryService.getInstance().getCurrentMethodLibrary());
		
		initializeViewersSelection();
	}

	
	/**
	 * @see org.eclipse.ui.forms.editor.FormPage#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	public void init(IEditorSite site, IEditorInput input) {
		setSite(site);
		setInput(input);

		ConfigurationEditorInput configInput = (ConfigurationEditorInput) input;
		config = configInput.getConfiguration();
		actionMgr = ((ConfigurationEditor) getEditor()).getActionManager();
	}

	/**
	 * Create tree content
	 * @param toolkit
	 * @param section
	 */
	public void createContent(FormToolkit toolkit, Section section) {
		Composite sectionClient = toolkit.createComposite(section);
		sectionClient.setLayoutData(new GridData(GridData.FILL_BOTH));
		section.setClient(sectionClient);
		GridLayout layout = new GridLayout(2, true);
		sectionClient.setLayout(layout);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(
				sectionClient.getParent(),
				AuthoringUIHelpContexts.CONFIGURATION_EDITOR_ALL_CONTEXT);

		Composite buttonComposite = toolkit.createComposite(sectionClient);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 7;
		buttonComposite.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		buttonComposite.setLayoutData(gridData);
		
		Composite radioComposite = toolkit.createComposite(buttonComposite);
		gridLayout = new GridLayout();
		radioComposite.setLayout(gridLayout);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 1;
		radioComposite.setLayoutData(gridData);
		
		
		updateOnClick = toolkit.createButton(radioComposite, AuthoringUIResources.ConfigurationPage_updateOnClick, SWT.RADIO);
		updateOnClick.setToolTipText(AuthoringUIResources.ConfigurationPage_updateOnClickToolTip); 
		updateOnClick.setLayoutData(new GridData(SWT.BEGINNING, SWT.DEFAULT, false, false));
		updateOnClick.getAccessible().addAccessibleListener(new AuthoringAccessibleListener(
				AuthoringUIResources.ConfigurationPage_updateOnClickToolTip));
		updateOnClick.setSelection(true);
		
		noUpdateOnClick = toolkit.createButton(radioComposite, AuthoringUIResources.ConfigurationPage_noUpdateOnClick, SWT.RADIO);
		noUpdateOnClick.setToolTipText(AuthoringUIResources.ConfigurationPage_noUpdateOnClickToolTip); 
		noUpdateOnClick.setLayoutData(new GridData(SWT.BEGINNING, SWT.DEFAULT, false, false));
		noUpdateOnClick.getAccessible().addAccessibleListener(new AuthoringAccessibleListener(
				AuthoringUIResources.ConfigurationPage_noUpdateOnClickToolTip));

		hideButton = toolkit.createButton(buttonComposite, "", SWT.PUSH //$NON-NLS-1$
				| GridData.HORIZONTAL_ALIGN_END);
		hideButton.setImage(AuthoringUIPlugin.getDefault().getSharedImage(
				"hideUncheckedElem.gif")); //$NON-NLS-1$
		hideButton.setToolTipText(AuthoringUIResources.ConfigurationPage_hideToolTip); 
		hideButton.setLayoutData(new GridData(GridData.END));
		hideButton.getAccessible().addAccessibleListener(new AuthoringAccessibleListener(
				AuthoringUIResources.ConfigurationPage_hideToolTip));
		
		fixWarningButton = toolkit.createButton(buttonComposite, "", SWT.PUSH); //$NON-NLS-1$
		fixWarningButton.setImage(AuthoringUIPlugin.getDefault()
				.getSharedImage("addref_co.gif")); //$NON-NLS-1$
		fixWarningButton.setToolTipText(AuthoringUIResources.ConfigurationPage_AddMissingToolTip); 
		fixWarningButton.setLayoutData(new GridData(GridData.END));
		fixWarningButton.getAccessible().addAccessibleListener(new AuthoringAccessibleListener(
				AuthoringUIResources.ConfigurationPage_AddMissingToolTip));

		// Add the closure button.
		closureButton = toolkit.createButton(buttonComposite, "", SWT.PUSH); //$NON-NLS-1$
		closureButton.setImage(AuthoringUIPlugin.getDefault().getSharedImage(
				"closure_co.gif")); //$NON-NLS-1$
		closureButton.setToolTipText(AuthoringUIResources.ConfigurationPage_MakeClosureToolTip); 
		closureButton.setLayoutData(new GridData(GridData.END));
		closureButton.getAccessible().addAccessibleListener(new AuthoringAccessibleListener(
				AuthoringUIResources.ConfigurationPage_MakeClosureToolTip));
		// closureButton.setText("");

		refreshButton = toolkit.createButton(buttonComposite, "", SWT.PUSH); //$NON-NLS-1$
		refreshButton.setImage(AuthoringUIImages.IMG_REFRESH);
		refreshButton.setToolTipText(AuthoringUIResources.refreshButton_text);
		{
			GridData gd = new GridData(GridData.END
					| GridData.HORIZONTAL_ALIGN_END);
			gd.horizontalAlignment = 3;
			gd.horizontalSpan = 1;
			refreshButton.setLayoutData(gd);
		}
		refreshButton.getAccessible().addAccessibleListener(new AuthoringAccessibleListener(
				AuthoringUIResources.refreshButton_text));

		// Create Viewer and Handle Listener for the viewer.
		createViewers(toolkit, sectionClient);

		Label elemDespLabel = toolkit
				.createLabel(sectionClient, AuthoringUIResources.ConfigurationPage_Description); 
		GridData gd1 = new GridData();
		gd1.horizontalSpan = 6;
		elemDespLabel.setLayoutData(gd1);

		elemDespContentText = toolkit.createText(sectionClient, "", SWT.NONE //$NON-NLS-1$
				| SWT.MULTI | SWT.READ_ONLY | SWT.WRAP);
		GridData gd2 = new GridData(GridData.FILL_BOTH);
		gd2.grabExcessHorizontalSpace = true;
		gd2.horizontalSpan = 6;
		gd2.heightHint = 50;

		toolkit.paintBordersFor(sectionClient);
		toolkit.paintBordersFor(buttonComposite);
		elemDespContentText.setLayoutData(gd2);
		
		// set text widget to viewers so they can update description field
		configViewer.addSelectionChangedListener(treeSelectionListener);
		addCategoryViewer.addSelectionChangedListener(treeSelectionListener);
		subCategoryViewer.addSelectionChangedListener(treeSelectionListener);

		hideButton.setEnabled(true);
		hideButton.setVisible(true);
		refreshButton.setEnabled(true);
		refreshButton.setVisible(true);
		fixWarningButton.setEnabled(true);
		fixWarningButton.setVisible(true);
		closureButton.setEnabled(true);
		closureButton.setVisible(true);
	}


	private void initializeViewersSelection() {
		initializeConfigFactory();

    	List<MethodPackage> packages = new ArrayList<MethodPackage>(config.getMethodPackageSelection());
//    	List<MethodPlugin> plugins = new ArrayList<MethodPlugin>(config.getMethodPluginSelection());
//    	initializeViewerSelection(configViewer, plugins);
    	initializeViewerSelection(configViewer, packages);
		
		// read from config and check the appropriate items in the CC viewers
		List<ContentCategory> addCats = new ArrayList<ContentCategory>(config.getAddedCategory());
		initializeViewerSelection(addCategoryViewer, addCats);
    	List<ContentCategory> subCats = new ArrayList<ContentCategory>(config.getSubtractedCategory());
    	initializeViewerSelection(subCategoryViewer, subCats);
    	
	}
	
	private void initializeViewerSelection(ContainerCheckedTreeViewer viewer, List<? extends Object> elements) {
		if (!elements.isEmpty())
			viewer.setCheckedElements(elements.toArray());
	}

	private void createViewers(FormToolkit toolkit, Composite sectionClient) {
		Composite configViewerLabelComposite = toolkit.createComposite(sectionClient);
		GridData gd = new GridData(SWT.FILL, SWT.END, true, false);
		configViewerLabelComposite.setLayoutData(gd);
		gd.horizontalSpan = 1;
		configViewerLabelComposite.setLayout(new GridLayout(3, false));

		Composite addCatsViewerLabelComposite = toolkit.createComposite(sectionClient);
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		addCatsViewerLabelComposite.setLayoutData(gd);
		gd.horizontalSpan = 1;
		addCatsViewerLabelComposite.setLayout(new GridLayout(3, false));
		
		// create the library tree viewer
		configViewer = new MethodContainerCheckedTreeViewer(sectionClient);
		gd = new GridData(GridData.FILL_BOTH
				| GridData.GRAB_HORIZONTAL);
		gd.heightHint = 200;
		gd.verticalSpan = 3;
		configViewer.getTree().setLayoutData(gd);

		AdapterFactory adapterFactory = TngAdapterFactory.INSTANCE
			.getNavigatorView_ComposedAdapterFactory();
		contProvider = new ConfigPackageContentProvider(adapterFactory);
		configViewer.setContentProvider(contProvider);
		labelProvider = new ConfigPackageLabelProvider(contProvider);
		configViewer.setLabelProvider(labelProvider);
		createViewerLabelAndButtons(toolkit, configViewerLabelComposite,
				AuthoringUIResources.ConfigurationPage_TreeTitleLabel, configViewer);

		
		
		addCategoryViewer = new MethodContainerCheckedTreeViewer2(sectionClient);
		gd = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL);
		gd.heightHint= 200;
		addCategoryViewer.getTree().setLayoutData(gd);
		addCategoryViewer.setContentProvider(new CategoryContentProvider(adapterFactory, config));
		addCategoryViewer.setLabelProvider(new CategoryLabelProvider(adapterFactory));

		createViewerLabelAndButtons(toolkit, addCatsViewerLabelComposite,
				AuthoringUIResources.ConfigurationPage_AddCategoriesTitleLabel, addCategoryViewer);
	
		Composite subCatsViewerLabelComposite = toolkit.createComposite(sectionClient);
		gd = new GridData(SWT.FILL, SWT.END, true, false);
		subCatsViewerLabelComposite.setLayoutData(gd);
		gd.horizontalSpan = 1;
		subCatsViewerLabelComposite.setLayout(new GridLayout(3, false));
		
		subCategoryViewer = new MethodContainerCheckedTreeViewer2(sectionClient);
		gd = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL);
		gd.heightHint= 200;
		subCategoryViewer.getTree().setLayoutData(gd);
		subCategoryViewer.setContentProvider(new CategoryContentProvider(TngAdapterFactory.INSTANCE
						.getNavigatorView_ComposedAdapterFactory(), config));
		subCategoryViewer.setLabelProvider(new CategoryLabelProvider(TngAdapterFactory.INSTANCE
						.getNavigatorView_ComposedAdapterFactory()));
		createViewerLabelAndButtons(toolkit, subCatsViewerLabelComposite,
				AuthoringUIResources.ConfigurationPage_SubCategoriesTitleLabel, subCategoryViewer);
		
		// add listener so the 2 Category viewers are in sync
		// that is, when an item is checked in one, it is unchecked in the other
		addCategoryViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(final CheckStateChangedEvent event) {
				//Potentially long operation - show a busy cursor
				BusyIndicator.showWhile(subCategoryViewer.getTree().getDisplay(), new Runnable() {
					public void run() {
						if (event.getChecked())
							subCategoryViewer.setChecked(event.getElement(), false);
					}
				});
			}
		});

		subCategoryViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(final CheckStateChangedEvent event) {
				//Potentially long operation - show a busy cursor
				BusyIndicator.showWhile(addCategoryViewer.getTree().getDisplay(), new Runnable() {
					public void run() {
						if (event.getChecked())
							addCategoryViewer.setChecked(event.getElement(), false);
					}
				});
			}
		});
	}
	
	private void createViewerLabelAndButtons(FormToolkit toolkit, Composite parent, String text, final TreeViewer viewer) {
		Label label = toolkit.createLabel(parent, text);
		GridData gd = new GridData(SWT.FILL, SWT.END, true, false);
		label.setLayoutData(gd);
		
		Button expandButton = toolkit.createButton(parent, null, SWT.PUSH);
		gd = new GridData(SWT.RIGHT, SWT.END, false, false);
		expandButton.setLayoutData(gd);
		expandButton.setImage(AuthoringUIPlugin.getDefault().getSharedImage("expandall.gif")); //$NON-NLS-1$
		expandButton.setToolTipText(AuthoringUIResources.FilterDialog_ExpandAll); 
		expandButton.getAccessible().addAccessibleListener(new AuthoringAccessibleListener(
						AuthoringUIResources.FilterDialog_ExpandAll));
		
		expandButton.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			
			public void widgetSelected(SelectionEvent e) {
				viewer.expandAll();
			}
		});


		Button collapseButton = toolkit.createButton(parent, null, SWT.PUSH);
		gd = new GridData(SWT.RIGHT, SWT.END, false, false);
		collapseButton.setLayoutData(gd);
		collapseButton.setImage(AuthoringUIPlugin.getDefault().getSharedImage("collapseall.gif")); //$NON-NLS-1$
		collapseButton.setToolTipText(AuthoringUIResources.FilterDialog_CollapseAll); 
		collapseButton.getAccessible().addAccessibleListener(new AuthoringAccessibleListener(
				AuthoringUIResources.FilterDialog_CollapseAll));
		collapseButton.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			
			public void widgetSelected(SelectionEvent e) {
				viewer.collapseAll();
			}
		});

	}

	
	private void reInitializeConfigFactory() {
		// the following may not be the most efficient way
		// need a fast way to update the closure
//		System.out.println("$$$ reInit closure for config add notification!");

		createConfigurationClosure();
	}
	
	private void createConfigurationClosure() {
		closure = new ConfigurationClosure(actionMgr, config);
		closure.addListener(closureListener);
		if (labelProvider != null) {
			labelProvider.setClosure(closure);
		}
	}


	/**
	 * Set input for connfiguration viewer
	 * @param input
	 */
	public void setInput(Object input) {
		configViewer.setInput(input);
		addCategoryViewer.setInput(input);
		subCategoryViewer.setInput(input);

//		updateCheckStates();
	}

	private void refreshViewers() {
		configViewer.refresh();
		addCategoryViewer.refresh();
		subCategoryViewer.refresh();
	}
	
	
	protected void showHideElements() {
		configViewerHideUncheckedFilter.toggleHideUnchecked();
		addCategoryHideUncheckedFilter.toggleHideUnchecked();
		subCategoryHideUncheckedFilter.toggleHideUnchecked();
		refreshViewers();
//		updateCheckStates(); // neded to have this to update the check status
	}
	
	/**
	 * Initialize configuration factory
	 */
	public void initializeConfigFactory() {
		// loading the configuration closure might be slow,
		// display a progress bar
		org.eclipse.epf.library.edit.util.IRunnableWithProgress runnable = new org.eclipse.epf.library.edit.util.IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				createConfigurationClosure();
			}

		};

		UserInteractionHelper.runWithProgress(runnable, AuthoringUIResources.ConfigurationPage_LoadingMessage); 

		configViewerHideUncheckedFilter = new HideUncheckedViewerFilter(configViewer);
		configViewer.addFilter(configViewerHideUncheckedFilter);
		addCategoryHideUncheckedFilter = new HideUncheckedViewerFilter(addCategoryViewer);
		addCategoryViewer.addFilter(addCategoryHideUncheckedFilter);
		subCategoryHideUncheckedFilter = new HideUncheckedViewerFilter(subCategoryViewer);
		subCategoryViewer.addFilter(subCategoryHideUncheckedFilter);
		
	}

	
	private void addListeners() {
		closureButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				
				BusyIndicator.showWhile(form.getDisplay(), new Runnable() {
					public void run() {
						makeClosure();
					}
				});
			};
		});
		fixWarningButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				BusyIndicator.showWhile(form.getDisplay(), new Runnable() {
					public void run() {
						fixWarning();
					}
				});				
			};
		});
		refreshButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				BusyIndicator.showWhile(form.getDisplay(), new Runnable() {
					public void run() {
						saveConfiguration();
						showErrors();
					}
				});				
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		hideButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				showHideElements();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		// add a check state change listener
		ICheckStateListener configCheckStateListener = new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent evt) {
				
				BusyIndicator.showWhile(form.getDisplay(), new Runnable() {
					public void run() {
						if (updateOnClick.getSelection()) {
							saveConfiguration();
							// update the closure error
							showErrors();
						}
						actionMgr.execute(new MethodElementSetPropertyCommand(config, TOUCHED_BY_CONFIG_EDITOR, Boolean.TRUE.toString()));
					}
				});
				
			}
		};
		
		ICheckStateListener catsCheckStateListener = new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				
				BusyIndicator.showWhile(form.getDisplay(), new Runnable() {
					public void run() {
						saveContentCategorySelectionsToConfiguration();
						// update the closure error
						showErrors();
					}
				});
			}
		};
		
		configViewer.addCheckStateListener(configCheckStateListener);
		addCategoryViewer.addCheckStateListener(catsCheckStateListener);
		subCategoryViewer.addCheckStateListener(catsCheckStateListener);

		// listen to the library changes and automatically update the
		// configuration view
		libListener = new ILibraryChangeListener() {
			public void libraryChanged(int option, Collection<Object> changedItems) {
				// for performance reason, we should not response to every
				// library change
				// only cover package and plugin changes
				if (option == ILibraryChangeListener.OPTION_DELETED
						|| option == ILibraryChangeListener.OPTION_NEWCHILD
						|| option == ILibraryChangeListener.OPTION_NEWCHILD) {
					if (changedItems != null && changedItems.size() > 0) {
						Object o = changedItems.toArray()[0];
						if (o instanceof MethodPlugin
								|| o instanceof ProcessComponent
								|| o instanceof MethodPackage
								|| o instanceof CustomCategory) {
							reInitializeConfigFactory();
							refreshViewers();
//							updateCheckStates();
							showErrors();
						}
					}
				}
			}
		};

		ILibraryManager manager = LibraryService.getInstance().getCurrentLibraryManager();
		if (manager != null) {
			manager.addListener(libListener);
		}
		
		// listen to plugin presentation layout changes
		PluginUIPackageContext.INSTANCE.addListener(layoutListener);
	}

	protected void showErrors() {
		// save the previous invalid elements
		List<Object> invalid = closure.getInvalidElements();

		closure.checkError();
		
		if (configViewerHideUncheckedFilter.isHideUnchecked()) {
			configViewer.refresh();
		} else {
			// get the new error elements, add to the previous error elements,
			// and update them to update the error/warning images
			invalid.addAll(closure.getInvalidElements());

			// also add the UI folders
			ConfigPackageContentProvider cp = (ConfigPackageContentProvider) configViewer
					.getContentProvider();
			invalid.addAll(cp.getUIElements());

			configViewer.update(invalid.toArray(), null);
		}

	}

	
	/**
	 * Make configuration closure
	 *
	 */
	protected void makeClosure() {
		closure.fixErrors();
		refreshViewers();
//		updateCheckStates();
		saveConfiguration();

	}

	/**
	 * Fix all warnings 
	 */
	protected void fixWarning() {

		closure.fixProblems();
		refreshViewers();	
//		updateCheckStates();
		saveConfiguration();

	}

	/**
	 * Save configuration
	 * @return
	 * 		True if configuration is save successfully, false otherwise
	 */
	public boolean saveConfiguration() {
		ConfigurationData configData = LibraryService.getInstance()
							.getConfigurationManager(config)
									.getConfigurationData();
		
		configData.setEnableUpdate(false);
		boolean ret = doSaveConfiguration();
		configData.setEnableUpdate(true);

		return ret;
	}
	
	private boolean doSaveConfiguration() {

		boolean oldNotify = config.eDeliver();
	    try
	    {
	    	
	    	List<MethodPackage> oldPackages = new ArrayList<MethodPackage>(config.getMethodPackageSelection());
	    	List<MethodPlugin> oldPlugins = new ArrayList<MethodPlugin>(config.getMethodPluginSelection());
	    	
	    	Set<MethodPackage> newPackages = getCheckedMethodPackages(configViewer.getCheckedElements());
	    	Set<MethodPlugin> newPlugins = getCheckedMethodPlugins(configViewer.getCheckedElements());
	    	
	    	oldPackages.removeAll(newPackages);
	    	oldPlugins.removeAll(newPlugins);
	    	
	    	newPackages.removeAll(config.getMethodPackageSelection());
	    	newPlugins.removeAll(config.getMethodPluginSelection());
	    	
			if (ConfigurationUtil.removeCollFromMethodPluginList(actionMgr, config, oldPlugins) == false)
				return false;
			if (ConfigurationUtil.removeCollFromMethodPackageList(actionMgr, config, oldPackages) == false)
				return false;
			
			if (ConfigurationUtil.addCollToMethodPluginList(actionMgr, config, newPlugins) == false)
				return false;
			if (ConfigurationUtil.addCollToMethodPackageList(actionMgr, config, newPackages) == false)
				return false;
	
	    	// validate before save
			LibraryUtil.validateMethodConfiguration(actionMgr, config);

			actionMgr.execute(new MethodElementSetPropertyCommand(config, TOUCHED_BY_CONFIG_EDITOR, Boolean.TRUE.toString()));

			return true;
		
		} finally {
			config.eSetDeliver(oldNotify);
		}
	}
	
	/**
	 * Save configuration
	 * @return
	 * 		True if configuration is save successfully, false otherwise
	 */
	public boolean saveContentCategorySelectionsToConfiguration() {
		
    	List<ContentCategory> oldAddCats = new ArrayList<ContentCategory>(config.getAddedCategory());
    	List<ContentCategory> oldSubCats = new ArrayList<ContentCategory>(config.getSubtractedCategory());
    	
    	Set<ContentCategory> newAddCats = getCheckedContentCategories(addCategoryViewer.getCheckedElements());
    	Set<ContentCategory> newSubCats = getCheckedContentCategories(subCategoryViewer.getCheckedElements());
    	
    	oldAddCats.removeAll(newAddCats);
    	oldSubCats.removeAll(newSubCats);
    	
    	newAddCats.removeAll(config.getAddedCategory());
    	newSubCats.removeAll(config.getSubtractedCategory());
		
		if (ConfigurationUtil.removeCollFromAddedCategoryList(actionMgr, config, oldAddCats) == false)
			return false;
		
		if (!newAddCats.isEmpty()) {
			if (ConfigurationUtil.addCollToAddedCategoryList(actionMgr, config, newAddCats) == false)
				return false;
			
			Map<String, MethodElement> pluginMap = MethodElementUtil.buildMap(config.getMethodPluginSelection());
			HashSet<MethodPlugin> newAddedPlugins = new HashSet<MethodPlugin>();
			for (Iterator<ContentCategory> it = newAddCats.iterator(); it.hasNext(); ) {
				ContentCategory cat = it.next();
				MethodPlugin plugin = UmaUtil.getMethodPlugin(cat);
				if (! pluginMap.containsKey(plugin.getGuid())) {
					if (! newAddedPlugins.contains(plugin)) {
						newAddedPlugins.add(plugin);
					}
				}
			}
			if (! newAddedPlugins.isEmpty()) {
				if (ConfigurationUtil.addCollToMethodPluginList(actionMgr, config, newAddedPlugins) == false)
					return false;
				LibraryUtil.validateMethodConfiguration(actionMgr, config);
			}
			
		}
		if (ConfigurationUtil.removeCollFromSubtractedCategoryList(actionMgr, config, oldSubCats) == false)
			return false;
		
		if (ConfigurationUtil.addCollToSubtractedCategoryList(actionMgr, config, newSubCats) == false)
			return false;

		actionMgr.execute(new MethodElementSetPropertyCommand(config, TOUCHED_BY_CONFIG_EDITOR, Boolean.TRUE.toString()));

		return true;
	}
	
	protected Set<ContentCategory> getCheckedContentCategories(Object[] checkedItems) {
		Set<ContentCategory> result = new HashSet<ContentCategory>();
		for (int i = 0; i < checkedItems.length; i++) {
			Object item = TngUtil.unwrap(checkedItems[i]);
			if (item instanceof ContentCategory) {
//				if (config.getMethodPluginSelection().contains(LibraryUtil.getMethodPlugin((ContentCategory)item))) {
					result.add((ContentCategory)item);
//				}
			}
		}
		return result;
	}
	
	protected Set<MethodPackage> getCheckedMethodPackages(Object[] checkedItems) {
		Set<MethodPackage> result = new HashSet<MethodPackage>();
		for (int i = 0; i < checkedItems.length; i++) {
			Object item = TngUtil.unwrap(checkedItems[i]);
			if (item instanceof MethodPackage) {
				result.add((MethodPackage)item);
			}
		}
		return result;
	}
	
	protected Set<MethodPlugin> getCheckedMethodPlugins(Object[] checkedItems) {
		Set<MethodPlugin> result = new HashSet<MethodPlugin>();
		for (int i = 0; i < checkedItems.length; i++) {
			Object item = TngUtil.unwrap(checkedItems[i]);
			if (item instanceof MethodPlugin) {
				result.add((MethodPlugin)item);
			}
		}
		return result;
	}
	
	public void dispose() {
		super.dispose();

		if (libListener != null) {
			ILibraryManager manager = LibraryService.getInstance().getCurrentLibraryManager();
			if (manager != null) {
				manager.removeListener(libListener);
			}
		}
		
		if (layoutListener != null) {
			PluginUIPackageContext.INSTANCE.removeListener(layoutListener);
		}

		if (closure != null) {
			closure.dispose();
			closure = null;
		}
	}
	
	public void gotoMarker(IMarker marker) {
		// select problem in main viewer
		
		MethodElement e = ConfigurationMarkerHelper.INSTANCE.getErrorMethodElement(marker);

		if (e != null && configViewer != null) {
			configViewer.setSelection(new StructuredSelection(LibraryUtil.getSelectable(e)), true);
		}
	}
	
	public void doQuickFix(IMarker marker) {
		if ( marker == null ) {
			return;
		}
		MethodElement element = markerHelper.getCauseMethodElement(marker);	
		if ( element == null ) {
			return;
		}
		
		if ( closure.getConfigurationManager().getConfigurationData().isElementInSubtractedCategory(element)) {
			String message = AuthoringUIResources.bind(
					AuthoringUIResources.configurationPage_QuickfixError_reason1, 
					(new String[] {LibraryUtil.getTypeName(element)}));
			AuthoringUIPlugin.getDefault().getMsgDialog()
				.displayWarning(
						AuthoringUIResources.configurationPage_quickfixError_title, 
						message);
			return;
		}
		
		Object owner = LibraryUtil.getSelectable(element);
		configViewer.setChecked(owner, true);
		saveConfiguration();
		showErrors();					
   }

}
