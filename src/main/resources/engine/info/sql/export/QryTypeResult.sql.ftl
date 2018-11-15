select * from (
	select
	g.name as groupName,
	count(e.emp_id) as number,
	CONCAT(round(count(e.emp_id) / n.allnumber *100,2),'%')as percent
	from t_project_group p
	left join t_group g on g.id = p.group_id
	left join t_group_emp e on e.group_id = p.group_id
	left join (
		select 
		p.project_id as projectid,
		count(e.emp_id) as allnumber
		from t_project_group p
		left join t_group_emp e on e.group_id = p.group_id
		where p.project_id = #{data.projId }
		) n on n.projectid = p.project_id
	where p.project_id = #{data.projId}
 ) a 