steal('jquery/class').then(function($) {

	$.Class("Plugins.Common.Enum", {
		getTrueOrFalse:function()
        {
            return [                   
            	{"value":"true","name":$.t("common.true")},
            	{"value":"false","name":$.t("common.false")}];
        },
		getAuditStatus:function()
        {
            return [                   
            	{"value":"UNAUDITED","name":$.t("select.audit_status.UNAUDITED")},
            	{"value":"AUDITING","name":$.t("select.audit_status.AUDITING")},
            	{"value":"AUDIT_SUCCESS","name":$.t("select.audit_status.AUDIT_SUCCESS")},
            	{"value":"AUDIT_FAILED","name":$.t("select.audit_status.AUDIT_FAILED")},
            	{"value":"CHANGED","name":$.t("select.audit_status.CHANGED")}];
        },
        
        getBlockStatus:function(){
        	return [                   
                	{"value":"block","name":$.t("select.block_status.block")},
                	{"value":"active","name":$.t("select.block_status.active")}];
        },
        
        getLockStatus:function(){
        	return [                   
                	{"value":"lock","name":$.t("select.lock_status.lock")},
                	{"value":"unlock","name":$.t("select.lock_status.unlock")}];
        },
        
        getProjectType:function(){
        	return [
        	        {"value":"SELFTEST","name":$.t("project_process.SELFTEST")},
        	        {"value":"ADMISSIONTEST","name":$.t("project_process.ADMISSIONTEST")},
        	        {"value":"STEREOTYPETEST","name":$.t("project_process.STEREOTYPETEST")}];
        },
        
        getProjectLevel:function(){
        	return [
        	        {"value":"KEY","name":$.t("project_process.KEY")},
        	        {"value":"IMPORTANT","name":$.t("project_process.IMPORTANT")},
        	        {"value":"GENERAL","name":$.t("project_process.GENERAL")}];
        },
        
        getInfoLevel:function(){
        	return [
        	        {"value":"public","name":$.t("project.security_array.public")},
        	        {"value":"inside","name":$.t("project.security_array.inside")},
        	        {"value":"secret","name":$.t("project.security_array.secret")},
        	        {"value":"confidential","name":$.t("project.security_array.confidential")},
        	        {"value":"normal_secret","name":$.t("project.security_array.normal_secret")},
        	        {"value":"key_secret","name":$.t("project.security_array.key_secret")}];
        },
        
        getEnvType:function(){
        	return [
        	        {"value":"SOFTWARE","name":$.t("project.evn_config_array.evn_array.type_array.software")},
        	        {"value":"HARDWARE","name":$.t("project.evn_config_array.evn_array.type_array.hardware")},
        	        {"value":"OTHER","name":$.t("project.evn_config_array.evn_array.type_array.other")}];
        },
        
        getTestProcess:function(){
        	return [
        	        {"value":"DOC_REVIEW","name":$.t("project.stages.doc_review")},
        	        {"value":"CODE_REVIEW","name":$.t("project.stages.code_review")},
        	        {"value":"STATIC_ANALYSIS","name":$.t("project.stages.static")},
        	        {"value":"UNIT_TEST","name":$.t("project.stages.units")},
        	        {"value":"PART_TEST","name":$.t("project.stages.assembly")},
        	        {"value":"CONFIG_TEST","name":$.t("project.stages.config")},
        	        {"value":"SYSTEM_TEST","name":$.t("project.stages.system")}];
        },
        
        getProjectStates:function(){
        	return [
        	        {"value":"NEW","name":$.t("project.NEW")},
        	        {"value":"SETUP","name":$.t("project.SETUP")},
        	        {"value":"COMPLETED","name":$.t("project.COMPLETED")}];
        },
        
        getTaskStatus:function(){
        	return [
        	        {"value":"NEW","name":$.t("test_task.task_status.NEW")},
        	        {"value":"ACCEPTED","name":$.t("test_task.task_status.ACCEPTED")},
        	        {"value":"ONGOING","name":$.t("test_task.task_status.ONGOING")},
        	        {"value":"FINISHED","name":$.t("test_task.task_status.FINISHED")}];
        },
        
        getCheckMinUnit:function(){
        	return[
        	       {"value":"0.5","name":"0.5"+$.t("common.man_hour")},
        	       {"value":"1","name":"1"+$.t("common.man_hour")},
        	       {"value":"4","name":"4"+$.t("common.man_hour")},
        	       {"value":"8","name":"8"+$.t("common.man_hour")}]
        },
        
        getAttendanceType:function(){
        	return[
        	       {"value":"NORMAL","name":$.t("select.workload_type.NORMAL")},
        	       {"value":"HOLIDAY","name":$.t("select.workload_type.HOLIDAY")},
        	       {"value":"NIGHT","name":$.t("select.workload_type.NIGHT")}
        	       ]
        },
        
        getCheckInStatus:function(){
        	return[
        	       {"value":"NORMAL","name":$.t("human_resources.attendance_manage.normal")},
        	       {"value":"LATE","name":$.t("human_resources.attendance_manage.late")},
        	       {"value":"CLEAR","name":$.t("human_resources.attendance_manage.clear")},
        	       {"value":"SPECIAL","name":$.t("human_resources.attendance_manage.special")}
        	       ]
        },
        
        getCheckOutStatus:function(){
        	return[
        	       {"value":"NORMAL","name":$.t("human_resources.attendance_manage.normal")},
        	       {"value":"EARLY","name":$.t("human_resources.attendance_manage.early")},
        	       {"value":"NO_CHECK_OUT","name":$.t("human_resources.attendance_manage.no_check_out")},
        	       {"value":"CLEAR","name":$.t("human_resources.attendance_manage.clear")},
        	       {"value":"SPECIAL","name":$.t("human_resources.attendance_manage.special")}
        	       ]
        },
        
        getWorkloadType:function(){
        	return[
        	       {"value":"NORMAL","name":$.t("select.workload_type.NORMAL")},
        	       {"value":"HOLIDAY","name":$.t("select.workload_type.HOLIDAY")},
        	       {"value":"NIGHT","name":$.t("select.workload_type.NIGHT")},
        	       {"value":"LEAVE","name":$.t("select.workload_type.LEAVE")}
        	       ]
        },
        
        getExecStatus:function(){
        	return[
        	       {"value":"NA","name":$.t("test_case.exec_status_list.NA")},
        	       {"value":"EXECUTED","name":$.t("test_case.exec_status_list.EXECUTED")},
        	       {"value":"NON_EXECUTED","name":$.t("test_case.exec_status_list.NON_EXECUTED")}
        	       ]
        }
        
	}, {
		init: function() {	
		    
		}
	});
});
