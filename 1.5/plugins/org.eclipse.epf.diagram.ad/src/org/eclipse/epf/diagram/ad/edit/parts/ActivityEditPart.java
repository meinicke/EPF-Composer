/*
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial implementation
 *
 */
package org.eclipse.epf.diagram.ad.edit.parts;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.epf.diagram.ad.custom.policies.ActivityCreationEditPolicy;
import org.eclipse.epf.diagram.ad.custom.policies.ActivityXYLayoutEditPolicy;
import org.eclipse.epf.diagram.ad.edit.policies.ActivityCanonicalEditPolicy;
import org.eclipse.epf.diagram.ad.edit.policies.ActivityItemSemanticEditPolicy;
import org.eclipse.epf.diagram.ad.part.ActivityDiagramEditorPlugin;
import org.eclipse.epf.diagram.core.part.util.DiagramEditorUtil;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class ActivityEditPart extends DiagramEditPart {

	/**
	 * @generated
	 */
	public static final String MODEL_ID = "AD"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 79;

	/**
	 * @generated
	 */
	public ActivityEditPart(View view) {
		super(view);
	}

	/**
	 * @modified
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new ActivityItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new ActivityCanonicalEditPolicy());

		//		 modified START
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new ActivityXYLayoutEditPolicy());
		installEditPolicy(
				EditPolicyRoles.CREATION_ROLE,
				new ActivityCreationEditPolicy());
		// modified END
	}

	@Override
	protected void handleNotificationEvent(Notification event) {
		if (NotationPackage.Literals.VIEW__VISIBLE.equals(event.getFeature())) {
			Object notifier = event.getNotifier();
			if (notifier == getModel())
				refreshVisibility();
			else if (event.getNotifier() instanceof Node) {
				refreshChildren();
				DiagramEditorUtil.refreshConnectionEditParts(this);
			}
		} else
			super.handleNotificationEvent(event);
	}

	/**
	 * GMF and GEF is not handling connections visibility properly. 
	 * This is work-around solution to handle connections visibility.  
	 * Visibility of edges need to set first and then nodes. 
	 */
	private void refreshConnections() {
		// TODO Auto-generated method stub
		Diagram act = (Diagram) getModel();
		List edges = act.getEdges();
		for (Iterator iter = edges.iterator(); iter.hasNext();) {
			Edge edge = (Edge) iter.next();
			if (edge.isVisible()) {
				ConnectionEditPart part = createOrFindConnection(edge);
				if (part != null) {
					if (part.getSource() == null) {
						for (Iterator iter1 = getChildren().iterator(); iter1
								.hasNext();) {
							EditPart element = (EditPart) iter1.next();
							Object model = element.getModel();
							if (edge.getSource() == model) {
								if (element instanceof GraphicalEditPart) {
									if (((GraphicalEditPart) element)
											.getFigure().isVisible()) {
										part.setSource(element);
									} else {
										part.setSource(null);
									}
								}
							}
						}
					}
					if (part.getTarget() == null) {
						for (Iterator iter1 = getChildren().iterator(); iter1
								.hasNext();) {
							EditPart element = (EditPart) iter1.next();
							Object model = element.getModel();
							if (edge.getTarget() == model) {
								if (element instanceof GraphicalEditPart) {
									if (((GraphicalEditPart) element)
											.getFigure().isVisible()) {
										part.setTarget(element);
									} else {
										part.setTarget(null);
									}
								}
							}
						}
					}
				}
			}
		}

		try {
			for (Iterator iter = edges.iterator(); iter.hasNext();) {
				Edge edge = (Edge) iter.next();
				org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart part = (org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart) createOrFindConnection(edge);
				if ((part.getSource() == null || part.getTarget() == null)
						&& part.getFigure() != null) {
					part.getFigure().setVisible(false);
					part.getFigure().revalidate();
					part.deactivate();
				}
			}
		} catch (Exception e) {
			if (ActivityDiagramEditorPlugin.getInstance().isDebugging()) {
				ActivityDiagramEditorPlugin.getInstance().getLogger().logError(
						"Error occured while refresh the connection: " + e); //$NON-NLS-1$
			}
		}
	}
	
	/**
	 * Moves complete diagram children to given delta. 
	 * @param int  (X-axis delta)
	 * @param int  (Y-axis delta)
	 * 
	 */
	public void moveFigure(int moveX, int moveY) {
		for (Iterator iter = this.getChildren().iterator(); iter.hasNext();) {
			Object child = iter.next();
			if (child instanceof GraphicalEditPart) {
				GraphicalEditPart part = (GraphicalEditPart) child;
				Rectangle bounds = part.getFigure().getBounds();
				Rectangle r = new Rectangle(new Point(bounds.x + moveX,
						bounds.y + moveY), bounds.getSize());

				// also need to move the bend points
//				List list = part.getSourceConnections();
//				for (Iterator itor = list.iterator(); itor.hasNext();) {
//					ControlFlowEditPart linkpart = (ControlFlowEditPart) itor.next();
//					Link link = (Link) linkpart.getModel();
//					if (link.getBendpoints().size() > 0) {
//						PointList plist = ((PolylineConnection) linkpart
//								.getFigure()).getPoints();
//						List points = new ArrayList();
//						for (int i = 1; i < plist.size() - 1; i++) {
//							Point p = plist.getPoint(i);
//							p = p.translate(new Point(moveX, moveY));
//
//							org.eclipse.draw2d.AbsoluteBendpoint bp = new org.eclipse.draw2d.AbsoluteBendpoint(
//									p);
//							points.add(bp);
//						}
//
//						linkpart.getConnectionFigure().setRoutingConstraint(
//								points);
//					}
//				}

				this.setLayoutConstraint(part, part.getFigure(), r);

			} else {
				// System.out.println("Part not moved: " + child); //$NON-NLS-1$
			}
		}

	}
}
