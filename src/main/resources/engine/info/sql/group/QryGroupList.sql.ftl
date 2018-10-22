select * from (
	select 
	case when(select count(id) from t_test where group_id=g.id) >=1 then 0 
	when(select count(id) from t_project_group where group_id=g.id ) >=1 then 0 else 1 end as det,
	g.id as id,
	g.name as name,
	t.name as createBy,
	t2.name as updateBy,
	g.update_time as updateTime,
	g.create_time as createTime  
	from t_group g 
	LEFT JOIN t_employee t ON t.id=g.create_by 
	LEFT JOIN t_employee t2 ON t2.id=g.update_by 
 ) a 