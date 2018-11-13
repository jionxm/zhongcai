select * from (
	select 
	p.id as ctlId,
	p.group_id as groupId,
	p.number as number,
	g.name as groupName
	
	from t_participants p  
	LEFT JOIN t_group g ON p.group_id=g.id
	where p.id = #{data.ctlId }
 ) a 