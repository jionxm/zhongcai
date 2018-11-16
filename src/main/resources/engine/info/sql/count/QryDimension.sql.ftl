select * from (
select 
	q.dimension,
	(select count(1) from t_questions qs where qs.dimension=q.dimension) as dimenCount
from t_questions q 
LEFT JOIN t_test t on t.id = q.test_id
LEFT JOIN t_project_group g on g.test_id=t.id  where g.id=#{data.groupId} group by q.dimension order by q.dimension

)a