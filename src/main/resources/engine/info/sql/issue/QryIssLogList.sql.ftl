select * from (
	select 
	l.id as id,
	l.issue_id as issueId,
	l.type as type,
	d.name as typeName,
	l.log_content as logContent,
	l.create_by as createBy,
	l.create_time as createTime,
	e.name as createByName,
	l.update_status as updateStatus,
	d1.name as upStatusName,
	t.title
	from t_issue_log l
	left join t_employee e on e.id = l.create_by
	left join t_dict d on d.code = l.type and d.cata_code = 't_issue_log.type'
	left join t_dict d1 on d1.code = l.update_status and d1.cata_code = 't_issue.status'
	left join t_issue t on t.id=l.issue_id	
 ) a