select * from (select * from (
Select * from (
    select
    eo.id as empId,
    eo.name as empName,
    eo.orgName as orgName,
    eo.managerName as managerName,
    eo.positionName as positionName,
    i.create_time as createTime,
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
    from
    (select e.id,e.name,o.name as orgName, e1.name as managerName,po.name as positionName from t_employee e
    left join t_org o on e.org_id =o.id
    left join t_employee e1 on  o.manager_id = e1.id
    left join t_position po on e.position_id=po.id
    where case when   isnull(#{data.orgId}) or #{data.orgId}=''  then 1=1 else  o.id =#{data.orgId} end 
    ) eo 
    left join  t_issue i on i.assignee = eo.id
    where case when   isnull(#{data.createTime}) or #{data.createTime}=''  then 1=1 else  i.create_time >= #{data.createTime} end 
    
    GROUP BY eo.id
    order by orgName,unComplete desc,empName
) a

union all
select
    '$ALL' as empId,
    '合计' as empName,
    '' as orgName,
    '' as managerName,
    '' as positionName,
     i.create_time as createTime,
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
    (select e.id from t_employee e
    left join t_org o on e.org_id =o.id
    where case when  isnull(#{data.orgId}) or #{data.orgId}=''  then 1=1 else o.id =#{data.orgId} end 
    ) eo 
    left join   t_issue  i on i.assignee = eo.id
    where case when   isnull(#{data.createTime}) or #{data.createTime}=''  then 1=1 else  i.create_time > #{data.createTime} end 
)  a
where total is not null and total <> 0
) devOverView