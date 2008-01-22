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
package org.eclipse.epf.uma.impl;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.epf.uma.Diagram;
import org.eclipse.epf.uma.DiagramLink;
import org.eclipse.epf.uma.Point;
import org.eclipse.epf.uma.SemanticModelBridge;
import org.eclipse.epf.uma.UmaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diagram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.epf.uma.impl.DiagramImpl#getZoom <em>Zoom</em>}</li>
 *   <li>{@link org.eclipse.epf.uma.impl.DiagramImpl#getViewpoint <em>Viewpoint</em>}</li>
 *   <li>{@link org.eclipse.epf.uma.impl.DiagramImpl#getDiagramLink <em>Diagram Link</em>}</li>
 *   <li>{@link org.eclipse.epf.uma.impl.DiagramImpl#getNamespace <em>Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DiagramImpl extends GraphNodeImpl implements Diagram {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The default value of the '{@link #getZoom() <em>Zoom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZoom()
	 * @generated
	 * @ordered
	 */
	protected static final Double ZOOM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getZoom() <em>Zoom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZoom()
	 * @generated
	 * @ordered
	 */
	protected Double zoom = ZOOM_EDEFAULT;

	/**
	 * The cached value of the '{@link #getViewpoint() <em>Viewpoint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewpoint()
	 * @generated
	 * @ordered
	 */
	protected Point viewpoint;

	/**
	 * The cached value of the '{@link #getDiagramLink() <em>Diagram Link</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiagramLink()
	 * @generated
	 * @ordered
	 */
	protected EList<DiagramLink> diagramLink;

	/**
	 * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected SemanticModelBridge namespace;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiagramImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UmaPackage.Literals.DIAGRAM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Double getZoom() {
		return zoom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setZoom(Double newZoom) {
		Double oldZoom = zoom;
		zoom = newZoom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UmaPackage.DIAGRAM__ZOOM, oldZoom, zoom));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Point getViewpoint() {
		if (viewpoint != null && ((EObject) viewpoint).eIsProxy()) {
			InternalEObject oldViewpoint = (InternalEObject) viewpoint;
			viewpoint = (Point) eResolveProxy(oldViewpoint);
			if (viewpoint != oldViewpoint) {
				InternalEObject newViewpoint = (InternalEObject) viewpoint;
				NotificationChain msgs = oldViewpoint.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - UmaPackage.DIAGRAM__VIEWPOINT,
						null, null);
				if (newViewpoint.eInternalContainer() == null) {
					msgs = newViewpoint
							.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
									- UmaPackage.DIAGRAM__VIEWPOINT, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							UmaPackage.DIAGRAM__VIEWPOINT, oldViewpoint,
							viewpoint));
			}
		}
		return viewpoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Point basicGetViewpoint() {
		return viewpoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetViewpoint(Point newViewpoint,
			NotificationChain msgs) {
		Point oldViewpoint = viewpoint;
		viewpoint = newViewpoint;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, UmaPackage.DIAGRAM__VIEWPOINT,
					oldViewpoint, newViewpoint);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setViewpoint(Point newViewpoint) {
		if (newViewpoint != viewpoint) {
			NotificationChain msgs = null;
			if (viewpoint != null)
				msgs = ((InternalEObject) viewpoint).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - UmaPackage.DIAGRAM__VIEWPOINT,
						null, msgs);
			if (newViewpoint != null)
				msgs = ((InternalEObject) newViewpoint).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - UmaPackage.DIAGRAM__VIEWPOINT,
						null, msgs);
			msgs = basicSetViewpoint(newViewpoint, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UmaPackage.DIAGRAM__VIEWPOINT, newViewpoint, newViewpoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<DiagramLink> getDiagramLink() {
		if (diagramLink == null) {
			diagramLink = new EObjectWithInverseResolvingEList<DiagramLink>(
					DiagramLink.class, this, UmaPackage.DIAGRAM__DIAGRAM_LINK,
					UmaPackage.DIAGRAM_LINK__DIAGRAM);
		}
		return diagramLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SemanticModelBridge getNamespace() {
		if (namespace != null && ((EObject) namespace).eIsProxy()) {
			InternalEObject oldNamespace = (InternalEObject) namespace;
			namespace = (SemanticModelBridge) eResolveProxy(oldNamespace);
			if (namespace != oldNamespace) {
				InternalEObject newNamespace = (InternalEObject) namespace;
				NotificationChain msgs = oldNamespace.eInverseRemove(this,
						UmaPackage.SEMANTIC_MODEL_BRIDGE__DIAGRAM,
						SemanticModelBridge.class, null);
				if (newNamespace.eInternalContainer() == null) {
					msgs = newNamespace.eInverseAdd(this,
							UmaPackage.SEMANTIC_MODEL_BRIDGE__DIAGRAM,
							SemanticModelBridge.class, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							UmaPackage.DIAGRAM__NAMESPACE, oldNamespace,
							namespace));
			}
		}
		return namespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SemanticModelBridge basicGetNamespace() {
		return namespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNamespace(
			SemanticModelBridge newNamespace, NotificationChain msgs) {
		SemanticModelBridge oldNamespace = namespace;
		namespace = newNamespace;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, UmaPackage.DIAGRAM__NAMESPACE,
					oldNamespace, newNamespace);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamespace(SemanticModelBridge newNamespace) {
		if (newNamespace != namespace) {
			NotificationChain msgs = null;
			if (namespace != null)
				msgs = ((InternalEObject) namespace).eInverseRemove(this,
						UmaPackage.SEMANTIC_MODEL_BRIDGE__DIAGRAM,
						SemanticModelBridge.class, msgs);
			if (newNamespace != null)
				msgs = ((InternalEObject) newNamespace).eInverseAdd(this,
						UmaPackage.SEMANTIC_MODEL_BRIDGE__DIAGRAM,
						SemanticModelBridge.class, msgs);
			msgs = basicSetNamespace(newNamespace, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					UmaPackage.DIAGRAM__NAMESPACE, newNamespace, newNamespace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case UmaPackage.DIAGRAM__DIAGRAM_LINK:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getDiagramLink())
					.basicAdd(otherEnd, msgs);
		case UmaPackage.DIAGRAM__NAMESPACE:
			if (namespace != null)
				msgs = ((InternalEObject) namespace).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - UmaPackage.DIAGRAM__NAMESPACE,
						null, msgs);
			return basicSetNamespace((SemanticModelBridge) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case UmaPackage.DIAGRAM__VIEWPOINT:
			return basicSetViewpoint(null, msgs);
		case UmaPackage.DIAGRAM__DIAGRAM_LINK:
			return ((InternalEList<?>) getDiagramLink()).basicRemove(otherEnd,
					msgs);
		case UmaPackage.DIAGRAM__NAMESPACE:
			return basicSetNamespace(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UmaPackage.DIAGRAM__ZOOM:
			return getZoom();
		case UmaPackage.DIAGRAM__VIEWPOINT:
			if (resolve)
				return getViewpoint();
			return basicGetViewpoint();
		case UmaPackage.DIAGRAM__DIAGRAM_LINK:
			return getDiagramLink();
		case UmaPackage.DIAGRAM__NAMESPACE:
			if (resolve)
				return getNamespace();
			return basicGetNamespace();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UmaPackage.DIAGRAM__ZOOM:
			setZoom((Double) newValue);
			return;
		case UmaPackage.DIAGRAM__VIEWPOINT:
			setViewpoint((Point) newValue);
			return;
		case UmaPackage.DIAGRAM__DIAGRAM_LINK:
			getDiagramLink().clear();
			getDiagramLink().addAll(
					(Collection<? extends DiagramLink>) newValue);
			return;
		case UmaPackage.DIAGRAM__NAMESPACE:
			setNamespace((SemanticModelBridge) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UmaPackage.DIAGRAM__ZOOM:
			setZoom(ZOOM_EDEFAULT);
			return;
		case UmaPackage.DIAGRAM__VIEWPOINT:
			setViewpoint((Point) null);
			return;
		case UmaPackage.DIAGRAM__DIAGRAM_LINK:
			getDiagramLink().clear();
			return;
		case UmaPackage.DIAGRAM__NAMESPACE:
			setNamespace((SemanticModelBridge) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UmaPackage.DIAGRAM__ZOOM:
			return ZOOM_EDEFAULT == null ? zoom != null : !ZOOM_EDEFAULT
					.equals(zoom);
		case UmaPackage.DIAGRAM__VIEWPOINT:
			return viewpoint != null;
		case UmaPackage.DIAGRAM__DIAGRAM_LINK:
			return diagramLink != null && !diagramLink.isEmpty();
		case UmaPackage.DIAGRAM__NAMESPACE:
			return namespace != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (zoom: "); //$NON-NLS-1$
		result.append(zoom);
		result.append(')');
		return result.toString();
	}

} //DiagramImpl
