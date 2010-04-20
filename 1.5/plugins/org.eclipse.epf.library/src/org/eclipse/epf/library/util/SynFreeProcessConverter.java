package org.eclipse.epf.library.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epf.library.LibraryServiceUtil;
import org.eclipse.epf.library.configuration.ConfigurationHelper;
import org.eclipse.epf.library.configuration.DefaultElementRealizer;
import org.eclipse.epf.library.configuration.ElementRealizer;
import org.eclipse.epf.library.edit.util.DescriptorPropUtil;
import org.eclipse.epf.library.edit.util.LibraryEditUtil;
import org.eclipse.epf.library.edit.util.MethodLibraryPropUtil;
import org.eclipse.epf.library.edit.util.MethodPluginPropUtil;
import org.eclipse.epf.library.edit.util.ProcessPropUtil;
import org.eclipse.epf.library.edit.util.ProcessScopeUtil;
import org.eclipse.epf.library.edit.util.ProcessUtil;
import org.eclipse.epf.persistence.MultiFileXMIResourceImpl;
import org.eclipse.epf.services.ILibraryPersister;
import org.eclipse.epf.uma.ContentDescription;
import org.eclipse.epf.uma.ContentElement;
import org.eclipse.epf.uma.Deliverable;
import org.eclipse.epf.uma.Descriptor;
import org.eclipse.epf.uma.DescriptorDescription;
import org.eclipse.epf.uma.Guidance;
import org.eclipse.epf.uma.MethodConfiguration;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.MethodLibrary;
import org.eclipse.epf.uma.MethodPlugin;
import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.RoleDescriptor;
import org.eclipse.epf.uma.Task;
import org.eclipse.epf.uma.TaskDescriptor;
import org.eclipse.epf.uma.UmaPackage;
import org.eclipse.epf.uma.WorkProduct;
import org.eclipse.epf.uma.WorkProductDescriptor;

public class SynFreeProcessConverter {

	private boolean debug = true;

	private ElementRealizer realizer;

	private MethodConfiguration config;

	private ElementRealizer getRealizer() {
		return realizer;
	}

	private void setRealizer(ElementRealizer realizer) {
		this.realizer = realizer;
	}

	private Set<Resource> resouresToSave;

	private UmaPackage up = UmaPackage.eINSTANCE;

	public SynFreeProcessConverter() {
	}

	public SynFreeProcessConverter(MethodConfiguration config) {
		this.config = config;
	}

	private MethodConfiguration getConfig() {
		return config;
	}

	public void convertLibrary(MethodLibrary lib) {
		convertLibrary(lib, true);
	}

	public void convertLibrary(MethodLibrary lib, boolean toSave) {
		if (debug) {
			System.out.println("LD> Begin convertLibrary: " + lib);
			System.out.println("");
		}
		convertLibrary_(lib, toSave);
		if (debug) {
			System.out.println("LD> End convertLibrary: " + lib);
			System.out.println("");
		}
	}

	private void convertLibrary_(MethodLibrary lib, boolean toSave) {
		if (lib == null) {
			return;
		}
		List<MethodPlugin> plugins = lib.getMethodPlugins();

		if (toSave) {
			resouresToSave = new HashSet<Resource>();
		}
		for (int i = 0; i < plugins.size(); i++) {
			convertPlugin(plugins.get(i), false);
		}

		MethodLibraryPropUtil propUtil = MethodLibraryPropUtil
				.getMethodLibraryPropUtil();
		propUtil.setSynFree(lib, true);
		if (toSave) {
			resouresToSave.add(lib.eResource());
			save();
		}
	}

	public void convertPlugin(MethodPlugin plugin, boolean toSave) {
		if (plugin == null) {
			return;
		}
		if (debug) {
			System.out.println("LD> convertPlugin: " + plugin);
			System.out.println("");
		}
		if (toSave) {
			resouresToSave = new HashSet<Resource>();
		}
		Set<Process> processes = LibraryEditUtil.getInstance()
				.collectProcesses(plugin);
		for (Process proc : processes) {
			convertProcess(proc, false);
		}

		MethodPluginPropUtil propUtil = MethodPluginPropUtil
				.getMethodPluginPropUtil();
		propUtil.setSynFree(plugin, true);
		if (toSave) {
			save();
		}
	}

	public void convertProcess(Process proc, boolean toSave) {
		if (proc == null) {
			return;
		}
		if (proc.getDefaultContext() == null) {
			ProcessScopeUtil.getInstance().loadScope(proc);
		}
		if (proc.getDefaultContext() == null) {
			return;
		}
		if (debug) {
			System.out.println("LD> convertProcess: " + proc);
			System.out.println("");
		}

		MethodConfiguration c = getConfig();
		if (c == null) {
			c = proc.getDefaultContext();
		}
		ElementRealizer r = DefaultElementRealizer.newElementRealizer(c);
		setRealizer(r);

		Set<Descriptor> descriptors = LibraryEditUtil.getInstance()
				.collectDescriptors(proc);

		if (toSave) {
			resouresToSave = new HashSet<Resource>();
		}
		for (Descriptor des : descriptors) {
			convert(des);
		}
		ProcessPropUtil.getProcessPropUtil().setSynFree(proc, true);

		if (toSave) {
			save();
		}
	}

	private void convert(Descriptor des) {
		boolean oldDeliver = des.eDeliver();
		try {
			if (oldDeliver) {
				des.eSetDeliver(false);
			}
			convert_(des);
		} finally {
			if (oldDeliver) {
				des.eSetDeliver(oldDeliver);
			}
		}
	}

	private void convert_(Descriptor des) {
		Resource res = des.eResource();
		if (res == null) {
			return;
		}
		if (debug) {
			System.out.println("LD> convert: " + des);
		}

		if (des instanceof TaskDescriptor) {
			convertTd((TaskDescriptor) des);

		} else if (des instanceof RoleDescriptor) {
			convertRd((RoleDescriptor) des);

		} else if (des instanceof WorkProductDescriptor) {
			convertWpd((WorkProductDescriptor) des);

		}

		convertGuidances(des);
		
		convertTextAttributes(des);

		if (resouresToSave != null) {
			resouresToSave.add(res);
		}
	}

	private void convertTextAttributes(Descriptor des) {
		DescriptorPropUtil propUtil = DescriptorPropUtil.getDesciptorPropUtil();
		ContentElement element = (ContentElement) propUtil.getLinkedElement(des);
		if (element == null) {
			return;
		}

		if (propUtil.hasNoValue(des.getName())) {
			des.setName(element.getName());
		} else if (!des.getName().equals(element.getName())) {
			propUtil.setNameRepalce(des, true);
		}

		String elementStrValue = ConfigurationHelper.getPresentationName(element, getConfig());
		if (propUtil.hasNoValue(des.getPresentationName())) {
			des.setPresentationName(element.getPresentationName());
		} else if (!des.getPresentationName().equals(
				element.getPresentationName())) {
			propUtil.setPresentationNameRepalce(des, true);
		}

		elementStrValue = (String) ConfigurationHelper
				.calcAttributeFeatureValue(element, up
						.getMethodElement_BriefDescription(), getConfig());
		if (propUtil.hasNoValue(des.getBriefDescription())) {
			des.setBriefDescription(elementStrValue);
		} else if (!des.getBriefDescription().equals(
				elementStrValue)) {
			propUtil.setBriefDesRepalce(des, true);
		}
		
		ContentDescription ePrentation = element.getPresentation();
		DescriptorDescription dPrentation = (DescriptorDescription) des
				.getPresentation();

		boolean oldDeliver = dPrentation.eDeliver();
		try {
			if (oldDeliver) {
				dPrentation.eSetDeliver(false);
			}

			elementStrValue = (String) ConfigurationHelper
					.calcAttributeFeatureValue(ePrentation, element, up
							.getContentDescription_MainDescription(),
							getConfig());
			if (propUtil.hasNoValue(dPrentation.getRefinedDescription())) {
				dPrentation.setRefinedDescription(elementStrValue);
			} else if (!dPrentation.getRefinedDescription().equals(
					elementStrValue)) {
				propUtil.setMainDesRepalce(des, true);
			}

			elementStrValue = (String) ConfigurationHelper
					.calcAttributeFeatureValue(ePrentation, element, up
							.getContentDescription_KeyConsiderations(),
							getConfig());
			if (propUtil.hasNoValue(dPrentation.getKeyConsiderations())) {
				dPrentation.setKeyConsiderations(elementStrValue);
			} else if (!dPrentation.getKeyConsiderations().equals(
					elementStrValue)) {
				propUtil.setKeyConsiderRepalce(des, true);
			}

		} finally {
			if (oldDeliver) {
				dPrentation.eSetDeliver(oldDeliver);
			}
		}

	}

	private void convertTd(TaskDescriptor td) {
		Task task = (Task) getLinkedElement(td);
		if (task == null) {
			return;
		}

		convertManyEReference(td, task, up
				.getTaskDescriptor_PerformedPrimarilyBy(), up
				.getTaskDescriptor_PerformedPrimarilyByExcluded(), up
				.getTask_PerformedBy());

		convertManyEReference(td, task, up
				.getTaskDescriptor_AdditionallyPerformedBy(), up
				.getTaskDescriptor_AdditionallyPerformedByExclude(), up
				.getTask_AdditionallyPerformedBy());

		convertManyEReference(td, task, up.getTaskDescriptor_MandatoryInput(),
				up.getTaskDescriptor_MandatoryInputExclude(), up
						.getTask_MandatoryInput());

		convertManyEReference(td, task, up.getTaskDescriptor_OptionalInput(),
				up.getTaskDescriptor_OptionalInputExclude(), up
						.getTask_OptionalInput());

		convertManyEReference(td, task, up.getTaskDescriptor_Output(), up
				.getTaskDescriptor_OutputExclude(), up.getTask_Output());

		convertManyEReference(td, task, up.getTaskDescriptor_SelectedSteps(),
				up.getTaskDescriptor_SelectedStepsExclude(), up.getTask_Steps());

	}

	private void convertManyEReference(Descriptor ownerDescriptor,
			MethodElement ownerLinkedElement, EReference dFeature,
			EReference dFeatureExclude, EReference efeature) {
		DescriptorPropUtil propUtil = DescriptorPropUtil.getDesciptorPropUtil();

		List<MethodElement> elements = ConfigurationHelper.calc0nFeatureValue(
				ownerLinkedElement, efeature, getRealizer());

		List<MethodElement> descriptors = ConfigurationHelper
				.calc0nFeatureValue(ownerDescriptor, dFeature, getRealizer());

		// exclude list and local list
		List<MethodElement> excludeList = (List<MethodElement>) ownerDescriptor
				.eGet(dFeatureExclude);
		List<Descriptor> localList = new ArrayList<Descriptor>();
		Set<MethodElement> elementSet = new HashSet<MethodElement>();

		if (elements != null && !elements.isEmpty()) {
			elementSet.addAll(elements);
		}

		List<MethodElement> linkedElements = new ArrayList<MethodElement>();
		Set<MethodElement> linkedElementSet = new HashSet<MethodElement>();

		if (descriptors != null) {
			for (MethodElement des : descriptors) {
				if (des instanceof Descriptor) {
					Descriptor d = (Descriptor) des;
					MethodElement linkedElement = getLinkedElement(d);
					if (linkedElement == null) {
						localList.add(d);
					} else {
						linkedElementSet.add(linkedElement);
						if (elementSet.contains(linkedElement)) {
							propUtil.setCreatedByReference(d, true);
						} else {
							localList.add(d);
						}
					}
				} else {
					linkedElementSet.addAll(descriptors);
					break;
				}
			}
		}

		for (MethodElement element : elements) {
			if (!linkedElementSet.contains(element)) {
				excludeList.add(element);
			}
		}

		// Handle localList
		for (Descriptor des : localList) {
			propUtil.addLocalUse(des, ownerDescriptor, dFeature);
		}
	}

	private void convertGuidances(Descriptor des) {
		MethodElement element = ProcessUtil.getAssociatedElement(des);
		if (element == null) {
			return;
		}

		// if (des.getGuid().equals("_kF8tYTbsEduMn613sF6-Uw")) {
		// System.out.println("");
		// }

		Map<EReference, EReference> refMap = LibraryEditUtil.getInstance()
				.getGuidanceRefMap(getLinkedElementType(des));

		Set<Guidance> elementGuidanceSet = new HashSet<Guidance>();
		Set<Guidance> descripGuidanceSet = new HashSet<Guidance>();
		for (Map.Entry<EReference, EReference> entry : refMap.entrySet()) {
			EReference elementRef = entry.getKey();
			EReference descripRef = entry.getValue();
			Object elementValue = ConfigurationHelper.calc0nFeatureValue(
					element, elementRef, getRealizer());
			Object descripValue = ConfigurationHelper.calc0nFeatureValue(
					des, descripRef, getRealizer());
			if (elementValue instanceof List) {
				List list = (List) elementValue;
				if (!list.isEmpty()) {
					elementGuidanceSet.addAll((List) elementValue);
				}
			}
			if (descripValue instanceof List) {
				List list = (List) descripValue;
				if (!list.isEmpty()) {
					descripGuidanceSet.addAll((List) descripValue);
				}
			}
		}
		for (Guidance g : elementGuidanceSet) {
			if (!descripGuidanceSet.contains(g)) {
				des.getGuidanceExclude().add(g);
			}
		}
		for (Guidance g : descripGuidanceSet) {
			if (!elementGuidanceSet.contains(g)) {
				des.getGuidanceAdditional().add(g);
			}
		}
	}	
	
	private void convertRd(RoleDescriptor rd) {
		Role role = (Role) getLinkedElement(rd);
		if (role == null) {
			return;
		}

		convertManyEReference(rd, role, up.getRoleDescriptor_ResponsibleFor(),
				up.getRoleDescriptor_ResponsibleForExclude(), up
						.getRole_ResponsibleFor());
	}

	private void convertWpd(WorkProductDescriptor wpd) {
		WorkProduct wp = (WorkProduct) getLinkedElement(wpd);
		if (wp == null) {
			return;
		}

		if (wp instanceof Deliverable) {
			convertManyEReference(wpd, wp, up
					.getWorkProductDescriptor_DeliverableParts(), up
					.getWorkProductDescriptor_DeliverablePartsExclude(), up
					.getDeliverable_DeliveredWorkProducts());
		}
	}

	private void save() {
		ILibraryPersister.FailSafeMethodLibraryPersister persister = LibraryServiceUtil
				.getCurrentPersister().getFailSafePersister();

		try {
			for (Iterator<Resource> it = resouresToSave.iterator(); it
					.hasNext();) {
				MultiFileXMIResourceImpl res = (MultiFileXMIResourceImpl) it
						.next();
				persister.save(res);
			}
			persister.commit();

		} catch (Exception e) {
			persister.rollback();
			e.printStackTrace();

		} finally {

		}
	}

	private MethodElement getLinkedElement(Descriptor des) {
		MethodElement element = DescriptorPropUtil.getDesciptorPropUtil()
				.getLinkedElement(des);
		if (element == null) {
			return null;
		}
		return ConfigurationHelper.getCalculatedElement(element, getRealizer());
	}

	public static EClass getLinkedElementType(Descriptor des) {
		if (des instanceof TaskDescriptor) {
			return UmaPackage.eINSTANCE.getTask();
		}
		if (des instanceof RoleDescriptor) {
			return UmaPackage.eINSTANCE.getRole();
		}
		if (des instanceof WorkProductDescriptor) {
			return UmaPackage.eINSTANCE.getWorkProduct();
		}
	
		throw new UnsupportedOperationException();
	}
	
}
