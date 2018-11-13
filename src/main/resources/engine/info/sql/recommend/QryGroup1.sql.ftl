select * from (
	select 
	r.id as recommendId,
	p.group_id as groupId,
	p.number as number,
	g.name as groupName,
	r.title as title
	
	from t_participants p  
	LEFT JOIN t_recommend r ON p.recommend_id=r.id
	LEFT JOIN t_group g ON p.group_id=g.id
	where p.id = #{data.recId }
 ) a 