select 
	q.dimension,
	q.question,
	CONCAT(ifnull(q.weight,0),'%') as weight
from t_questions q 
LEFT JOIN t_test t on t.id = q.test_id
LEFT JOIN t_project_group g on g.test_id=t.id   where g.id=#{data.groupId}   order by dimension,question
