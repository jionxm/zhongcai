select * from (
	select 
	p.id as post,
	p.name as postName
	from t_position p  
	where p.id not IN (select post from t_recommend_type where recommend_id=#{data.id})
 ) a 