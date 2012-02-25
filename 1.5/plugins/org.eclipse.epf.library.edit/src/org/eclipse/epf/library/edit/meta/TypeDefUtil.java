package org.eclipse.epf.library.edit.meta;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epf.common.utils.ExtensionHelper;
import org.eclipse.epf.library.edit.meta.internal.ModifiedTypeMetaImpl;
import org.eclipse.epf.library.edit.meta.internal.TypeDefParserImpl;
import org.eclipse.epf.library.edit.util.PracticePropUtil;
import org.eclipse.epf.library.edit.util.PropUtil;
import org.eclipse.epf.uma.BreakdownElement;
import org.eclipse.epf.uma.ContentDescription;
import org.eclipse.epf.uma.ContentElement;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.Practice;
import org.eclipse.epf.uma.util.ExtendedAttribute;
import org.eclipse.epf.uma.util.ExtendedReference;
import org.eclipse.epf.uma.util.MetaElement;
import org.eclipse.epf.uma.util.ModifiedTypeMeta;
import org.eclipse.epf.uma.util.QualifiedReference;
import org.eclipse.epf.uma.util.UmaUtil;
import org.eclipse.epf.uma.util.UserDefinedTypeMeta;

public class TypeDefUtil {

	private static TypeDefUtil instance;
	
	static {
		Object obj = ExtensionHelper.create(TypeDefUtil.class, null);
		if (obj instanceof TypeDefUtil) {
			instance = (TypeDefUtil) obj;
		} else {
			instance = new TypeDefUtil();
		}
	}
	
	public static TypeDefUtil getInstance() {
		return instance;
	}

	protected TypeDefUtil() {		
	}
	
	public TypeDefParser getTypeDefParser() {
		return new TypeDefParserImpl();
	}
	
	public IMetaDef createMetaDef(Class cls) {
		if (cls == ModifiedTypeMeta.class) {
			return new ModifiedTypeMetaImpl();
		}
		return null;
	}
	
	public void associate(ExtendedReference eRef, EReference ref) {
		associate_(eRef, ref);
	}
	
	public void associate(ExtendedAttribute eAtt, EAttribute att) {
		associate_(eAtt, att);
	}
	
	private void associate_(MetaElement metaElement, EStructuralFeature feature) {
		if (! (metaElement instanceof Adapter)) {
			return;
		}
		int sz = feature.eAdapters().size();
		for (int i = sz - 1; i >=0; i--) {
			Object adapter = feature.eAdapters().get(i);
			if (adapter instanceof MetaElement) {
				feature.eAdapters().remove(i);
			}
		}
		feature.eAdapters().add((Adapter) metaElement);
	}
	
	public ExtendedReference getAssociatedExtendedReference(EStructuralFeature feature) {
		MetaElement element = getAssociatedMetaElement(feature);
		if (element instanceof ExtendedReference) {
			return (ExtendedReference) element;
		}
		return null;
	}

	public ExtendedAttribute getAssociatedExtendedAttribute(EStructuralFeature feature) {
		MetaElement element = getAssociatedMetaElement(feature);
		if (element instanceof ExtendedAttribute) {
			return (ExtendedAttribute) element;
		}
		return null;
	}
	
	private MetaElement getAssociatedMetaElement(EStructuralFeature feature) {
		if (feature == null) {
			return null;
		}
		for (Object adapter : feature.eAdapters()) {
			if (adapter instanceof MetaElement) {
				return (MetaElement) adapter;
			}
		}
		return null;
	}
		
	public Object eGet(EObject obj, EStructuralFeature feature) {
		return eGet(obj, feature, false);
	}
	
	public Object eGet(EObject obj, EStructuralFeature feature, boolean toModify) {
		if (obj == null) {
			return null;
		}		
		if (obj instanceof MethodElement) {
			PropUtil propUtil = PropUtil.getPropUtil();
			MethodElement element = (MethodElement) obj;
			if (feature instanceof EReference) {
				EReference ref = (EReference) feature;
				if (element instanceof Practice) {
					PracticePropUtil practicePropUtil = PracticePropUtil.getPracticePropUtil();
					Practice practice = (Practice) element;
					UserDefinedTypeMeta meta = practicePropUtil.getUdtMeta(practice);
					if (meta != null && meta.isQualifiedRefernce((EReference) feature)) {
						List list = practicePropUtil.getQReferenceListById(element,
								feature.getName(), false);
						return list;
					}
				}								
				if (ref == UmaUtil.MethodElement_UdtList) {
					return propUtil.getUdtList(element, toModify);
				}
				ExtendedReference eRef = getAssociatedExtendedReference(ref);
				if (eRef != null) {
					return propUtil.getExtendedReferenceList(element, eRef, toModify);
				}
			} else if (feature instanceof EAttribute && element instanceof ContentDescription) {
				EAttribute att = (EAttribute) feature;
				ExtendedAttribute eAtt = getAssociatedExtendedAttribute(att);
				if (eAtt != null) {
					return propUtil.getExtendedAttribute((ContentDescription) element, eAtt);
				}
			}
		}
		return obj.eGet(feature);
	}
	
	public void eSet(EObject obj, EStructuralFeature feature, Object newValue) {
		if (obj instanceof ContentDescription) {
			ExtendedAttribute eAtt = getAssociatedExtendedAttribute(feature);
			if (eAtt != null && (newValue == null || newValue instanceof String) ) {
				PropUtil.getPropUtil().setExtendedAttribute((ContentDescription) obj, eAtt, (String) newValue);
				return;
			}
		}
		obj.eSet(feature, newValue);
	}
		
	public List<EReference> getEAllReferences(MethodElement element) {
		List<EReference> list = new ArrayList<EReference>(element.eClass().getEAllReferences());
		
		if (element instanceof Practice) {
			PracticePropUtil practicePropUtil = PracticePropUtil.getPracticePropUtil();
			Practice practice = (Practice) element;
			UserDefinedTypeMeta meta = practicePropUtil.getUdtMeta(practice);
			if (meta != null && !meta.getQualifiedReferences().isEmpty()) {
				list.addAll(meta.getQualifiedReferences());
			}
		}
		
		PropUtil propUtil = PropUtil.getPropUtil();	
		
		if (propUtil.hasUdtList(element)) {
			list.add(UmaUtil.MethodElement_UdtList);
		}
					
		ModifiedTypeMeta meta = propUtil.getGlobalMdtMeta(element);
		if (meta != null) {
			list = new ArrayList<EReference>(list);
			for (ExtendedReference eRef : meta.getReferences()) {
				list.add(eRef.getReference());
				for (QualifiedReference qRef : eRef.getQualifiedReferences()) {
					list.add(qRef.getReference());
				}
			}
		}
		return list;
	}
	
	public List<EAttribute> getEAllAttributes(MethodElement element) {
		List<EAttribute> list =new ArrayList<EAttribute>(element.eClass().getEAllAttributes());
		
		if (! (element instanceof ContentDescription)) {
			return list;
		}
					
		EObject owner = element.eContainer();
		if (! (owner instanceof MethodElement)) {
			return list;
		}
		
		PropUtil propUtil = PropUtil.getPropUtil();	
		ModifiedTypeMeta meta = propUtil.getGlobalMdtMeta((MethodElement) owner);
		if (meta == null) {
			return list;
		}
		for (ExtendedAttribute eAtt : meta.getRtes()) {
			list.add(eAtt.getAttribute());
		}
		
		return list;
	}
	
	public static Class getSuperClass(Class cls) {
		Class cls1 = ContentElement.class;
		Class cls2 = BreakdownElement.class;
		Class[] ins = cls.getInterfaces();
		if (ins == null || ins.length == 0) {
			return null;
		}
		for (Class in : ins) {
			if (cls1.isAssignableFrom(in) || cls2.isAssignableFrom(in)) {
				return in;
			}
		}
		return null;
	}
	
}
