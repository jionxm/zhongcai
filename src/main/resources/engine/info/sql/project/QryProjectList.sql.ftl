select * from (
	select 
	p.id as id,
	p.name as name,
	p.year as year,	
	p.plan_date as planDate,
	p.address as address,
	p.message as message,
	p.state as state,
	d3.name as stateName,
	p.update_by as updateBy,
	p.create_by as createBy,
	p.update_time as updateTime,
	p.create_time as createTime  
	
	from t_project p 
	LEFT JOIN t_dict d3 ON p.state=d3.code and d3.cata_code="t_recommend.state"
 ) a 