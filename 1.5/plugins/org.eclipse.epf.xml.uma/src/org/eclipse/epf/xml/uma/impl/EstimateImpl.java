/**
 * <copyright>
 * </copyright>
 *
 * $Id: EstimateImpl.java,v 1.1 2008/01/15 08:51:36 jtham Exp $
 */
package org.eclipse.epf.xml.uma.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.epf.xml.uma.Estimate;
import org.eclipse.epf.xml.uma.UmaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Estimate</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.epf.xml.uma.impl.EstimateImpl#getGroup2 <em>Group2</em>}</li>
 *   <li>{@link org.eclipse.epf.xml.uma.impl.EstimateImpl#getEstimationMetric <em>Estimation Metric</em>}</li>
 *   <li>{@link org.eclipse.epf.xml.uma.impl.EstimateImpl#getEstimationConsiderations <em>Estimation Considerations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EstimateImpl extends GuidanceImpl implements Estimate {
	/**
	 * The cached value of the '{@link #getGroup2() <em>Group2</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup2()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap group2;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EstimateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UmaPackage.Literals.ESTIMATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup2() {
		if (group2 == null) {
			group2 = new BasicFeatureMap(this, UmaPackage.ESTIMATE__GROUP2);
		}
		return group2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getEstimationMetric() {
		return getGroup2().list(UmaPackage.Literals.ESTIMATE__ESTIMATION_METRIC);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getEstimationConsiderations() {
		return getGroup2().list(UmaPackage.Literals.ESTIMATE__ESTIMATION_CONSIDERATIONS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case UmaPackage.ESTIMATE__GROUP2:
				return ((InternalEList)getGroup2()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UmaPackage.ESTIMATE__GROUP2:
				if (coreType) return getGroup2();
				return ((FeatureMap.Internal)getGroup2()).getWrapper();
			case UmaPackage.ESTIMATE__ESTIMATION_METRIC:
				return getEstimationMetric();
			case UmaPackage.ESTIMATE__ESTIMATION_CONSIDERATIONS:
				return getEstimationConsiderations();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case UmaPackage.ESTIMATE__GROUP2:
				((FeatureMap.Internal)getGroup2()).set(newValue);
				return;
			case UmaPackage.ESTIMATE__ESTIMATION_METRIC:
				getEstimationMetric().clear();
				getEstimationMetric().addAll((Collection)newValue);
				return;
			case UmaPackage.ESTIMATE__ESTIMATION_CONSIDERATIONS:
				getEstimationConsiderations().clear();
				getEstimationConsiderations().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case UmaPackage.ESTIMATE__GROUP2:
				getGroup2().clear();
				return;
			case UmaPackage.ESTIMATE__ESTIMATION_METRIC:
				getEstimationMetric().clear();
				return;
			case UmaPackage.ESTIMATE__ESTIMATION_CONSIDERATIONS:
				getEstimationConsiderations().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case UmaPackage.ESTIMATE__GROUP2:
				return group2 != null && !group2.isEmpty();
			case UmaPackage.ESTIMATE__ESTIMATION_METRIC:
				return !getEstimationMetric().isEmpty();
			case UmaPackage.ESTIMATE__ESTIMATION_CONSIDERATIONS:
				return !getEstimationConsiderations().isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (group2: ");
		result.append(group2);
		result.append(')');
		return result.toString();
	}

} //EstimateImpl