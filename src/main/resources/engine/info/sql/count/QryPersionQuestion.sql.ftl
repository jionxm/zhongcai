select 
		e.`name` as empName,
		r.emp_id,
		GROUP_CONCAT(r.result ORDER BY q.dimension,q.question)as results,
    	FORMAT(avg(r.result),2) as avgResult,
		p.`name` as projectName,
		GROUP_CONCAT(q.question ORDER BY q.dimension,q.question) as questions,
		GROUP_CONCAT(dimension order by dimension) as dimension
from t_result r

LEFT JOIN t_questions q on q.id=r.question_id
LEFT JOIN t_qr qr on qr.id=r.QR_id
LEFT JOIN t_project_group g on g.id=qr.proj_group_id
LEFT JOIN t_project p on p.id = g.project_id
LEFT JOIN t_employee e on e.id=r.emp_id 

where g.id=#{data.groupId} and p.id=#{data.projId} group by emp_id  ORDER BY avgResult desc 

