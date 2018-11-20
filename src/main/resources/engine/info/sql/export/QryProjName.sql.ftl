select * from (
	select 
		concat(p.year,'') as `year`,
		p.`name` as projectName,
		SUBSTR(SYSDATE() FROM 1 FOR 10)as reportDate,
		 g.id,
		t.type,
		(select o.`name` from t_org o where o.`code`=r.dept_code) as orgName,
		IFNULL((select sum(n.number) from t_tester_number n where n.pro_group_id=g.id),0) as peopleCount
from t_project p 
 left JOIN t_project_group g on g.project_id=p.id
LEFT JOIN t_test t on  t.id=g.test_id
 LEFT JOIN t_group r on r.id=g.group_id where p.id = #{data.projId } and g.id=#{data.groupId }
 ) a 