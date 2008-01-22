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
package org.eclipse.epf.uma;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Descriptor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Descriptor is an abstract generalization for special Breakdown Elements that references one concrete Content Element.  A descriptor provides a representation of a Content Element within breakdown structures.  In addition to just referencing Content Elements it allows overriding the Content Elements structural relationships by defining its own sets of associations.
 * Descriptors are the key concept for realizing the separation of processes from method content.  A Descriptor can be characterized as a reference object for one particular Content Element, which has its own relationships and properties.  When a Descriptor is created it shall be provided with congruent copies of the relationships defined for the referenced content element.  However, a user can modify these relationships for the particular process situation for which the descriptor has been created. 
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epf.uma.Descriptor#getIsSynchronizedWithSource <em>Is Synchronized With Source</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epf.uma.UmaPackage#getDescriptor()
 * @model abstract="true"
 * @generated
 */
public interface Descriptor extends BreakdownElement {
	/**
	 * Returns the value of the '<em><b>Is Synchronized With Source</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Synchronized With Source</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Synchronized With Source</em>' attribute.
	 * @see #isSetIsSynchronizedWithSource()
	 * @see #unsetIsSynchronizedWithSource()
	 * @see #setIsSynchronizedWithSource(Boolean)
	 * @see org.eclipse.epf.uma.UmaPackage#getDescriptor_IsSynchronizedWithSource()
	 * @model default="true" unsettable="true" dataType="org.eclipse.epf.uma.Boolean" required="true" ordered="false"
	 * @generated
	 */
	Boolean getIsSynchronizedWithSource();

	/**
	 * Sets the value of the '{@link org.eclipse.epf.uma.Descriptor#getIsSynchronizedWithSource <em>Is Synchronized With Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Synchronized With Source</em>' attribute.
	 * @see #isSetIsSynchronizedWithSource()
	 * @see #unsetIsSynchronizedWithSource()
	 * @see #getIsSynchronizedWithSource()
	 * @generated
	 */
	void setIsSynchronizedWithSource(Boolean value);

	/**
	 * Unsets the value of the '{@link org.eclipse.epf.uma.Descriptor#getIsSynchronizedWithSource <em>Is Synchronized With Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetIsSynchronizedWithSource()
	 * @see #getIsSynchronizedWithSource()
	 * @see #setIsSynchronizedWithSource(Boolean)
	 * @generated
	 */
	void unsetIsSynchronizedWithSource();

	/**
	 * Returns whether the value of the '{@link org.eclipse.epf.uma.Descriptor#getIsSynchronizedWithSource <em>Is Synchronized With Source</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Is Synchronized With Source</em>' attribute is set.
	 * @see #unsetIsSynchronizedWithSource()
	 * @see #getIsSynchronizedWithSource()
	 * @see #setIsSynchronizedWithSource(Boolean)
	 * @generated
	 */
	boolean isSetIsSynchronizedWithSource();

} // Descriptor
