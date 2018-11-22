select * from (
select 
	e.id as id,
	e.name as name
from t_employee e where id NOT IN (select g.emp_id from t_group_emp g where g.group_id=#{data.id})
 ) a 