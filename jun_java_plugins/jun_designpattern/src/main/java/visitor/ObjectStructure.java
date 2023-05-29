package visitor;

import java.util.ArrayList;
import java.util.List;

public class ObjectStructure {
	List<Element> elements = new ArrayList<Element>();
	
	public void handleElements(Visitor visitor) {
		for (Element element : elements) {
			element.accept(visitor);
		}
	}
	
	public void addElement(Element element) {
		elements.add(element);
	}
}
