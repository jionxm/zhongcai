	select 
	q.id as id,t.test_id as testId,
	q.QR_code as QRCode,	
	q.proj_group_id as projGroupId,
	g.name as groupName,
	q.tester_id as testerId,
	d.name as testerName,
	q.file_id as fileId
	from t_qr q
	LEFT JOIN t_project p ON q.project_id=p.id
	LEFT JOIN t_tester t ON q.tester_id=t.id
	LEFT JOIN t_test tt ON tt.id = t.test_id
	LEFT JOIN t_group g ON g.id = (select group_id from t_project_group where id= q.proj_group_id)
	LEFT JOIN t_dict d ON d.code = t.name and d.cata_code="t_employee.identity" where p.id=#{data.id} and q.proj_group_id=#{data.projGroupId} order by d.`name`
