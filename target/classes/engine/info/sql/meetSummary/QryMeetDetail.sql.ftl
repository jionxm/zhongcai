select * from (
SELECT md.id as id,md.meeting_id as meetId,issue.title as issueName, 
issue.id as issueId,
e1.`name` as reporter,
e2.`name` as assignee,
md.create_time as createTime
from t_meeting_detail md
LEFT JOIN t_issue issue ON issue.id=md.issue_id
LEFT JOIN t_employee e1 ON e1.id=issue.reporter
LEFT JOIN t_employee e2 ON e2.id=issue.assignee
 ) a 