select * from (
	select 
	p.id as id,
	p.name as name,
	p.year as year,	
	p.type as type,	
	p.plan_date as planDate,
	p.address as address,
	p.message as message,
	p.state as state,
	d3.name as stateName,
	d1.name as typeName,
	p.qrstate as qrState,
	case when p.qrstate = 1 then "已生成" when p.qrstate = 0 then "未生成" end as qrStateName,
	p.update_by as updateBy,
	p.create_by as createBy,
	p.update_time as updateTime,
	p.create_time as createTime,  
	p.file_id as fileId
	from t_project p 
	LEFT JOIN t_dict d3 ON p.state=d3.code and d3.cata_code="t_recommend.state"
	LEFT JOIN t_dict d1 ON p.type=d1.code and d1.cata_code="t_test.type"
 ) a 