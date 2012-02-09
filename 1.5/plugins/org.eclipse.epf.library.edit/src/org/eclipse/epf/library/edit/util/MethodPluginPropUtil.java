package org.eclipse.epf.library.edit.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.epf.library.edit.command.IActionManager;
import org.eclipse.epf.library.edit.uma.MethodElementExt;
import org.eclipse.epf.library.edit.uma.MethodPluginExt;
import org.eclipse.epf.uma.Constraint;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.MethodLibrary;
import org.eclipse.epf.uma.MethodPlugin;
import org.eclipse.epf.uma.UmaPackage;
import org.eclipse.epf.uma.WorkProduct;
import org.eclipse.epf.uma.ecore.impl.MultiResourceEObject.ExtendObject;
import org.eclipse.epf.uma.util.UmaUtil;


public class MethodPluginPropUtil extends PropUtil {

	public static final String PLUGIN_SynFree = "plugin_synFree"; //$NON-NLS-1$

	private static MethodPluginPropUtil methodPluginPropUtil = new MethodPluginPropUtil();
	public static MethodPluginPropUtil getMethodPluginPropUtil() {
		return methodPluginPropUtil;
	}
	
	public static MethodPluginPropUtil getMethodPluginPropUtil(IActionManager actionManager) {
		return new MethodPluginPropUtil(actionManager);
	}
	
	protected MethodPluginPropUtil() {		
	}
	
	protected MethodPluginPropUtil(IActionManager actionManager) {
		super(actionManager);
	}	
		
	public boolean isSynFree(MethodPlugin d) {
		Boolean value = getBooleanValue(d, PLUGIN_SynFree);
		return value == null ? false : value.booleanValue();
	}
	
	public void setSynFree(MethodPlugin d, boolean value) {
		setBooleanValue(d, PLUGIN_SynFree, value);
	}
	
	/**
	 * Find the state under the plug-in with the given name "stateName".
	 * If such state cannot be found, return null if "create" is false, otherwise create a new state
	 * under the plug-in and return it.
	 * 
	 * @param plugin
	 * @param stateName
	 * @param create
	 * @return the state
	 */
	public Constraint getWorkProductState(MethodPlugin plugin, String stateName, boolean create) {		
		return ConstraintManager.getWorkProductState(plugin, stateName, create, getActionManager());
	}
	
	/**
	 * Get all states created under the given "plugin".
	 * @param plugin
	 * @return the states
	 */
	public List<Constraint> getWorkProductStatesInPlugin(MethodPlugin plugin) {
		List<Constraint> list = new ArrayList<Constraint>();
		
		for (Iterator iter = plugin.getOwnedRules().iterator(); iter.hasNext();) {
			Constraint state = (Constraint) iter.next();
			if (state.getName().equals(ConstraintManager.Plugin_wpState)) {
				list.add(state);
			}
		}	
		
		return list;
	}
	
	/**
	 * Remove from the "plugin" a state with the given name "stateName".
	 * 
	 * @param plugin
	 * @param stateName
	 * @return
	 */
	public Constraint removeWorkProductState(MethodPlugin plugin,
			String stateName) {
		for (Iterator iter = plugin.getOwnedRules().iterator(); iter.hasNext();) {
			Constraint state = (Constraint) iter.next();
			if (state.getName().equals(ConstraintManager.Plugin_wpState) && state.getBody().equals(stateName)) {

				if (getActionManager() == null) {
					plugin.getOwnedRules().remove(state);
				}
				getActionManager().doAction(IActionManager.REMOVE, plugin,
						UmaPackage.eINSTANCE.getMethodElement_OwnedRules(),
						state, -1);
				return state;
			}
		}

		return null;
	}
	
	/**
	 * Get all states under the given "activePlugin"s library.
	 * If many states have a same name, only one of them will be returned in the list.
	 * If one of these same name states is in the "activePlugin", the method will make sure
	 * it is the one which gets returned in the list.
	 * 
	 * @param activePlugin
	 * @return
	 */
	public List<Constraint> getWorkProductStatesInLibrary(MethodPlugin activePlugin) {
		List<Constraint> resultList = getWorkProductStatesInPlugin(activePlugin);
		Set<String> stateIDs = new HashSet<String>();
		for (Constraint state : resultList) {
			stateIDs.add(getStateId(state));
		}
		MethodLibrary lib = UmaUtil.getMethodLibrary(activePlugin);
		for (MethodPlugin plugin : lib.getMethodPlugins()) {
			List<Constraint> list = getWorkProductStatesInPlugin(plugin);
			for (Constraint state : list) {
				String id = getStateId(state);
				if (! stateIDs.contains(id)) {
					stateIDs.add(id);
					resultList.add(state);
				}
			}
		}
		
		return resultList;
	}
	
	private String getStateId(Constraint state) {
		return state.getBody() + ", " + state.getBriefDescription();  //$NON-NLS-1$
	}
	
	@Override
	protected MethodElementExt createExtendObjectIfNeeded(MethodElement element, ExtendObject oldObj) {
		if (oldObj instanceof MethodPluginExt) {
			return (MethodPluginExt) oldObj;
		}
		if (element instanceof MethodPlugin) {
			return new MethodPluginExt((MethodPlugin) element, oldObj);
		}
		return super.createExtendObjectIfNeeded(element, oldObj);
	}
	
	private boolean isWpStatesLoaded(MethodPlugin plugin) {
		MethodPluginExt ext = (MethodPluginExt) getExtendObject(plugin, true);
		return ext.isWpStatesLoaded();
	}
	
	private void setWpStatesLoaded(MethodPlugin plugin, boolean wpStatesLoaded) {
		MethodPluginExt ext = (MethodPluginExt) getExtendObject(plugin, true);
		ext.setWpStatesLoaded(wpStatesLoaded);
	}
	
	public void loadWpStates(MethodPlugin plugin) {
		if (isWpStatesLoaded(plugin)) {
			return;
		}
		WorkProductPropUtil wpPropUtil = WorkProductPropUtil.getWorkProductPropUtil();
		
		for (WorkProduct wp : LibraryEditUtil.getInstance().getAllWorkProducts(plugin)) {
			wpPropUtil.getWorkProductStates(wp);
		}
		setWpStatesLoaded(plugin, true);
	}
	
}