select * from (
	
	select c_id as sha1,
	issue_id as issueId,
	message as message,
	timestamp as date,
	name as authorName
	from t_commit 
	where timestamp >
	(select DISTINCT c2.timestamp as date2 from t_commit  c2
	where c2.c_id=(select after from t_push where id=
	(select MAX(p2.id)as maxAfterId	from t_push p1
	LEFT JOIN t_push p2
	on p1.p_before=p2.after
	where p1.p_before=#{data.sha1} and p2.repository LIKE '%url=git@gitcloud.tsoftware.cn:root/sooshong_srv.git%')))
	AND
	timestamp<
	(select DISTINCT c1.timestamp as date1
	from t_commit c1 
	where c1.c_id=
	(select after from t_push 
	where id=
	(select MAX(p1.id)as maxBeforeId
	from t_push p1
	LEFT JOIN t_push p2
	on p1.p_before=p2.after
	where p1.p_before=#{data.sha1} and p1.repository LIKE '%url=git@gitcloud.tsoftware.cn:root/sooshong_srv.git%')))
) a 