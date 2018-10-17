	select * from (
		SELECT 
			issue.id,
			issue.id as issueId,
			issue.proj_id as projId, 
			issue.title,issue.priority,
			pro.name as projName,	
			empfir.name AS reporterName ,issue.reporter ,
			sprint.name AS sprintName ,issue.sprint_id as sprintId,
			empsec.name AS createByName,issue.create_by as createBy, 
			empthr.name AS updateByName,issue.update_by as updateBy,
			dictfir.name AS issueStatusName,issue.status as issueStatus,
			empfor.name AS testDesignByName,issue.test_design_by as testDesignBy,
			empfif.name AS assigneeName, issue.assignee,
			empsix.name AS testByName,issue.test_by as testBy,
			dictsec.name AS issueTypeName,issue.type as issueType,
			dictthr.name AS priorityName ,issue.workload,issue.remark,
			issue.create_time as createTime,issue.update_time as updateTime 
			,dr.name as riskResultTypeName
			,case when issue.status='close' then issue.update_time else null end as closeDate 
			,issue.risk_latest_date as riskLatestDate,issue.risk_resolve_date as  riskResolveDate 
			,issue.risk_result as riskResult   ,issue.risk_result_type as riskResultType  
		FROM 
			t_issue issue 
		LEFT JOIN 
			t_project pro 
		ON 
			issue.proj_id = pro.id												 		 
		LEFT JOIN 
			t_employee empfir 	
		ON 
			issue.reporter=empfir.id 											 		 
		LEFT JOIN 
			t_employee empsec
		ON 
			issue.create_by = empsec.id 											 		 
		LEFT JOIN 
			t_employee empthr 
		ON 
			issue.update_by = empthr.id 											 		 
		LEFT JOIN 
			t_employee empfor
		ON 
			issue.test_design_by = empfor.id 									 		 
		LEFT JOIN 
			t_employee empfif
		ON 
			issue.assignee = empfif.id 											 		
		LEFT JOIN 
			t_employee empsix
		ON 
			issue.test_by = empsix.id 											 	 
		LEFT JOIN 
			t_sprint sprint
		ON 
			issue.sprint_id = sprint.id 										 	 
		LEFT JOIN 
			t_dict dictfir
		ON 
			issue.status = dictfir.code and dictfir.cata_code ='T_ISSUE.STATUS'	 		 
		LEFT JOIN t_dict dictsec ON issue.type = dictsec.code and dictsec.cata_code ='T_ISSUE.TYPE' 			 
		LEFT JOIN 
			t_dict dictthr
		ON 
			issue.priority = dictthr.code 
		AND 
			dictthr.cata_code ='T_ISSUE.PRIORITY'

		LEFT JOIN t_dict dr ON issue.risk_result_type=dr.code and dr.cata_code = 't_issue.risk_result_type' 

		WHERE issue.TYPE='risk'
	) a