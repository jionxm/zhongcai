SELECT * from(
	SELECT 
g.name as groupName
from t_project_group pg
LEFT JOIN t_group g on g.id = pg.group_id
WHERE pg.project_id = #{data.projId }

) a 