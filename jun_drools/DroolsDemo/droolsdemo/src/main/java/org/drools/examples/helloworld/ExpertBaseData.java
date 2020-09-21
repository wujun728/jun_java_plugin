package org.drools.examples.helloworld;

import java.util.UUID;

public class ExpertBaseData {
	
	private String id;
	
	private InputDataModel inputDataModel;
	
	private OutputDataModel outputDataModel = new OutputDataModel();
	
	public String getId() {
		id = UUID.randomUUID().toString();
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public InputDataModel getInputDataModel() {
		return inputDataModel;
	}

	public void setInputDataModel(InputDataModel inputDataModel) {
		this.inputDataModel = inputDataModel;
	}

	public OutputDataModel getOutputDataModel() {
		return outputDataModel;
	}

	public void setOutputDataModel(OutputDataModel outputDataModel) {
		this.outputDataModel = outputDataModel;
	}
	
	

}
