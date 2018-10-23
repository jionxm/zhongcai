select * from (
	select 
	CODE as value,
	NAME as text from t_dict t WHERE cata_code='t_questions.type'  and t.code !='completion'
 ) a 
 
 
