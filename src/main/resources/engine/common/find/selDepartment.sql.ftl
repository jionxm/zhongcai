select * from (
	select 
	id as id,
	code as deptCode,
	name as departmentName 
	from t_org
 ) a 