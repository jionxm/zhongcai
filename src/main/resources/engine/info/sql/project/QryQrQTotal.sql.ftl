select * from (
	SELECT count(*) as total,
		b.questionNumber,
		b.questionName,
		qr.id as QRId,
		b.testId,
		b.projectId,
		b.ztqzId,
		b.projectGroupId,
		b.dimension,
		b.questionId,
		b.chooseType
	from (
		SELECT
			g.test_id as testId,
			g.project_id as projectId,
			g.id as projectGroupId,
			ta.ztqz_id as ztqzId,
			q.id as questionId,
			q.number as questionNumber,
			q.question as questionName,
			q.dimension as dimension,
			q.type as chooseType
		from t_questions q
			LEFT JOIN t_project_group g on g.test_id = q.test_id
			LEFT JOIN t_question_authorize ta on ta.question_id = q.id
			
) b
		LEFT JOIN t_qr qr on b.projectId = qr.project_id
		where b.projectId=#{data.ctlProjectId } and b.testId = #{data.ctlTestId } and b.ztqzId=#{data.ctlTesterId } and qr.id=#{data.ctlId } and b.projectGroupId=#{data.ctlProjGroupId }
 ) a 