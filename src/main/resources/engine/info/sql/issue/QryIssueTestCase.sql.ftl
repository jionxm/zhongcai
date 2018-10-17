select * from (
	select 
	
	i.id as id,
	t.issue_id as issueId,
	i.title as name,
	i.create_time as createTime,
	i.update_time as updateTime,
	i.remark as remark,
	empsec.name as createBy,
	empthr.name as updateBy
	from t_issue i
	left JOIN  t_testcase t
	on i.testcase_id=t.id
	LEFT JOIN 
		t_employee empsec
	ON 
		i.create_by = empsec.id 											 		 
	LEFT JOIN 
		t_employee empthr 
	ON 
		i.update_by = empthr.id
		
		where i.type="bug" and i.proj_id=#{data.projId }
 ) a 