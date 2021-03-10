package com.ibeetl.admin.core.gen;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

import com.ibeetl.admin.core.gen.model.Entity;

public class JSGen  implements AutoGen{

	@Override
	public void make(Target target, Entity entity) {
		JSDelGen  delGen = new JSDelGen();
		delGen.make(target, entity);
		JSAddGen addGen = new JSAddGen();
		addGen.make(target, entity);
		JSEditGen editGen = new JSEditGen();
		editGen.make(target, entity);
		JSApiGen apiGen = new JSApiGen();
		apiGen.make(target, entity);
		JSIndexGen indexGen = new JSIndexGen();
		indexGen.make(target, entity);
	}
	@Override
	public String getName() {
		return "";
	}
	
}

class JSDelGen  implements AutoGen{

	@Override
	public void make(Target target, Entity entity) {
		GroupTemplate gt = target.getGroupTemplate();
		Template template = gt.getTemplate("/js/del.js");
		template.binding("entity", entity);
		template.binding("target", target);
		String content = template.render();
		target.flush(this, content);
			
	}
	@Override
	public String getName() {
		return "del.js";
	}
	
}
class JSAddGen  implements AutoGen{
	
	@Override
	public void make(Target target, Entity entity) {
		GroupTemplate gt = target.getGroupTemplate();
		Template template = gt.getTemplate("/js/add.js");
		template.binding("entity", entity);
		template.binding("target", target);
		String content = template.render();
		target.flush(this, content);
		
	}
	@Override
	public String getName() {
		return "add.js";
	}
	
}

class JSApiGen  implements AutoGen{
	
	Entity entity;
	@Override
	public void make(Target target, Entity entity) {
		this.entity =entity;
		GroupTemplate gt = target.getGroupTemplate();
		Template template = gt.getTemplate("/js/entityApi.js");
		template.binding("entity", entity);
		template.binding("target", target);
		String content = template.render();
		target.flush(this, content);
		
	}
	@Override
	public String getName() {
		return entity.getCode()+"Api.js";
	}
	
}


class JSEditGen  implements AutoGen{
	
	@Override
	public void make(Target target, Entity entity) {
		GroupTemplate gt = target.getGroupTemplate();
		Template template = gt.getTemplate("/js/edit.js");
		template.binding("entity", entity);
		template.binding("target", target);
		String content = template.render();
		target.flush(this, content);
		
	}
	@Override
	public String getName() {
		return "edit.js";
	}
	
}

class JSIndexGen  implements AutoGen{
	
	@Override
	public void make(Target target, Entity entity) {
		GroupTemplate gt = target.getGroupTemplate();
		Template template = gt.getTemplate("/js/index.js");
		template.binding("entity", entity);
		template.binding("target", target);
		String content = template.render();
		target.flush(this, content);
	}
	@Override
	public String getName() {
		return "index.js";
	}
	
}




