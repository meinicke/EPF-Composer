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
 * A representation of the model object '<em><b>Work Product Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epf.uma.WorkProductDescription#getPurpose <em>Purpose</em>}</li>
 *   <li>{@link org.eclipse.epf.uma.WorkProductDescription#getImpactOfNotHaving <em>Impact Of Not Having</em>}</li>
 *   <li>{@link org.eclipse.epf.uma.WorkProductDescription#getReasonsForNotNeeding <em>Reasons For Not Needing</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epf.uma.UmaPackage#getWorkProductDescription()
 * @model
 * @generated
 */
public interface WorkProductDescription extends ContentDescription {
	/**
	 * Returns the value of the '<em><b>Purpose</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Describes why the work product is produced and to what use it will be put.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Purpose</em>' attribute.
	 * @see #isSetPurpose()
	 * @see #unsetPurpose()
	 * @see #setPurpose(String)
	 * @see org.eclipse.epf.uma.UmaPackage#getWorkProductDescription_Purpose()
	 * @model default="" unsettable="true" dataType="org.eclipse.epf.uma.String" ordered="false"
	 * @generated
	 */
	String getPurpose();

	/**
	 * Sets the value of the '{@link org.eclipse.epf.uma.WorkProductDescription#getPurpose <em>Purpose</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Purpose</em>' attribute.
	 * @see #isSetPurpose()
	 * @see #unsetPurpose()
	 * @see #getPurpose()
	 * @generated
	 */
	void setPurpose(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.epf.uma.WorkProductDescription#getPurpose <em>Purpose</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPurpose()
	 * @see #getPurpose()
	 * @see #setPurpose(String)
	 * @generated
	 */
	void unsetPurpose();

	/**
	 * Returns whether the value of the '{@link org.eclipse.epf.uma.WorkProductDescription#getPurpose <em>Purpose</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Purpose</em>' attribute is set.
	 * @see #unsetPurpose()
	 * @see #getPurpose()
	 * @see #setPurpose(String)
	 * @generated
	 */
	boolean isSetPurpose();

	/**
	 * Returns the value of the '<em><b>Impact Of Not Having</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Describes the consequences of not producing the work product.  This is intended to aid in the tailoring the method/process to the needs of a specific project.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Impact Of Not Having</em>' attribute.
	 * @see #isSetImpactOfNotHaving()
	 * @see #unsetImpactOfNotHaving()
	 * @see #setImpactOfNotHaving(String)
	 * @see org.eclipse.epf.uma.UmaPackage#getWorkProductDescription_ImpactOfNotHaving()
	 * @model default="" unsettable="true" dataType="org.eclipse.epf.uma.String" ordered="false"
	 * @generated
	 */
	String getImpactOfNotHaving();

	/**
	 * Sets the value of the '{@link org.eclipse.epf.uma.WorkProductDescription#getImpactOfNotHaving <em>Impact Of Not Having</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Impact Of Not Having</em>' attribute.
	 * @see #isSetImpactOfNotHaving()
	 * @see #unsetImpactOfNotHaving()
	 * @see #getImpactOfNotHaving()
	 * @generated
	 */
	void setImpactOfNotHaving(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.epf.uma.WorkProductDescription#getImpactOfNotHaving <em>Impact Of Not Having</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetImpactOfNotHaving()
	 * @see #getImpactOfNotHaving()
	 * @see #setImpactOfNotHaving(String)
	 * @generated
	 */
	void unsetImpactOfNotHaving();

	/**
	 * Returns whether the value of the '{@link org.eclipse.epf.uma.WorkProductDescription#getImpactOfNotHaving <em>Impact Of Not Having</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Impact Of Not Having</em>' attribute is set.
	 * @see #unsetImpactOfNotHaving()
	 * @see #getImpactOfNotHaving()
	 * @see #setImpactOfNotHaving(String)
	 * @generated
	 */
	boolean isSetImpactOfNotHaving();

	/**
	 * Returns the value of the '<em><b>Reasons For Not Needing</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Describes the circumstances in which it is reasonable not to produce the work product.  This is intended to aid in the tailoring of the method/process to the needs of a specific project.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Reasons For Not Needing</em>' attribute.
	 * @see #isSetReasonsForNotNeeding()
	 * @see #unsetReasonsForNotNeeding()
	 * @see #setReasonsForNotNeeding(String)
	 * @see org.eclipse.epf.uma.UmaPackage#getWorkProductDescription_ReasonsForNotNeeding()
	 * @model default="" unsettable="true" dataType="org.eclipse.epf.uma.String" ordered="false"
	 * @generated
	 */
	String getReasonsForNotNeeding();

	/**
	 * Sets the value of the '{@link org.eclipse.epf.uma.WorkProductDescription#getReasonsForNotNeeding <em>Reasons For Not Needing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reasons For Not Needing</em>' attribute.
	 * @see #isSetReasonsForNotNeeding()
	 * @see #unsetReasonsForNotNeeding()
	 * @see #getReasonsForNotNeeding()
	 * @generated
	 */
	void setReasonsForNotNeeding(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.epf.uma.WorkProductDescription#getReasonsForNotNeeding <em>Reasons For Not Needing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetReasonsForNotNeeding()
	 * @see #getReasonsForNotNeeding()
	 * @see #setReasonsForNotNeeding(String)
	 * @generated
	 */
	void unsetReasonsForNotNeeding();

	/**
	 * Returns whether the value of the '{@link org.eclipse.epf.uma.WorkProductDescription#getReasonsForNotNeeding <em>Reasons For Not Needing</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Reasons For Not Needing</em>' attribute is set.
	 * @see #unsetReasonsForNotNeeding()
	 * @see #getReasonsForNotNeeding()
	 * @see #setReasonsForNotNeeding(String)
	 * @generated
	 */
	boolean isSetReasonsForNotNeeding();

} // WorkProductDescription
