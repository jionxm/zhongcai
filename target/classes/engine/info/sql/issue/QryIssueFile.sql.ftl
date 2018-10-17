select * from (
	select 
	f.id as id,
	i.filename as fileName,
	f.issue_id as issueId,
	f.file_index_id as fileIndexId,
	f.create_time as createTime,
	e.name as createByName
	from t_issue_file f
	left join t_file_index i on i.id = f.file_index_id
	left join t_employee e on e.id = f.create_by
	
 ) a 
 