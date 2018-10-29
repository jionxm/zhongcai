select * from (
	SELECT
		question_number as questionNumber,
		result as result,
		question as questionName
	from t_result r
	left join t_questions q on q.number = r.question_number
	where QR_id = #{data.ctlId }
 ) a 