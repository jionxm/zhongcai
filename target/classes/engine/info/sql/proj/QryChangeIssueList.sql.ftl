select * from (
	    select
    ci.id,
    ci.issue_id as issueId,
    ci.change_id as changeId,
    i.title as issueName,
    i.proj_id as projId,
    ci.update_by as updateBy,
    e.name as updateByName,
    e2.name as assignee,
    e3.name as reporter,
    ci.create_by as createBy,
    e1.name as createByName,
    ci.update_time as updateTime,
    ci.create_time as createTime
    from t_change_apply_issue ci
    left join t_issue i on ci.issue_id = i .id
    left join t_employee  e on ci.update_by = e.id
    left join t_employee e1 on ci.create_by = e1.id
    left join t_employee e2 on i.assignee = e2.id
    left join t_employee e3 on i.reporter = e3.id
 ) a