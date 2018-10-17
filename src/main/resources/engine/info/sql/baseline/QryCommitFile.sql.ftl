select * from (
	select 
	c.id as id,
	c.c_id as Cid,
	c.message as message,
	c.url as url,
	c.timestamp as timestamp,
	c.name as name,
	c.email as email,
	c.push_sha as pushSha,
	c.issue_id as issueId
	from t_commit c
 ) a 