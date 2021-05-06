package com.jun.plugin.designpatterns.structurePatterns.adapter.adapter_object;

public class Adapter2 implements TargetInterface {
	private Source source;
	public Adapter2(Source source) {
		this.source=source;
	}

	@Override
	public void method() {
		source.method();
	}

	@Override
	public void methodNew() {
		System.err.println("apapter2 methodNew run ... ");
	}

}
