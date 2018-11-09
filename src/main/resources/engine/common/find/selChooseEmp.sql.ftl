select * from (
select 
	e.id as id,
	e.name as name
from t_employee e where id not IN (select g.emp_id from t_participants g where g.recommend_id=#{data.id})
 ) a 