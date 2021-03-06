//------------------------------------------------------------------------------
// Copyright (c) 2005, 2006 IBM Corporation and others.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
// IBM Corporation - initial implementation
//------------------------------------------------------------------------------
package org.eclipse.epf.library.edit.itemsfilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.epf.library.edit.IConfigurable;
import org.eclipse.epf.library.edit.IConfigurator;
import org.eclipse.epf.library.edit.IFilter;
import org.eclipse.epf.library.edit.ILibraryItemProvider;
import org.eclipse.epf.library.edit.IStatefulItemProvider;
import org.eclipse.epf.library.edit.configuration.CategoriesItemProvider;
import org.eclipse.epf.library.edit.util.ModelStructure;
import org.eclipse.epf.library.edit.util.TngUtil;
import org.eclipse.epf.uma.DisciplineGrouping;
import org.eclipse.epf.uma.MethodConfiguration;
import org.eclipse.epf.uma.UmaPackage;

/**
 * The item provider adapter for a discipline grouping in the method element
 * selection dialogs.
 * 
 * @author Shashidhar Kannoori
 * @since 1.0
 */
public class DisciplineGroupingItemProvider extends
		org.eclipse.epf.uma.provider.DisciplineGroupingItemProvider implements
		IConfigurable, ILibraryItemProvider, IStatefulItemProvider {

	private IFilter filter;

	private IConfigurator configurator;

	private Object parent;

	/**
	 * Creates a new instance.
	 * 
	 * @param adapterFactory
	 */
	public DisciplineGroupingItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildrenFeatures(java.lang.Object)
	 */
	public Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			childrenFeatures = new ArrayList();
			childrenFeatures.add(UmaPackage.eINSTANCE
					.getDisciplineGrouping_Disciplines());
			// childrenFeatures.add(UmaPackage.eINSTANCE.getDiscipline());
		}
		return childrenFeatures;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildren(java.lang.Object)
	 */
	public Collection getChildren(Object object) {
		Collection children;
		Object parent = getParent(object);
		if (parent instanceof CategoriesItemProvider) {

			// TODO: commented out until Jinhua fixed bugs in
			// ConfigurationFilter
			if (configurator != null) {
				children = configurator.getChildren(object,
						UmaPackage.eINSTANCE
								.getDisciplineGrouping_Disciplines());
				if (children != null) {
					System.out
							.println("DisciplineGroupingItemProvider.getChildren(): returned from configurator: " + children); //$NON-NLS-1$
					return children;
				}
			}

			children = super.getChildren(object);
			DisciplineGrouping grouping = (DisciplineGrouping) object;
			MethodConfiguration methodConf = (MethodConfiguration) ((CategoriesItemProvider) getParent(object))
					.getTarget();
			TngUtil.addExtendedChildren(grouping, methodConf, children,
					ModelStructure.DEFAULT.disciplineDefinitionPath);

			// set parent
			//
			for (Iterator iter = children.iterator(); iter.hasNext();) {
				Object adapter = adapterFactory.adapt(iter.next(),
						ITreeItemContentProvider.class);
				if (adapter instanceof ILibraryItemProvider) {
					((ILibraryItemProvider) adapter).setParent(this);
				}
			}

			return children;
		} else {
			Collection childrenCol = super.getChildren(object);
			for (Iterator iter = childrenCol.iterator(); iter.hasNext();) {
				Object child = iter.next();
				if (filter != null)
					if (!filter.accept(child))
						iter.remove();
			}
			return childrenCol;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epf.library.edit.IConfigurable#setFilter(com.ibm.library.edit.IFilter)
	 */
	public void setFilter(IFilter filter) {
		this.filter = filter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epf.llibrary.edit.IConfigurable#setLabel(java.lang.String)
	 */
	public void setLabel(String label) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.epf.llibrary.edit.IConfigurable#setParent(java.lang.Object)
	 */
	public void setParent(Object parent) {
		this.parent = parent;
	}

	public Object getParent(Object object) {
		if (parent != null) {
			return parent;
		}
		// if(object instanceof EObject) {
		// MethodPlugin plugin = UmaUtil.getMethodPlugin((EObject) object);
		// if(plugin != null) {
		// String[] path = {
		// TngEditPlugin.INSTANCE.getString("_UI_Content_group") //$NON-NLS-1$
		// ,TngEditPlugin.INSTANCE.getString("_UI_Standard_Categories_group")
		// //$NON-NLS-1$
		// ,TngEditPlugin.INSTANCE.getString("_UI_Disciplines_group")
		// //$NON-NLS-1$
		// };
		// Object adapter =
		// TngAdapterFactory.INSTANCE.getItemsFilter_AdapterFactory(filter).adapt(plugin,
		// ITreeItemContentProvider.class);
		// // IGroupContainer groups = (IGroupContainer)
		// TngAdapterFactory.INSTANCE.getItemsFilter_AdapterFactory(filter).adapt(plugin,
		// ITreeItemContentProvider.class);
		// // int i;
		// // for(i = 0; i < path.length - 1; i++) {
		// // groups = (IGroupContainer) groups.getGroupItemProvider(path[i]);
		// // if(groups == null) return null;
		// // }
		// // return (ItemProviderAdapter) groups.getGroupItemProvider(path[i]);
		// return TngUtil.getAdapter(plugin, path);
		// //return adapter;
		// }
		// }

		return super.getParent(object);
	}

}
