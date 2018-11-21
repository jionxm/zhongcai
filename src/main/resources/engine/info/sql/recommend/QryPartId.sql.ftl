select * from (
	select 
	p.id as id,
	p.group_id as groupId,	g.name as groupName,
	e.emp_id as empId
	
	from t_participants p  
	LEFT JOIN t_group g ON p.group_id=g.id
	LEFT JOIN t_group_emp e ON e.group_id=p.group_id
	where p.recommend_id = #{data.recId }
 ) a 