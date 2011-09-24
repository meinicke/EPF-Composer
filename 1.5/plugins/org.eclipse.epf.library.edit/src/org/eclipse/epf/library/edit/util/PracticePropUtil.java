package org.eclipse.epf.library.edit.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.epf.library.edit.LibraryEditPlugin;
import org.eclipse.epf.library.edit.command.IActionManager;
import org.eclipse.epf.library.edit.uma.ExtendReferenceMap;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.Practice;
import org.eclipse.epf.uma.util.UserDefinedTypeMeta;
import org.w3c.dom.Element;

public class PracticePropUtil extends MethodElementPropUtil {
	
	public static final String Practice_UtdData = "Practice_utdData";		//$NON-NLS-1$		
	
	private static PracticePropUtil practicePropUtil = new PracticePropUtil();
	public static PracticePropUtil getPracticePropUtil() {
		return practicePropUtil;
	}
	
	public static PracticePropUtil getPracticePropUtil(IActionManager actionManager) {
		return new PracticePropUtil(actionManager);
	}
	
	protected PracticePropUtil() {		
	}
	
	protected PracticePropUtil(IActionManager actionManager) {
		super(actionManager);
	}
	
	public void storeUtdData(Practice practice, UserDefinedTypeMeta meta)  throws Exception {
		PracticeXmlEditUtil xmlEditUtil = new PracticeXmlEditUtil(practice, this);
		xmlEditUtil.storeUtdData(meta);
	}
	
	public  UserDefinedTypeMeta getUtdData(Practice practice)  throws Exception {
		UserDefinedTypeMeta meta = new UserDefinedTypeMeta();
		PracticeXmlEditUtil xmlEditUtil = new PracticeXmlEditUtil(practice, this);
		xmlEditUtil.retrieveUtdData(meta);
		return meta.getId() == null ? null : meta;
	}
	
	public  boolean isUtdType(MethodElement element) {
		if (element instanceof Practice) {
			try {
				return getUtdData((Practice) element) != null;			
			} catch (Exception e) {
				LibraryEditPlugin.getDefault().getLogger().logError(e);
			}
		}
		return false;
	}
	
	public  boolean isUtdType(MethodElement element, String typeName) {
		if (typeName != null && element instanceof Practice) {
			try {
				UserDefinedTypeMeta meta = getUtdData((Practice) element);
				if (meta != null) {
					return typeName.equals(meta.getRteNameMap().get(UserDefinedTypeMeta._typeName));
				}
			} catch (Exception e) {
				LibraryEditPlugin.getDefault().getLogger().logError(e);
			}
		}
		return false;
	}
	
	public List<MethodElement> getUdtReferencingList(Practice practice) {
		String ofeature = ExtendReferenceMap.getOppositeName(ExtendReferenceMap.UtdList);
		List<MethodElement> list = (List<MethodElement>) getReferenceValue(ofeature, practice, false);
		return list == null ? new ArrayList<MethodElement>() : list;
	}
	
	private static class PracticeXmlEditUtil extends XmlEditUtil {
		
		private Practice practice;
		
		public static final String _id = "id"; 					//$NON-NLS-1$		
		
		public PracticeXmlEditUtil(Practice practice, MethodElementPropUtil propUtil) {
			super(propUtil);
			this.practice = practice;
		}		
		
		public void storeUtdData(UserDefinedTypeMeta meta) throws Exception {
			if (practice == null) {
				return;
			}
			Map<String, String> map = meta.getRteNameMap();
			Element firstElement = createFirstElement(Practice_UtdData);
			firstElement.setAttribute(_id, meta.getId());
			for (String name : UserDefinedTypeMeta.rteNames) {
				String value = map.get(name);
				if (value != null && value.trim().length() > 0) {
					firstElement.setAttribute(name, value.trim());
				}
			}			
			storeToOwner(practice, Practice_UtdData);	
		}
		
		public void retrieveUtdData(UserDefinedTypeMeta meta) throws Exception {
			Map<String, String> map = meta.getRteNameMap();
			
			String xmlString = getPropUtil().getStringValue(practice, Practice_UtdData);
			if (xmlString == null || xmlString.trim().length() == 0) {
				return;
			}
			Element firstElement = loadDocumentAndGetFirstElement(xmlString);	
				
			String value = firstElement.getAttribute(_id);
			if (value != null && value.length() > 0) {
				meta.setId(value);
			}
			for (String name : UserDefinedTypeMeta.rteNames) {
				value = firstElement.getAttribute(name);
				if (value != null && value.length() > 0) {
					map.put(name, value);
				}
			}
		}
		
	}

	
	
}
