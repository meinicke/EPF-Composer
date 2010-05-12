package org.eclipse.epf.library.edit.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.epf.library.edit.command.IActionManager;
import org.eclipse.epf.uma.Constraint;
import org.eclipse.epf.uma.MethodLibrary;
import org.eclipse.epf.uma.MethodPlugin;
import org.eclipse.epf.uma.UmaPackage;
import org.eclipse.epf.uma.util.UmaUtil;


public class MethodPluginPropUtil extends MethodElementPropUtil {

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
			if (state.getName().equals(ConstraintManager.Plugin_wpState)) {

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
		Set<String> names = new HashSet<String>();
		for (Constraint state : resultList) {
			names.add(state.getBody());
		}
		MethodLibrary lib = UmaUtil.getMethodLibrary(activePlugin);
		for (MethodPlugin plugin : lib.getMethodPlugins()) {
			List<Constraint> list = getWorkProductStatesInPlugin(plugin);
			for (Constraint state : resultList) {
				if (! names.contains(state.getBody())) {
					names.add(state.getBody());
					resultList.add(state);
				}
			}
		}
		
		return resultList;
	}
	
}