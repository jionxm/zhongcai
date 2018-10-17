select * from (
		SELECT
		i.id ,
		i.proj_id as projId,
		i.sprint_id as sprintId,
		'bug' as type,
		'open' as status
		FROM
			t_issue i 

 ) a 