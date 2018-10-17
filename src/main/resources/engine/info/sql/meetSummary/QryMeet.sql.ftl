select * from (
SELECT
	meet.id as id,
	meet.meeting_time as meetingTime,
	meet.originator as originator,
	meet.attendee as attendee,
	emp.`name` as writeByName,
	emp.`id` as writeBy,
	pro.name as projectName,
	pro.id as projectId,
	dic.`name` as status,
	meet.theme as theme,
	meet.content as content,
	meet.result as result,
	meet.place as place,
 	t1.issueCount,
	t2.fileCount
FROM
	t_meeting meet
LEFT JOIN (
	SELECT
		sum(id) AS issueCount,
		meeting_id
	FROM
		t_meeting_detail
	GROUP BY
		meeting_id
) t1 ON t1.meeting_id = meet.id
LEFT JOIN (
	SELECT
		sum(id) AS fileCount,
		meeting_id
	FROM
		t_meeting_file
	GROUP BY
		meeting_id
) t2 ON t1.meeting_id = meet.id
LEFT JOIN t_employee emp ON emp.id = meet.write_by
LEFT JOIN t_project pro ON pro.id=meet.project_id
LEFT JOIN t_dict dic ON dic.`code`=meet.`status`
GROUP BY meet.id
 ) a 