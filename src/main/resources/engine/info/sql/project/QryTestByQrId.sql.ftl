select 
	qr.id as qrId,
	t.id as projectId,
	t.`name` as projectName,
	t.message as message,
	qr.file_id as qrCode,
	(select g.`name` from T_PROJECT_GROUP pg INNER JOIN t_group g on g.id=pg.group_id where pg.id=qr.proj_group_id) as groupName
from t_project t inner join t_qr qr on qr.project_id=t.id  where qr.id=#{data.id}