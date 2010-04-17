package org.eclipse.epf.library.edit.uma;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.epf.uma.Descriptor;
import org.eclipse.epf.uma.MethodElement;

public class DescriptorExt extends MethodElementExt {

	private Descriptor greenParent;
	private Set<Descriptor> customizingChildSet;

	public DescriptorExt (Descriptor des) {
		super(des);
	}
	
	public void setGreenParent(Descriptor greenParent) {
		this.greenParent = greenParent;
	}
	
	public Descriptor getGreenParent() {
		return greenParent;
	}
	
	public Descriptor getDescriptor() {
		return (Descriptor) getElement();
	}
	
	public List<? extends Descriptor> getCustomizingChildren() {		
		List<Descriptor> children = new ArrayList<Descriptor>();
		if (customizingChildSet != null && !customizingChildSet.isEmpty()) {
			children.addAll(customizingChildSet);
		}
		return children;
	}
	
	public void addToCustomizingChildren(Descriptor child) {
		if (child == null) {
			return;
		}
		if (customizingChildSet == null) {
			customizingChildSet = new HashSet<Descriptor>();
		}
		customizingChildSet.add(child);
	}
		
}
