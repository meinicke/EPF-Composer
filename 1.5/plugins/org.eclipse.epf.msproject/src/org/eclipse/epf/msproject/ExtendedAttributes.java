/**
 * <copyright>
 * </copyright>
 *
 * $Id: ExtendedAttributes.java,v 1.1 2008/01/15 08:52:45 jtham Exp $
 */
package org.eclipse.epf.msproject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extended Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epf.msproject.ExtendedAttributes#getExtendedAttribute <em>Extended Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epf.msproject.MsprojectPackage#getExtendedAttributes()
 * @model extendedMetaData="name='ExtendedAttributes_._type' kind='elementOnly'"
 * @generated
 */
public interface ExtendedAttributes extends EObject {
	/**
	 * Returns the value of the '<em><b>Extended Attribute</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epf.msproject.ExtendedAttribute}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Each of the individual entries in the extended attributes definition 
	 *                     collection.  There are no limits to the number of children that may appear, but project 
	 *                     will only understand Flag1-Flag10, etc.  This must appear once in each ExtendedAttributes 
	 *                     collection.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Extended Attribute</em>' containment reference list.
	 * @see org.eclipse.epf.msproject.MsprojectPackage#getExtendedAttributes_ExtendedAttribute()
	 * @model type="org.eclipse.epf.msproject.ExtendedAttribute" containment="true" resolveProxies="false"
	 *        extendedMetaData="kind='element' name='ExtendedAttribute' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getExtendedAttribute();

} // ExtendedAttributes
