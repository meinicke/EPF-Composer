package org.eclipse.epf.library.edit.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.epf.library.edit.command.IActionManager;
import org.eclipse.epf.uma.Constraint;
import org.eclipse.epf.uma.WorkProduct;

public class WorkProductPropUtil extends MethodElementPropUtil {

	private static WorkProductPropUtil workProductPropUtil = new WorkProductPropUtil();
	public static WorkProductPropUtil getWorkProductPropUtil() {
		return workProductPropUtil;
	}
	
	public static WorkProductPropUtil getWorkProductPropUtil(IActionManager actionManager) {
		return new WorkProductPropUtil(actionManager);
	}
		
	protected WorkProductPropUtil() {		
	}
	
	protected WorkProductPropUtil(IActionManager actionManager) {
		super(actionManager);
	}
	
	public Set<Constraint> getAllStates(WorkProduct wp) {
		Set<WorkProduct> wpSet = new HashSet<WorkProduct>();
		wpSet.add(wp);
		//To collect all variability related wps into wps
		
		Set<Constraint> stateSet = new HashSet<Constraint>();

		for (WorkProduct w : wpSet) {
			List<Constraint> stateList = ConstraintManager.getWorkProductStates(wp);
			stateSet.addAll(stateList);
		}
		
		return stateSet;
	}
	
	public Constraint getState(WorkProduct wp, String stateName, boolean create) {		
		return ConstraintManager.getConstraint(wp, stateName, create);
	}
	
	public String getStateName(Constraint state) {
		if (state != null && state.getName().equals(ConstraintManager.WORKPRODUCT_STATE)) {
			return state.getBody();
		}
		
		return null;
	}
	
}