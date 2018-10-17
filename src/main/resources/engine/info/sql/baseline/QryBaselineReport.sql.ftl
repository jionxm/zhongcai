select * from (

	SELECT
		c.sha1 AS sha1,
		c.issue_id AS issueId,
		c.message AS message,
		c.insert_time AS insertTime,
		c.author AS author,
		e.NAME AS authorName,
		c.date AS date,
		c.branch AS branch,
		c.job_id AS jobId
	FROM
		t_commit_log c
	LEFT JOIN t_user u on c.author = u.name
  	LEFT JOIN t_employee e on e.id = u.emp_id
		
	WHERE
		c.date
	 	>
		(SELECT
			date
		FROM
			t_commit_log
		WHERE
			sha1 = (select sha1 from t_baseline where id = (select MAX(id) from t_baseline where id < (select id from t_baseline where sha1 = #{data.sha1}) and type = 'public')))
	AND
		c.date
		<=
		(SELECT
			date
		FROM
			t_commit_log
		WHERE
			sha1 = #{data.sha1})

 ) a 