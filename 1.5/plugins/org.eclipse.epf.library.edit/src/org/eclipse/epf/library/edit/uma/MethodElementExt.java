package org.eclipse.epf.library.edit.uma;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.epf.library.edit.util.Comparators;
import org.eclipse.epf.uma.Constraint;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.MethodPlugin;
import org.eclipse.epf.uma.WorkProduct;
import org.eclipse.epf.uma.ecore.impl.MultiResourceEObject.ExtendObject;
import org.eclipse.epf.uma.util.UmaUtil;

public class MethodElementExt extends ExtendObject {

	private MethodElement element;
	private boolean transientElement = false;
	private Object cachedObject;
	private ExtendReferenceMap extendReferenceMap;

	public MethodElementExt(MethodElement element) {
		this.element = element;
	}	
	
	public ExtendReferenceMap getExtendReferenceMap(boolean create) {
		if (create && extendReferenceMap == null) {
			extendReferenceMap = new ExtendReferenceMap();
		}
		return extendReferenceMap;
	}
	
	public Object getCachedObject() {
		return cachedObject;
	}

	public void setCachedObject(Object cachedObject) {
		this.cachedObject = cachedObject;
	}
	
	public MethodElement getElement() {
		return element;
	}
	
	public boolean isTransientElement() {
		return transientElement;
	}

	public void setTransientElement(boolean transientElement) {
		this.transientElement = transientElement;
	}
	
	public static class WorkProductStateExt extends MethodElementExt {
		private Set<WorkProduct> assignedToWps;
		
		public WorkProductStateExt(Constraint element) {
			super(element);
		}
		
		public void addToAssignedToWps(WorkProduct wp) {
			if (assignedToWps == null) {
				assignedToWps = new HashSet<WorkProduct>();
			}
			assignedToWps.add(wp);
		}
		
		public void removeFromAssignedToWps(WorkProduct wp) {
			if (assignedToWps == null) {
				return;
			}
			assignedToWps.remove(wp);
		}
		
		public List<WorkProduct> getAssignedToWorkProducts() {
			List<WorkProduct> list = new ArrayList<WorkProduct>();
			List<WorkProduct> list1 = new ArrayList<WorkProduct>();
			if (assignedToWps == null) {
				return list;
			}
			for (WorkProduct wp : assignedToWps) {
				if (UmaUtil.isInLibrary(wp)) {
					list.add(wp);
				} else {
					list1.add(wp);
				}
			}
			if (! list1.isEmpty()) {
				assignedToWps.removeAll(list1);
			}
			if (list.size() > 1) {
				Collections.sort(list, Comparators.PRESENTATION_NAME_COMPARATOR);
			}
			return list;
		}
		
	}

	public static class MethodPluginExt extends MethodElementExt {
		
		private boolean wpStatesLoaded = false;
		public MethodPluginExt(MethodPlugin plugin) {
			super(plugin);
		}	
		
		public boolean isWpStatesLoaded() {
			return wpStatesLoaded;
		}
		public void setWpStatesLoaded(boolean wpStatesLoaded) {
			this.wpStatesLoaded = wpStatesLoaded;
		}
	}
	
}
