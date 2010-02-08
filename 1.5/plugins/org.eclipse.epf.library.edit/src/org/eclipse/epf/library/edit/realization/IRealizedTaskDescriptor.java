package org.eclipse.epf.library.edit.realization;

import java.util.List;

import org.eclipse.epf.uma.RoleDescriptor;
import org.eclipse.epf.uma.WorkProductDescriptor;

public interface IRealizedTaskDescriptor  extends IRealizedDescriptor {

	List<RoleDescriptor> getPerformedPrimarilyBy();
	
	List<WorkProductDescriptor> getMandatoryInput();
}