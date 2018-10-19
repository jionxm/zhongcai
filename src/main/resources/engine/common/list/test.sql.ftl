select * from (
	select 
	id as value,
	title as text from t_test
	where state="start" or state="teststart"
 ) a 