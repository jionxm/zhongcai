select * from (
	select 
	t.id as id,
	t.test_id as testId,
	t.dimension as dimension,
	t.question as question,	
	t.weight as weight,
	t.type as type,
	d1.name as typeName,
	t.must as must,
	d3.name as mustName,
	t.text_length as textLength,
	t.choose_id as chooseId,
	c.name as chooseName,
	t.number as number,
	t.update_by as updateBy,
	t.create_by as createBy,
	t.update_time as updateTime,
	t.create_time as createTime  
	
	from t_questions t 
	LEFT JOIN t_dict d1 ON t.type=d1.code and d1.cata_code="t_questions.type"
	LEFT JOIN t_dict d3 ON t.must=d3.code and d3.cata_code="t_questions.must"
	LEFT JOIN t_choose c ON t.test_id = c.id 
	where t.test_id=#{data.ctlId}
 ) a 