select * from (
	select 
	q.id as id,
	q.project_id as projectId,
	p.name as projectName,
	q.QR_code as QRCode,	
	q.proj_group_id as projGroupId,
	g.name as groupName,
	q.state as state,
	case when q.state = 1 then "可用" when q.state = 0 then "不可用" end as stateName,
	q.tester_id as testerId,
	t.name as testerName1,
	d.name as testerName,
	q.file_id as fileId,
	t.test_id,
	tt.type as testType,
	q.update_by as updateBy,
	q.create_by as createBy,
	q.update_time as updateTime,
	q.create_time as createTime  
	
	from t_qr q
	LEFT JOIN t_project p ON q.project_id=p.id
	LEFT JOIN t_tester t ON q.tester_id=t.id
	LEFT JOIN t_test tt ON tt.id = t.test_id
	LEFT JOIN t_group g ON g.id = (select group_id from t_project_group where id= q.proj_group_id)
	LEFT JOIN t_dict d ON d.code = t.name and d.cata_code="t_employee.identity"
 ) a 