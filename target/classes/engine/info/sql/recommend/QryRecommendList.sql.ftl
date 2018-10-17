select * from (
	select 
	r.id as id,
	r.title as title,
	r.type as type,	
	d1.name as typeName,
	r.plan_date as planDate,
	r.address as address,
	r.message as message,
	r.method as method,
	d2.name as methodName,
	r.state as state,
	d3.name as stateName,
	r.update_by as updateBy,
	r.create_by as createBy,
	r.update_time as updateTime,
	r.create_time as createTime  
	
	from t_recommend r  
	LEFT JOIN t_dict d1 ON r.type=d1.code=d1.code and d1.cata_code="t_recommend.type"
  	LEFT JOIN t_dict d2 ON r.method=d2.code and d2.cata_code="t_recommend.method"
	LEFT JOIN t_dict d3 ON r.state=d3.code and d3.cata_code="t_recommend.state"
 ) a 