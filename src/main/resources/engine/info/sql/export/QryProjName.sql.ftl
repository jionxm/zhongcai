select * from (
	select 
		concat(p.year,'') as `year`,
		p.`name` as projectName,
		SUBSTR(SYSDATE() FROM 1 FOR 10)as reportDate,
		 g.id,
		(select o.`name` from t_org o where o.`code`=r.dept_code) as orgName,
		(select sum(n.number) from t_tester_number n where n.pro_group_id=g.id) as peopleCount
from t_project p 
 left JOIN t_project_group g on g.project_id=p.id
 LEFT JOIN t_group r on r.id=g.group_id where p.id = #{data.projId } and g.id=#{data.groupId }
 ) a 