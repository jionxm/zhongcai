select * from (
	SELECT
		question_id as questionId,
		result as result,
		question as questionName
	from t_result r
	left join t_questions q on q.id = r.question_id
	where QR_id = #{data.ctlId }
 ) a 