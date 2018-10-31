select * from (
	select 
	id as id,
	name as name
	from t_project
	where state='start' and qrstate='1'
 ) a 