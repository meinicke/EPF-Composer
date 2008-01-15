/**
 * <copyright>
 * </copyright>
 *
 * $Id: BreakdownElementDescription.java,v 1.1 2008/01/15 08:52:07 jtham Exp $
 */
package org.eclipse.epf.xml.uma;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Breakdown Element Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A generalized Content Description that is used to store the textual description for a Breakdown Element.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epf.xml.uma.BreakdownElementDescription#getUsageGuidance <em>Usage Guidance</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epf.xml.uma.UmaPackage#getBreakdownElementDescription()
 * @model extendedMetaData="name='BreakdownElementDescription' kind='elementOnly'"
 * @generated
 */
public interface BreakdownElementDescription extends ContentDescription {
	/**
	 * Returns the value of the '<em><b>Usage Guidance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Provides information and guidance on the meaning of the Boolean flag values and under what circumstances they should be overridden. For example, it describes why the breakdown element is optional or considerations for repeating it and differences in the individual occurrences of this Breakdown Element across the lifecycle.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Usage Guidance</em>' attribute.
	 * @see #setUsageGuidance(String)
	 * @see org.eclipse.epf.xml.uma.UmaPackage#getBreakdownElementDescription_UsageGuidance()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='usageGuidance'"
	 * @generated
	 */
	String getUsageGuidance();

	/**
	 * Sets the value of the '{@link org.eclipse.epf.xml.uma.BreakdownElementDescription#getUsageGuidance <em>Usage Guidance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Usage Guidance</em>' attribute.
	 * @see #getUsageGuidance()
	 * @generated
	 */
	void setUsageGuidance(String value);

} // BreakdownElementDescription