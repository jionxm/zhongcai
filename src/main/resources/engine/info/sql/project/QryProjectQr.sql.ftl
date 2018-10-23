select * from (
	select 	
	q.id as id,
	q.project_id as projectId,
	q.QR_code as QRCode,
	q.proj_group_id as projGroupId,
	q.state as state,
	q.tester_id as testerId,
	q.file_id as fileId,
	t.name as createBy,	
	q.create_time as createTime,
	t2.name as updateBy,
	q.update_time as updateTime
	
	
	from t_qr q
	LEFT JOIN t_employee t ON t.id=q.create_by 
	LEFT JOIN t_employee t2 ON t2.id=q.update_by 
 ) a 