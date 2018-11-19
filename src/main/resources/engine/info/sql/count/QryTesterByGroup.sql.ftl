
select 
	n.dimension,
	td.`name` as dimensionName
from t_project_group g 
LEFT JOIN t_tester_number n on n.pro_group_id=g.id
LEFT JOIN t_project p on p.id=g.project_id
LEFT JOIN t_tester t on t.id=n.dimension
LEFT JOIN t_dict td on td.`code`=t.`name`

where g.id=  #{data.groupId} and p.id=#{data.projId}
