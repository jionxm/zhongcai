select 
		max(r.result+0) as maxScore,
		min(r.result+0) as minScore,
		avg(r.result+0) as avgScore,
    q.question,
		p.`name` as projectName,
		dimension 	
from t_result r
LEFT JOIN t_questions q on q.id=r.question_id
LEFT JOIN t_qr qr on qr.id=r.QR_id
LEFT JOIN t_project_group g on g.id=qr.proj_group_id
LEFT JOIN t_project p on p.id = g.project_id
LEFT JOIN t_employee e on e.id=r.emp_id 

where  g.id=#{data.groupId } and p.id=#{data.projId}   group by q.question ORDER BY q.dimension
