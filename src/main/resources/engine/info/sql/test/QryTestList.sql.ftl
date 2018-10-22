select * from (
	select 
	t.id as id,
	t.title as title,
	t.type as type,	
	t.group_id as groupId,
	g.name as groupName,
	t.version as version,
	d1.name as typeName,
	t.message as message,
	t.state as state,
	d3.name as stateName,
	t.update_by as updateBy,
	t.create_by as createBy,
	t.update_time as updateTime,
	t.create_time as createTime  
	
	from t_test t 
	LEFT JOIN t_dict d1 ON t.type=d1.code and d1.cata_code="t_test.type"
	LEFT JOIN t_dict d3 ON t.state=d3.code and d3.cata_code="t_recommend.state"
	LEFT JOIN t_group g ON t.group_id=g.id
 ) a 