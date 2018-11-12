select * from (
	select 
		q.question,
		q.id as questionId,
		c.id as typeId,
		c.name as typeName,
		cv.name as valueName,
		q.type as chooseType,
		cv.score as score
		
	from t_choose c
		LEFT JOIN t_choose_value cv ON c.id = cv.choose_id
		LEFT JOIN t_questions q ON q.choose_id = c.id
 ) a 