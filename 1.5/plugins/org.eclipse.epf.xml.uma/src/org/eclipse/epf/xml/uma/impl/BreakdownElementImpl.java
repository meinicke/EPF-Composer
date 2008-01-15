/**
 * <copyright>
 * </copyright>
 *
 * $Id: BreakdownElementImpl.java,v 1.1 2008/01/15 08:51:36 jtham Exp $
 */
package org.eclipse.epf.xml.uma.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.epf.xml.uma.BreakdownElement;
import org.eclipse.epf.xml.uma.UmaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Breakdown Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.epf.xml.uma.impl.BreakdownElementImpl#getPresentedAfter <em>Presented After</em>}</li>
 *   <li>{@link org.eclipse.epf.xml.uma.impl.BreakdownElementImpl#getPresentedBefore <em>Presented Before</em>}</li>
 *   <li>{@link org.eclipse.epf.xml.uma.impl.BreakdownElementImpl#getPlanningData <em>Planning Data</em>}</li>
 *   <li>{@link org.eclipse.epf.xml.uma.impl.BreakdownElementImpl#getSuperActivity <em>Super Activity</em>}</li>
 *   <li>{@link org.eclipse.epf.xml.uma.impl.BreakdownElementImpl#isHasMultipleOccurrences <em>Has Multiple Occurrences</em>}</li>
 *   <li>{@link org.eclipse.epf.xml.uma.impl.BreakdownElementImpl#isIsOptional <em>Is Optional</em>}</li>
 *   <li>{@link org.eclipse.epf.xml.uma.impl.BreakdownElementImpl#isIsPlanned <em>Is Planned</em>}</li>
 *   <li>{@link org.eclipse.epf.xml.uma.impl.BreakdownElementImpl#getPrefix <em>Prefix</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BreakdownElementImpl extends ProcessElementImpl implements BreakdownElement {
	/**
	 * The default value of the '{@link #getPresentedAfter() <em>Presented After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPresentedAfter()
	 * @generated
	 * @ordered
	 */
	protected static final String PRESENTED_AFTER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPresentedAfter() <em>Presented After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPresentedAfter()
	 * @generated
	 * @ordered
	 */
	protected String presentedAfter = PRESENTED_AFTER_EDEFAULT;

	/**
	 * The default value of the '{@link #getPresentedBefore() <em>Presented Before</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPresentedBefore()
	 * @generated
	 * @ordered
	 */
	protected static final String PRESENTED_BEFORE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPresentedBefore() <em>Presented Before</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPresentedBefore()
	 * @generated
	 * @ordered
	 */
	protected String presentedBefore = PRESENTED_BEFORE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPlanningData() <em>Planning Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlanningData()
	 * @generated
	 * @ordered
	 */
	protected static final String PLANNING_DATA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPlanningData() <em>Planning Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlanningData()
	 * @generated
	 * @ordered
	 */
	protected String planningData = PLANNING_DATA_EDEFAULT;

	/**
	 * The default value of the '{@link #getSuperActivity() <em>Super Activity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperActivity()
	 * @generated
	 * @ordered
	 */
	protected static final String SUPER_ACTIVITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSuperActivity() <em>Super Activity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperActivity()
	 * @generated
	 * @ordered
	 */
	protected String superActivity = SUPER_ACTIVITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isHasMultipleOccurrences() <em>Has Multiple Occurrences</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasMultipleOccurrences()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_MULTIPLE_OCCURRENCES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasMultipleOccurrences() <em>Has Multiple Occurrences</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasMultipleOccurrences()
	 * @generated
	 * @ordered
	 */
	protected boolean hasMultipleOccurrences = HAS_MULTIPLE_OCCURRENCES_EDEFAULT;

	/**
	 * This is true if the Has Multiple Occurrences attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean hasMultipleOccurrencesESet;

	/**
	 * The default value of the '{@link #isIsOptional() <em>Is Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsOptional()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_OPTIONAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsOptional() <em>Is Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsOptional()
	 * @generated
	 * @ordered
	 */
	protected boolean isOptional = IS_OPTIONAL_EDEFAULT;

	/**
	 * This is true if the Is Optional attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean isOptionalESet;

	/**
	 * The default value of the '{@link #isIsPlanned() <em>Is Planned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsPlanned()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_PLANNED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsPlanned() <em>Is Planned</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsPlanned()
	 * @generated
	 * @ordered
	 */
	protected boolean isPlanned = IS_PLANNED_EDEFAULT;

	/**
	 * This is true if the Is Planned attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean isPlannedESet;

	/**
	 * The default value of the '{@link #getPrefix() <em>Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String PREFIX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPrefix() <em>Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrefix()
	 * @generated
	 * @ordered
	 */
	protected String prefix = PREFIX_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BreakdownElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UmaPackage.Literals.BREAKDOWN_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPresentedAfter() {
		return presentedAfter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPresentedAfter(String newPresentedAfter) {
		String oldPresentedAfter = presentedAfter;
		presentedAfter = newPresentedAfter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UmaPackage.BREAKDOWN_ELEMENT__PRESENTED_AFTER, oldPresentedAfter, presentedAfter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPresentedBefore() {
		return presentedBefore;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPresentedBefore(String newPresentedBefore) {
		String oldPresentedBefore = presentedBefore;
		presentedBefore = newPresentedBefore;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UmaPackage.BREAKDOWN_ELEMENT__PRESENTED_BEFORE, oldPresentedBefore, presentedBefore));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPlanningData() {
		return planningData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlanningData(String newPlanningData) {
		String oldPlanningData = planningData;
		planningData = newPlanningData;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UmaPackage.BREAKDOWN_ELEMENT__PLANNING_DATA, oldPlanningData, planningData));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSuperActivity() {
		return superActivity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperActivity(String newSuperActivity) {
		String oldSuperActivity = superActivity;
		superActivity = newSuperActivity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UmaPackage.BREAKDOWN_ELEMENT__SUPER_ACTIVITY, oldSuperActivity, superActivity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasMultipleOccurrences() {
		return hasMultipleOccurrences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasMultipleOccurrences(boolean newHasMultipleOccurrences) {
		boolean oldHasMultipleOccurrences = hasMultipleOccurrences;
		hasMultipleOccurrences = newHasMultipleOccurrences;
		boolean oldHasMultipleOccurrencesESet = hasMultipleOccurrencesESet;
		hasMultipleOccurrencesESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UmaPackage.BREAKDOWN_ELEMENT__HAS_MULTIPLE_OCCURRENCES, oldHasMultipleOccurrences, hasMultipleOccurrences, !oldHasMultipleOccurrencesESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetHasMultipleOccurrences() {
		boolean oldHasMultipleOccurrences = hasMultipleOccurrences;
		boolean oldHasMultipleOccurrencesESet = hasMultipleOccurrencesESet;
		hasMultipleOccurrences = HAS_MULTIPLE_OCCURRENCES_EDEFAULT;
		hasMultipleOccurrencesESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, UmaPackage.BREAKDOWN_ELEMENT__HAS_MULTIPLE_OCCURRENCES, oldHasMultipleOccurrences, HAS_MULTIPLE_OCCURRENCES_EDEFAULT, oldHasMultipleOccurrencesESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetHasMultipleOccurrences() {
		return hasMultipleOccurrencesESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsOptional() {
		return isOptional;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsOptional(boolean newIsOptional) {
		boolean oldIsOptional = isOptional;
		isOptional = newIsOptional;
		boolean oldIsOptionalESet = isOptionalESet;
		isOptionalESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UmaPackage.BREAKDOWN_ELEMENT__IS_OPTIONAL, oldIsOptional, isOptional, !oldIsOptionalESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetIsOptional() {
		boolean oldIsOptional = isOptional;
		boolean oldIsOptionalESet = isOptionalESet;
		isOptional = IS_OPTIONAL_EDEFAULT;
		isOptionalESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, UmaPackage.BREAKDOWN_ELEMENT__IS_OPTIONAL, oldIsOptional, IS_OPTIONAL_EDEFAULT, oldIsOptionalESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetIsOptional() {
		return isOptionalESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsPlanned() {
		return isPlanned;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsPlanned(boolean newIsPlanned) {
		boolean oldIsPlanned = isPlanned;
		isPlanned = newIsPlanned;
		boolean oldIsPlannedESet = isPlannedESet;
		isPlannedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UmaPackage.BREAKDOWN_ELEMENT__IS_PLANNED, oldIsPlanned, isPlanned, !oldIsPlannedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetIsPlanned() {
		boolean oldIsPlanned = isPlanned;
		boolean oldIsPlannedESet = isPlannedESet;
		isPlanned = IS_PLANNED_EDEFAULT;
		isPlannedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, UmaPackage.BREAKDOWN_ELEMENT__IS_PLANNED, oldIsPlanned, IS_PLANNED_EDEFAULT, oldIsPlannedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetIsPlanned() {
		return isPlannedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrefix(String newPrefix) {
		String oldPrefix = prefix;
		prefix = newPrefix;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UmaPackage.BREAKDOWN_ELEMENT__PREFIX, oldPrefix, prefix));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UmaPackage.BREAKDOWN_ELEMENT__PRESENTED_AFTER:
				return getPresentedAfter();
			case UmaPackage.BREAKDOWN_ELEMENT__PRESENTED_BEFORE:
				return getPresentedBefore();
			case UmaPackage.BREAKDOWN_ELEMENT__PLANNING_DATA:
				return getPlanningData();
			case UmaPackage.BREAKDOWN_ELEMENT__SUPER_ACTIVITY:
				return getSuperActivity();
			case UmaPackage.BREAKDOWN_ELEMENT__HAS_MULTIPLE_OCCURRENCES:
				return isHasMultipleOccurrences() ? Boolean.TRUE : Boolean.FALSE;
			case UmaPackage.BREAKDOWN_ELEMENT__IS_OPTIONAL:
				return isIsOptional() ? Boolean.TRUE : Boolean.FALSE;
			case UmaPackage.BREAKDOWN_ELEMENT__IS_PLANNED:
				return isIsPlanned() ? Boolean.TRUE : Boolean.FALSE;
			case UmaPackage.BREAKDOWN_ELEMENT__PREFIX:
				return getPrefix();
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
			case UmaPackage.BREAKDOWN_ELEMENT__PRESENTED_AFTER:
				setPresentedAfter((String)newValue);
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__PRESENTED_BEFORE:
				setPresentedBefore((String)newValue);
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__PLANNING_DATA:
				setPlanningData((String)newValue);
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__SUPER_ACTIVITY:
				setSuperActivity((String)newValue);
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__HAS_MULTIPLE_OCCURRENCES:
				setHasMultipleOccurrences(((Boolean)newValue).booleanValue());
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__IS_OPTIONAL:
				setIsOptional(((Boolean)newValue).booleanValue());
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__IS_PLANNED:
				setIsPlanned(((Boolean)newValue).booleanValue());
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__PREFIX:
				setPrefix((String)newValue);
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
			case UmaPackage.BREAKDOWN_ELEMENT__PRESENTED_AFTER:
				setPresentedAfter(PRESENTED_AFTER_EDEFAULT);
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__PRESENTED_BEFORE:
				setPresentedBefore(PRESENTED_BEFORE_EDEFAULT);
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__PLANNING_DATA:
				setPlanningData(PLANNING_DATA_EDEFAULT);
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__SUPER_ACTIVITY:
				setSuperActivity(SUPER_ACTIVITY_EDEFAULT);
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__HAS_MULTIPLE_OCCURRENCES:
				unsetHasMultipleOccurrences();
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__IS_OPTIONAL:
				unsetIsOptional();
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__IS_PLANNED:
				unsetIsPlanned();
				return;
			case UmaPackage.BREAKDOWN_ELEMENT__PREFIX:
				setPrefix(PREFIX_EDEFAULT);
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
			case UmaPackage.BREAKDOWN_ELEMENT__PRESENTED_AFTER:
				return PRESENTED_AFTER_EDEFAULT == null ? presentedAfter != null : !PRESENTED_AFTER_EDEFAULT.equals(presentedAfter);
			case UmaPackage.BREAKDOWN_ELEMENT__PRESENTED_BEFORE:
				return PRESENTED_BEFORE_EDEFAULT == null ? presentedBefore != null : !PRESENTED_BEFORE_EDEFAULT.equals(presentedBefore);
			case UmaPackage.BREAKDOWN_ELEMENT__PLANNING_DATA:
				return PLANNING_DATA_EDEFAULT == null ? planningData != null : !PLANNING_DATA_EDEFAULT.equals(planningData);
			case UmaPackage.BREAKDOWN_ELEMENT__SUPER_ACTIVITY:
				return SUPER_ACTIVITY_EDEFAULT == null ? superActivity != null : !SUPER_ACTIVITY_EDEFAULT.equals(superActivity);
			case UmaPackage.BREAKDOWN_ELEMENT__HAS_MULTIPLE_OCCURRENCES:
				return isSetHasMultipleOccurrences();
			case UmaPackage.BREAKDOWN_ELEMENT__IS_OPTIONAL:
				return isSetIsOptional();
			case UmaPackage.BREAKDOWN_ELEMENT__IS_PLANNED:
				return isSetIsPlanned();
			case UmaPackage.BREAKDOWN_ELEMENT__PREFIX:
				return PREFIX_EDEFAULT == null ? prefix != null : !PREFIX_EDEFAULT.equals(prefix);
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
		result.append(" (presentedAfter: ");
		result.append(presentedAfter);
		result.append(", presentedBefore: ");
		result.append(presentedBefore);
		result.append(", planningData: ");
		result.append(planningData);
		result.append(", superActivity: ");
		result.append(superActivity);
		result.append(", hasMultipleOccurrences: ");
		if (hasMultipleOccurrencesESet) result.append(hasMultipleOccurrences); else result.append("<unset>");
		result.append(", isOptional: ");
		if (isOptionalESet) result.append(isOptional); else result.append("<unset>");
		result.append(", isPlanned: ");
		if (isPlannedESet) result.append(isPlanned); else result.append("<unset>");
		result.append(", prefix: ");
		result.append(prefix);
		result.append(')');
		return result.toString();
	}

} //BreakdownElementImpl