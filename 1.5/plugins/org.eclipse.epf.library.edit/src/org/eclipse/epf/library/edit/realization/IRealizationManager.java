package org.eclipse.epf.library.edit.realization;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.Process;

public interface IRealizationManager {
	
	/**
	 * @param element
	 * @return
	 */
	IRealizedElement getRealizedElement(MethodElement element);
	
	/**
	 * @param element
	 * @return
	 */
	IRealizedElement removeRealizedElement(MethodElement element);
	
	/**
	 * Update process model with realization
	 */
	void updateProcessModel(Process proc);
	
	/**
	 * Update activity model with realization
	 */
	void updateActivityModel(Activity act);
	
	/**
	 * Called at the beginning of publishing
	 */
	void beginPublish();
	
	/**
	 * Called at the end of publishing
	 */
	void endPublish();
	
	boolean debug = true;
	
	boolean timing = true;
	
}
