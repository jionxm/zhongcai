select 
	qr.id as qrId,
	t.id as projectId,
	t.`name` as projectName,
	t.message as message,
	qr.file_id as file_id,
	qr.qr_code,
	qr.state,
	(select  td.`name` from t_tester te INNER JOIN t_dict td on td.code = te.`name` where te.id=qr.tester_id) as groupName
from t_project t inner join t_qr qr on qr.project_id=t.id  where qr.QR_code=#{data.id} and qr.state='1'