select * from (
	select 
	p.id as id,
	p.group_id as groupId,
	g.name as groupName,	
	p.test_id as testId,
	t.title as testName,
	p.state as state,
    tp.qrstate as qrState,
    tp.id as projectId,
    (select count(1) from T_TESTER_NUMBER tn where tn.pro_group_id=p.id)as testerCount
	from t_project_group p
	LEFT JOIN t_group g ON g.id=p.group_id
	LEFT JOIN t_test t ON t.id=p.test_id
    LEFT JOIN t_project tp on tp.id=p.project_id
	where p.project_id = #{data.ctlId}
 ) a 