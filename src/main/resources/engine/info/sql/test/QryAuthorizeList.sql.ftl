SELECT
	t.id,
	t.question_id as questionId,
	t.state,
	t.ztqz_id as ztqzId,
    q.question as questionName,
    q.dimension,
	t.create_by as createBy,
	t.create_time as createTime ,
	t.update_by as updateBy,
	t.update_time as updateTime
FROM
	t_question_authorize t left join t_questions q on t.question_id=q.id where t.ztqz_id=#{data.ztqzId}
