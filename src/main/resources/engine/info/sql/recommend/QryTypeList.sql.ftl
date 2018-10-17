select * from (
	select 
	r.id as id,
	r.post as post,	
	p.name as postName,
	r.mode as mode,
	d1.name as modeName,
	r.number_min as numberMin,
	r.number_max as numberMax,
	r.basic as basic,
	r.update_by as updateBy,
	r.create_by as createBy,
	r.update_time as updateTime,
	r.create_time as createTime  
	from t_recommend_type r  
	LEFT JOIN t_dict d1 ON d1.code = r.mode
	LEFT JOIN t_position p ON p.id = r.post
	where r.recommend_id = #{data.ctlId }
 ) a 