select * from (
	select 
	p.id as id,
	p.group_id as groupId,
	g.name as groupName,
	p.number as number,
	p.recommend_id as recommendId
	
	from t_participants p  
	LEFT JOIN t_group g ON p.group_id=g.id
	where p.id = #{data.id }
 ) a 