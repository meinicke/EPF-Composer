//------------------------------------------------------------------------------
// Copyright (c) 2005, 2008 IBM Corporation and others.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
// IBM Corporation - initial implementation
//------------------------------------------------------------------------------
package org.eclipse.epf.library.edit.configuration;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.epf.library.edit.IConfigurable;
import org.eclipse.epf.library.edit.IFilter;
import org.eclipse.epf.library.edit.ILibraryItemProvider;
import org.eclipse.epf.library.edit.LibraryEditPlugin;
import org.eclipse.epf.library.edit.PresentationContext;
import org.eclipse.epf.library.edit.util.TngUtil;
import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.Checklist;
import org.eclipse.epf.uma.Concept;
import org.eclipse.epf.uma.ContentCategory;
import org.eclipse.epf.uma.DescribableElement;
import org.eclipse.epf.uma.Example;
import org.eclipse.epf.uma.FulfillableElement;
import org.eclipse.epf.uma.Guidance;
import org.eclipse.epf.uma.Practice;
import org.eclipse.epf.uma.Report;
import org.eclipse.epf.uma.ReusableAsset;
import org.eclipse.epf.uma.Roadmap;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.SupportingMaterial;
import org.eclipse.epf.uma.Task;
import org.eclipse.epf.uma.Template;
import org.eclipse.epf.uma.TermDefinition;
import org.eclipse.epf.uma.ToolMentor;
import org.eclipse.epf.uma.UmaPackage;
import org.eclipse.epf.uma.WorkProduct;

/**
 * The item provider adapter for a practice.
 * 
 * @author Weiping Lu
 * @since 1.5
 */
public class PracticeItemProvider extends
		org.eclipse.epf.uma.provider.PracticeItemProvider implements
		IConfigurable, ILibraryItemProvider {

	private IFilter filter;

	private Object parent;

	private String label;
	
	private static String ROADMAP = "roadmap"; //$NON-NLS-1$
	private static String CATEGORIES = "categories"; //$NON-NLS-1$
	private static String UNKNOWN = "unknown"; //$NON-NLS-1$

	public PracticeItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildren(java.lang.Object)
	 */
	public Collection<?> getChildren(Object object) {
		return Collections.EMPTY_LIST;
	}
	
	/**
	 * @param children
	 * @return
	 */
	public Collection<?> getModifiedChildren(Object parentObject, Collection children) {
		List ret = new ArrayList();

		GroupingHelper groupingHelper = new GroupingHelper();
		grouping(parentObject, ret, children, groupingHelper);
		
		return ret;
	}
	
	private void grouping(Object parentObject, List ret, Collection children, 
			GroupingHelper groupingHelper) {
		Map<String, List> map = getSubGroupMap(children, groupingHelper);
		
		String[] keys = groupingHelper.getKeysInOrder();
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			List subgroupChildren = map.get(key);
			if (subgroupChildren == null || subgroupChildren.isEmpty()) {
				continue;
			}
			if (groupingHelper.toGroup(key, subgroupChildren)) {
				subgroupChildren = groupingHelper.nestedGrouping(parentObject, key, subgroupChildren);
				PracticeSubgroupItemProvider sub = new PracticeSubgroupItemProvider(
						getAdapterFactory(), key, getImageObject(key), subgroupChildren, parentObject);
				ret.add(sub);
			} else {
				sort(subgroupChildren);
				ret.addAll(subgroupChildren);
			}
		}
	}

	private static void sort(List subgroupChildren) {
		if (subgroupChildren.size() > 1) {
			Comparator comparator = PresentationContext.INSTANCE.getPresNameComparator();
			Collections.<FulfillableElement>sort(subgroupChildren, comparator);
		}
	}	
	
	private Map<String, List> getSubGroupMap(Collection children, GroupingHelper groupingHelper) {
		 Map<String, List> map = new LinkedHashMap<String, List>(); 
		
		for (Object child: children) {
			String key = groupingHelper.getSubGroupName(child);
			add(map, key, child);
		}				
		
		return map;
	}
	
	private Object getImageObject(String subGroupName) {
		
		String imageStr = "full/obj16/Folder"; //$NON-NLS-1$
		
		if (false) {		//Not used for now
			if (subGroupName.equals(getUIString("_UI_Key_Concepts"))) { //$NON-NLS-1$
				imageStr = "full/obj16/Concepts"; //$NON-NLS-1$
			} else if (subGroupName.equals(getUIString("_UI_WorkProducts_group"))) { //$NON-NLS-1$
				imageStr = "full/obj16/WorkProducts"; //$NON-NLS-1$
			} else if (subGroupName.equals(getUIString("_UI_Tasks_group"))) { //$NON-NLS-1$
				imageStr = "full/obj16/Tasks"; //$NON-NLS-1$
			} else if (subGroupName.equals(getUIString("_UI_Roles_group"))) { //$NON-NLS-1$
				imageStr = "full/obj16/Roles"; //$NON-NLS-1$
			} else if (subGroupName.equals(getUIString("_UI_Activities_group"))) { //$NON-NLS-1$
				imageStr = "full/obj16/Processes"; //$NON-NLS-1$
			} else if (subGroupName.equals(getUIString("_UI_Guidances_group"))) { //$NON-NLS-1$
				imageStr = "full/obj16/GuidanceFolder"; //$NON-NLS-1$
				
				//Guidance sub groups
			} else if (subGroupName.equals(getUIString("_UI_Guidances_Checklists"))) { //$NON-NLS-1$
				imageStr = "full/obj16/Checklists"; //$NON-NLS-1$
			} else if (subGroupName.equals(getUIString("_UI_Guidances_Examples"))) { //$NON-NLS-1$
				imageStr = "full/obj16/Examples"; //$NON-NLS-1$
			} else if (subGroupName.equals(getUIString("_UI_Guidances_Practices"))) { //$NON-NLS-1$
				imageStr = "full/obj16/Practices"; //$NON-NLS-1$
			} else if (subGroupName.equals(getUIString("_UI_Guidances_Reports"))) { //$NON-NLS-1$
				imageStr = "full/obj16/Reports"; //$NON-NLS-1$
			} else if (subGroupName.equals(getUIString("_UI_Guidances_ReusableAssets"))) { //$NON-NLS-1$
				imageStr = "full/obj16/ReusableAssets"; //$NON-NLS-1$
			} else if (subGroupName.equals(getUIString("_UI_Guidances_SupportingMaterials"))) { //$NON-NLS-1$
				imageStr = "full/obj16/SupportingMaterials"; //$NON-NLS-1$
			} else if (subGroupName.equals(getUIString("_UI_Guidances_Templates"))) { //$NON-NLS-1$
				imageStr = "full/obj16/Templates"; //$NON-NLS-1$
			} else if (subGroupName.equals(getUIString("_UI_Guidances_ToolMentors"))) { //$NON-NLS-1$
				imageStr = "full/obj16/ToolMentors"; //$NON-NLS-1$
			}
		}
	
		return imageStr == null ? null : LibraryEditPlugin.INSTANCE.getImage(imageStr);
	}
	
	private static String getUIString(String key) {
		return LibraryEditPlugin.INSTANCE.getString(key);
	}

	private static void add(Map<String, List> map, String key, Object value) {
		List list = map.get(key);
		if (list == null) {
			list = new ArrayList();
			map.put(key, list);
		}
		list.add(value);
	}
	
	public boolean hasChildren(Object object) {
		return true;
	}

	public Collection getNewChildDescriptors(Object object,
			EditingDomain editingDomain, Object sibling) {
		return Collections.EMPTY_LIST;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epf.uma.provider.PracticeItemProvider#getChildrenFeatures(java.lang.Object)
	 */
	public Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			childrenFeatures = new ArrayList();
			childrenFeatures
					.add(UmaPackage.Literals.PRACTICE__CONTENT_REFERENCES);
			childrenFeatures
					.add(UmaPackage.Literals.PRACTICE__ACTIVITY_REFERENCES);
			childrenFeatures.add(UmaPackage.Literals.PRACTICE__SUB_PRACTICES);
		}
		return childrenFeatures;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getParent(java.lang.Object)
	 */
	public Object getParent(Object object) {
		Object parent = TngUtil
				.getNavigatorParentItemProvider((Guidance) object);
		if (parent == null) {
			return super.getParent(object);
		}
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.library.edit.IConfigurable#setFilter(com.ibm.library.edit.IFilter)
	 */
	public void setFilter(IFilter filter) {
		this.filter = filter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.library.edit.IConfigurable#setParent(java.lang.Object)
	 */
	public void setParent(Object parent) {
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.library.edit.IConfigurable#setLabel(java.lang.String)
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getText(java.lang.Object)
	 */
	public String getText(Object object) {
		return TngUtil.getLabel(object, getString("_UI_Practice_type")); //$NON-NLS-1$
	}

	public Object getImage(Object object) {
		if (object instanceof DescribableElement) {
			if (((DescribableElement) object).getNodeicon() != null) {
				URI imgUri = TngUtil.getFullPathofNodeorShapeIconURI(
						(DescribableElement) object,
						((DescribableElement) object).getNodeicon());
				Object image = LibraryEditPlugin.INSTANCE
						.getSharedImage(imgUri);
				if (image != null)
					return image;
			}
		}
		return super.getImage(object);
	}
	
	class GroupingHelper {
		protected String getSubGroupName(Object obj) {
			if (obj instanceof Roadmap) {
				return ROADMAP;
			}
			if (obj instanceof Concept) {
				return getUIString("_UI_Key_Concepts"); //$NON-NLS-1$
			}
			if (obj instanceof WorkProduct) {
				return getUIString("_UI_WorkProducts_group"); //$NON-NLS-1$
			}
			if (obj instanceof Task) {
				return getUIString("_UI_Tasks_group"); //$NON-NLS-1$
			}
			if (obj instanceof Role) {
				return getUIString("_UI_Roles_group"); //$NON-NLS-1$
			}
			if (obj instanceof Activity) {
				return getUIString("_UI_Activities_group"); //$NON-NLS-1$
			}
			if (obj instanceof Guidance) {			
				return getUIString("_UI_Guidances_group"); //$NON-NLS-1$
			}
			if (obj instanceof ContentCategory) {			
				return CATEGORIES;
			}
			
			return UNKNOWN;
		}
		
		protected String[] getKeysInOrder() {
			String[] keys = {
					ROADMAP,
					getUIString("_UI_Key_Concepts"),	//$NON-NLS-1$
					getUIString("_UI_WorkProducts_group"),	//$NON-NLS-1$
					getUIString("_UI_Tasks_group"),			//$NON-NLS-1$
					getUIString("_UI_Activities_group"),	//$NON-NLS-1$
					getUIString("_UI_Roles_group"),			//$NON-NLS-1$
					getUIString("_UI_Guidances_group"),		//$NON-NLS-1$
					CATEGORIES,
					UNKNOWN
			};
			
			return keys;
		}
		
		protected boolean toGroup(String key, List subgroupChildren) {
			if (key.equals(ROADMAP) || 
				key.equals(CATEGORIES) ||
				key.equals(UNKNOWN) ||
				subgroupChildren.size() < 3) {
				return false;
			}
			return true;
		}
		
		protected List<?> nestedGrouping(Object parentObject, String key, List<?> subgroupChildren) {
			if (! key.equals(PracticeItemProvider.getUIString("_UI_Guidances_group"))) {
				return subgroupChildren;
			}
			
			List ret = new ArrayList<Object>();
			
			GroupingHelper groupingHelper = new GuidanceGroupingHelper();
			grouping(parentObject, ret, subgroupChildren, groupingHelper);
			
			return ret;
		}
	}
	
	class GuidanceGroupingHelper extends GroupingHelper {
		protected String getSubGroupName(Object obj) {
			
			if (obj instanceof Checklist) {
				return getUIString("_UI_Guidances_Checklists"); //$NON-NLS-1$
			}
			if (obj instanceof Example) {
				return getUIString("_UI_Guidances_Examples"); //$NON-NLS-1$
			}
			if (obj instanceof Practice) {
				return getUIString("_UI_Guidances_Practices"); //$NON-NLS-1$
			}
			if (obj instanceof Report) {
				return getUIString("_UI_Guidances_Reports"); //$NON-NLS-1$
			}
			
			if (obj instanceof ReusableAsset) {
				return getUIString("_UI_Guidances_ReusableAssets"); //$NON-NLS-1$
			}
			if (obj instanceof SupportingMaterial) {
				return getUIString("_UI_Guidances_SupportingMaterials"); //$NON-NLS-1$
			}
			if (obj instanceof Template) {
				return getUIString("_UI_Guidances_Templates"); //$NON-NLS-1$
			}
			if (obj instanceof TermDefinition) {
				return getUIString("_UI_Guidances_TermDefinitions"); //$NON-NLS-1$
			}
			
			if (obj instanceof ToolMentor) {
				return getUIString("_UI_Guidances_ToolMentors"); //$NON-NLS-1$
			}
			
			return UNKNOWN;
			
		}
		
		protected String[] getKeysInOrder() {
			String[] keys = {
					getUIString("_UI_Guidances_Checklists"),	//$NON-NLS-1$
					getUIString("_UI_Guidances_Examples"),	//$NON-NLS-1$
					getUIString("_UI_Guidances_Practices"),			//$NON-NLS-1$
					getUIString("_UI_Guidances_Reports"),	//$NON-NLS-1$
					getUIString("_UI_Guidances_ReusableAssets"),			//$NON-NLS-1$
					getUIString("_UI_Guidances_SupportingMaterials"),		//$NON-NLS-1$
					getUIString("_UI_Guidances_Templates"),		//$NON-NLS-1$
					getUIString("_UI_Guidances_ToolMentors"),		//$NON-NLS-1$
					UNKNOWN				
			};
			
			return keys;
		}
		
		protected boolean toGroup(String key, List subgroupChildren) {
			if (key.equals(UNKNOWN) || 
				subgroupChildren.size() < 3) {
				return false;
			}
			return true;
		}
		
		protected List<?> nestedGrouping(Object parentObject, String key, List<?> subgroupChildren) {
			return subgroupChildren;
		}

	}

}