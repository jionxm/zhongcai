SELECT * from(
	SELECT 
	r.result as rs
from t_project_group pg
LEFT JOIN t_qr q on q.proj_group_id = pg.id
LEFT JOIN t_group g on g.id = pg.group_id
LEFT JOIN t_result r on r.QR_id = q.id
WHERE pg.project_id = #{data.projId } and g.name = #{data.groupname }

) a 