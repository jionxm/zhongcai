Select * from (
	select p.name as projName,
  		   p.id as projId,
  		   p.id as id
	  from t_project p
) a