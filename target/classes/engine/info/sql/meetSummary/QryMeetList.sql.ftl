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
	dic.`name` as statusName,
	dic.`code` as status,
	meet.theme as theme,
 	CASE WHEN ISNULL(t1.issueCount) THEN '-'
	ELSE t1.issueCount END as issueCount,
 	CASE WHEN ISNULL(t2.fileCount) THEN '-'
	ELSE t2.fileCount END as fileCount
FROM
	t_meeting meet
LEFT JOIN (
	SELECT
		count(id) AS issueCount,
		meeting_id
	FROM
		t_meeting_detail
	GROUP BY
		meeting_id
) t1 ON t1.meeting_id = meet.id
LEFT JOIN (
	SELECT
		count(id) AS fileCount,
		meeting_id
	FROM
		t_meeting_file
	GROUP BY
		meeting_id
) t2 ON t2.meeting_id = meet.id
LEFT JOIN t_employee emp ON emp.id = meet.write_by
LEFT JOIN t_project pro ON pro.id=meet.project_id
LEFT JOIN t_dict dic ON dic.`code`=meet.`status`
GROUP BY meet.id
 ) a 