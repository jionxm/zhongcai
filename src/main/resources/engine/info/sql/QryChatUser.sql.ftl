select * from (
	select u.name as userName,
	e.name as name
	from
	t_user u
	LEFT JOIN t_employee e
	on u.emp_id=e.id
 ) a 