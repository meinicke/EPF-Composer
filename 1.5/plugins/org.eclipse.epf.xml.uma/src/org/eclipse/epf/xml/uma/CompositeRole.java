/**
 * <copyright>
 * </copyright>
 *
 * $Id: CompositeRole.java,v 1.1 2008/01/15 08:52:07 jtham Exp $
 */
package org.eclipse.epf.xml.uma;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Role</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A special Role Descriptor that relates to more then one Role.  It represents a grouping of Roles with the main purpose of simplification, i.e. reducing the number of roles for a process.
 * A Composite Role is a grouping of Roles that can be used in an Activity or Process to reduce the number of Roles.  A typical application would be a process for a small team in which a standard set of roles from the method content would be all performed by one or more resource.  By using Composite Role the process would suggest a typical clustering of Roles to Resources.  A Composite Role could perform all Tasks defined for the Roles it refers to.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epf.xml.uma.CompositeRole#getGroup1 <em>Group1</em>}</li>
 *   <li>{@link org.eclipse.epf.xml.uma.CompositeRole#getAggregatedRole <em>Aggregated Role</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epf.xml.uma.UmaPackage#getCompositeRole()
 * @model extendedMetaData="name='CompositeRole' kind='elementOnly'"
 * @generated
 */
public interface CompositeRole extends RoleDescriptor {
	/**
	 * Returns the value of the '<em><b>Group1</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group1</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group1</em>' attribute list.
	 * @see org.eclipse.epf.xml.uma.UmaPackage#getCompositeRole_Group1()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:23'"
	 * @generated
	 */
	FeatureMap getGroup1();

	/**
	 * Returns the value of the '<em><b>Aggregated Role</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epf.xml.uma.Role}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Aggregated Role</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Aggregated Role</em>' containment reference list.
	 * @see org.eclipse.epf.xml.uma.UmaPackage#getCompositeRole_AggregatedRole()
	 * @model type="org.eclipse.epf.xml.uma.Role" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AggregatedRole' group='#group:23'"
	 * @generated
	 */
	EList getAggregatedRole();

} // CompositeRole