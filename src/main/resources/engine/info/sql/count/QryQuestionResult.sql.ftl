select 
	q.dimension,
	avg(rs.result) avgScore,
	q.question
from t_result rs 
LEFT JOIN t_questions q on q.id=rs.question_id
LEFT JOIN t_test t on t.id = q.test_id
LEFT JOIN t_project_group g on g.test_id=t.id 
LEFT JOIN t_project tp on tp.id=g.project_id
LEFT JOIN t_tester_number n on n.pro_group_id = g.id
where g.id=#{data.groupId } and tp.id=#{data.projId} and  rs.QR_id in (select tq.id from t_qr tq where tq.project_id=tp.id and tq.tester_id=#{data.dimension})
group by q.id   

ORDER BY q.dimension,q.question