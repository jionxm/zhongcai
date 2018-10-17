select * from (select * from (
Select * from (

    select
    case when isnull(#{data.projId}) or #{data.projId}='' then '$ALL' else i.proj_id end as projId,
    case when isnull(#{data.projId}) or #{data.projId}='' then '' else p.name end  as projectName,
    case when isnull(#{data.sprintId}) or #{data.sprintId}=''  then '$ALL'  else i.sprint_id end as sprintId,
    e.id as empId,
    e.name as empName,
    o.name as orgName,
    e1.name as managerName,
    po.name as positionName,
    sum(case when i.status='open' then 1 else 0 end ) as open,
    sum(case when i.status='reopen' then 1 else 0 end ) as reopen,
    sum(case when i.status='workin' then 1 else 0 end ) as workin,
    sum(case when i.status='resolve' then 1 else 0 end ) as resolve,
    sum(case when i.status='test' then 1 else 0 end ) as test,
    sum(case when i.status='close' then 1 else 0 end ) as close,
    sum(case when i.status='cancel' then 1 else 0 end ) as cancel,
    sum(case when i.status in ('open','reopen','workin','resolve','test') then 1 else 0 end) as unComplete,
    sum(case when i.status in ('open','reopen') then 1 else 0 end) as unDoing,
    sum(case when i.status in ('open','reopen','workin') then 1 else 0 end) as unSolve,
    sum(case when i.status in ('open','reopen','workin','resolve','test','close') then 1 else 0 end) as total,
   CONCAT(convert(case when sum(case when i.status in('open','reopen','workin','resolve','test','close') then 1 else 0 end) = 0
    then
    0
    else
    sum(case when i.status in ('resolve','test','close') then 1 else 0 end ) /sum(case when i.status in ('open','reopen','workin','resolve','test','close') then 1 else 0 end) *100
    end ,decimal(10,2)),'%')
    as percent,
    CONCAT(convert(case when sum(case when i.status in( 'open' ,'reopen' ,'workin','resolve','test','close') then 1 else 0 end) = 0
    then
    0
    else
    sum(case when i.status in ('close') then 1 else 0 end ) /sum(case when i.status in ('open','reopen','workin','resolve','test','close') then 1 else 0 end) *100
    end ,decimal(10,2)),'%')
    as resolvePercent
    from (select * from
    t_issue i1
    where  i1.proj_id = #{data.projId}    and
       case when isnull(#{data.sprintId}) or #{data.sprintId}=''  then 1=1    else  i1.sprint_id = #{data.sprintId} end
    )  i
    left join t_employee e on i.assignee = e.id
    left join t_org o on e.org_id=o.id
    left join t_position po on e.position_id=po.id
    left join t_employee e1 on  o.manager_id = e1.id
    left join t_project p on p.id = i.proj_id
    GROUP BY e.id
    order by o.pid,empName
) a

union all
select
    case when isnull(#{data.projId}) or #{data.projId}='' then '$ALL' else i.proj_id end as projId,
    case when isnull(#{data.projId}) or #{data.projId}='' then '' else p.name end  as projectName,
    case when isnull(#{data.sprintId}) or #{data.sprintId}=''  then '$ALL'  else i.sprint_id end as sprintId,
    '$ALL' as empId,
    '合计' as empName,
    '' as orgName,
    '' as managerName,
    '' as positionName,
    sum(case when i.status='open' then 1 else 0 end ) as openStatus,
    sum(case when i.status='reopen' then 1 else 0 end ) as reopen,
    sum(case when i.status='workin' then 1 else 0 end ) as workIn,
    sum(case when i.status='resolve' then 1 else 0 end ) as resolve,
    sum(case when i.status='test' then 1 else 0 end ) as test,
    sum(case when i.status='close' then 1 else 0 end ) as closeStatus,
    sum(case when i.status='cancel' then 1 else 0 end ) as cancel,
  sum(case when i.status in ('open','reopen','workin','resolve','test') then 1 else 0 end) as unComplete,
    sum(case when i.status in ('open','reopen') then 1 else 0 end) as unDoing,
    sum(case when i.status in ('open','reopen','workin') then 1 else 0 end) as unSolve,
    sum(case when i.status in ('open','reopen','workin','resolve','test','close') then 1 else 0 end) as total,
   CONCAT(convert(case when sum(case when i.status in('open','reopen','workin','resolve','test','close') then 1 else 0 end) = 0
    then
    0
    else
    sum(case when i.status in ('resolve','test','close') then 1 else 0 end ) /sum(case when i.status in ('open','reopen','workin','resolve','test','close') then 1 else 0 end) *100
    end ,decimal(10,2)),'%')
    as percent,
    CONCAT(convert(case when sum(case when i.status in( 'open' ,'reopen' ,'workin','resolve','test','close') then 1 else 0 end) = 0
    then
    0
    else
    sum(case when i.status in ('close') then 1 else 0 end ) /sum(case when i.status in ('open','reopen','workin','resolve','test','close') then 1 else 0 end) *100
    end ,decimal(10,2)),'%')
    as resolvePercent
    from
    t_issue  i
    left join t_project  p on i.proj_id = p.id
    where   i.proj_id = #{data.projId} and
       		 case when isnull(#{data.sprintId}) or #{data.sprintId}=''  then  1=1   else  i.sprint_id = #{data.sprintId} end
)  a
where total is not null and total <> 0
) devOverView