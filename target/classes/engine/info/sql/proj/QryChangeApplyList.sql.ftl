select * from (
    select
    c.id,
    c.proj_id as projId,
    p.name as projectName,
    c.applicant,
     DATE_FORMAT(c.apply_date,'%Y-%m-%d') as applyDate,
    DATE_FORMAT(c.complete_date,'%Y-%m-%d') as completeDate,
    c.title,
    case when c.audit_result is null then '' else  case when c.audit_result ='y' then '同意' else  '不同意' end       end as auditResult,
    c.change_reason as changeReason,
    c.change_content as changeContent,
    c.change_plan as changePlan,
    c.update_by as updateBy,
    e.name as updateByName,
    c.create_by as createBy,
    e1.name as createByName,
    c.update_time as updateTime,
    c.create_time as createTime,
    f.fileCount,
    i.issueCount
    from t_change_apply c
    inner join t_project p on c.proj_id = p.id
    left join t_employee  e on c.update_by = e.id
    left join t_employee e1 on c.create_by = e1.id
    left join (
           SELECT count(change_id) AS fileCount, change_id FROM t_change_apply_file GROUP BY change_id) f
            on f.change_id = c.id
    left join (
           SELECT count(change_id) AS issueCount, change_id FROM t_change_apply_issue GROUP BY change_id) i
            on i.change_id = c.id

) a