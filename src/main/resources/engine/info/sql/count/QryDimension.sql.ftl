select 
	q.dimension,
	count(dimension) as dimenCount
from t_questions q 
LEFT JOIN t_test t on t.id = q.test_id
LEFT JOIN t_project_group g on g.test_id=t.id  where g.id=#{data.groupId} and  g.project_id=#{data.projId}   group by dimension order by q.dimension

