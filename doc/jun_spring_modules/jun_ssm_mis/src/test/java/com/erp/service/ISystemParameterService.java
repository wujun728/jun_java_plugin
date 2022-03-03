package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.Parameter;
import com.erp.viewModel.ParameterModel;

public interface ISystemParameterService
{

	List<ParameterModel> findParameterList(String type );

	boolean persistenceParameter(Map<String, List<Parameter>> map );

}
