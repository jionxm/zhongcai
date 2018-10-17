select * from (select * from(
select i.id as issueId,
i.create_time as createTime,
case when isnull(#{data.projId}) or #{data.projId}=''  then ''  else i.proj_id end as projId,
case when isnull(#{data.sprintId}) or #{data.sprintId}='' then '' else i.sprint_id end as sprintId,
    i.title as issueName ,
    sum( case when t.last_result = 'pass' then 1 else 0 end ) as pass,
    sum( case when t.last_result = 'fail' then 1 else 0 end ) as fail,
    sum( case when t.last_result = 'invalid' then 1 else 0 end ) as invalid,
    sum( case when t.last_result = 'blocker' then 1 else 0 end ) as blocker,
    sum( case when t.id is null then 0 else case when t.last_result = '' or t.last_result is null then 1 else 0 end end) as notRun,
    sum( case when t.id is null then 0 else case when t.last_result = '' or t.last_result is null or t.last_result in( 'pass','fail','invalid','blocker') then 1 else 0 end end) as total
    from (select i1.*
            from
            t_issue i1
            where i1.type in ('feature','improvement')
            and   i1.proj_id = #{data.projId}
            and  case when isnull(#{data.sprintId}) or #{data.sprintId}='' then 1=1   else  i1.sprint_id = #{data.sprintId} end ) i
    Left join t_testcase t
on i.id =t.issue_id
GROUP BY i.title,i.id
union all
    select
'' as issueId,
'' as createTime,
case when isnull(#{data.projId}) or #{data.projId}=''  then ''  else i.proj_id end as projId,
case when isnull(#{data.sprintId}) or #{data.sprintId}='' then '' else i.sprint_id end as sprintId,
    '合计' as issueName,
    sum( case when t.last_result = 'pass' then 1 else 0 end ) as pass,
    sum( case when t.last_result = 'fail' then 1 else 0 end ) as fail,
    sum( case when t.last_result = 'invalid' then 1 else 0 end ) as invalid,
    sum( case when t.last_result = 'blocker' then 1 else 0 end ) as blocker,
    sum( case when t.id is null then 0 else case when t.last_result = '' or t.last_result is null then 1 else 0 end end) as notRun,
    sum( case when t.id is null then 0 else case when t.last_result = '' or t.last_result is null or t.last_result in( 'pass' , 'fail' , 'invalid','blocker') then 1 else 0 end end) as total
    from (select i1.*
    from
    t_issue i1
   where i1.type in ('feature','improvement')
  and   i1.proj_id = #{data.projId}
   and  case when isnull(#{data.sprintId}) or #{data.sprintId}='' then 1=1   else  i1.sprint_id = #{data.sprintId} end ) i
  Left join t_testcase t on i.id =t.issue_id
) testR
where total is not null and total <> 0
) testReport
