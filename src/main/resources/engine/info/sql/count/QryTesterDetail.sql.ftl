select * from (
	select 
		g.id as groupId,
		d.`name` as type,
		CONCAT('(',e.dimension,'%)') as percent,
		ifnull((select n.number from t_tester_number  n where n.pro_group_id=g.id and e.id=n.dimension),0) as count
FROM
t_project_group g

LEFT JOIN t_tester e on e.test_id=g.test_id
LEFT JOIN t_dict d on d.`code`=e.`name`

where g.id=  #{data.groupId}
)a