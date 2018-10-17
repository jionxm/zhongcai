select * from (
	select 
	m.id as id,
	m.memo as memo,
	m.issue_id as issueId,
	m.create_time as createTime,
	m.create_by as createBy,
	e.name as createByName
	from t_issue_memo m
	left join t_employee e on e.id = m.create_by
	
 ) a 