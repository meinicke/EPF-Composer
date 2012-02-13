package org.eclipse.epf.library.edit.meta.internal;

import org.eclipse.epf.common.utils.XMLUtil;
import org.eclipse.epf.library.edit.meta.IMetaDef;
import org.eclipse.epf.library.edit.meta.TypeDefException;
import org.eclipse.epf.uma.util.MetaElement;
import org.w3c.dom.Element;

public class MetaElementImpl implements MetaElement, IMetaDef {

	private String id;
	private String name;
	private String globalId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getGlobalId() {
		return globalId;
	}
	
	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}

    public int compareTo(MetaElement o) {
    	return getName().compareTo(o.getName());
    }
	
	public void parseElement(Element element) throws TypeDefException {
		if (element == null) {
			return;
		}
		id = element.getAttribute(IMetaDef.ID);
		name = element.getAttribute(IMetaDef.NAME);
		globalId = id;
		Element nameElement = XMLUtil.getFirstChildElementByTagName(element, IMetaDef.NAME);
		if (nameElement != null) {
			name = element.getTextContent();
		}		
	}
	
}
