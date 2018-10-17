select * from (
	select 
	c.c_id as sha1,
	c.message as message,
	c.name as authorName,
	c.timestamp as date,
	c.issue_id as issueId
	from t_commit c
	
 ) a  