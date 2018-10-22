select * from (
	select 
	id as id,
	title as title 
	from t_test
	where state="start" or state="teststart"
 ) a 