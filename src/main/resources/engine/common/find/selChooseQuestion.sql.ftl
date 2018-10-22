select * from (
select 
	t.id as questionId,
	t.dimension,
    t.question
from t_questions t where t.test_id=(select te.test_id from t_tester te where te.id=#{data.id}) and not exists  (select q.id from t_question_authorize q where q.question_id=t.id  and q.ztqz_id=#{data.id})
 ) a 