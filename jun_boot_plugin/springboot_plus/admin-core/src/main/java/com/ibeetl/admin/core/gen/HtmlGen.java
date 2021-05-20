package com.ibeetl.admin.core.gen;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

import com.ibeetl.admin.core.gen.model.Entity;

public class HtmlGen  implements AutoGen{

	@Override
	public void make(Target target, Entity entity) {
		
		HtmlIndexGen indexGen = new HtmlIndexGen();
		indexGen.make(target, entity);
		
		HtmlEditGen editGen = new HtmlEditGen();
		editGen.make(target, entity);
		
		HtmlAddGen addGen = new HtmlAddGen();
		addGen.make(target, entity);
	}
	
	@Override
	public String getName() {
		return "";
	}
	
}


class HtmlIndexGen  implements AutoGen{
	
	@Override
	public void make(Target target, Entity entity) {
		GroupTemplate gt = target.getGroupTemplate();
		Template template = gt.getTemplate("/html/index.html");
		template.binding("entity", entity);
		template.binding("target", target);
		String content = template.render();
		target.flush(this, content);
	}

	@Override
	public String getName() {
		return "index.html";
	}
	
}

class HtmlEditGen  implements AutoGen{
	
	@Override
	public void make(Target target, Entity entity) {
		GroupTemplate gt = target.getGroupTemplate();
		Template template = gt.getTemplate("/html/edit.html");
		template.binding("entity", entity);
		template.binding("target", target);
		String content = template.render();
		target.flush(this, content);
	}
	@Override
	public String getName() {
		return "edit.html";
	}
	
}

class HtmlAddGen  implements AutoGen{
	
	@Override
	public void make(Target target, Entity entity) {
		GroupTemplate gt = target.getGroupTemplate();
		Template template = gt.getTemplate("/html/add.html");
		template.binding("entity", entity);
		template.binding("target", target);
		String content = template.render();
		target.flush(this, content);
	}
	@Override
	public String getName() {
		return "add.html";
	}
	
}




