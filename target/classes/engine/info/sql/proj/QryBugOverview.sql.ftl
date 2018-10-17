select * from ( select * from ( select* from(
    select CAST(@rownum:=@rownum+1  as DECIMAL) as rowNum,
case when isnull(#{data.projId}) or #{data.projId}='' then '' else i.proj_id end as projId,
case when isnull(#{data.projId}) or #{data.projId}='' then '' else p.name end  as projectName,
case when isnull(#{data.sprintId}) or #{data.sprintId}=''  then '$ALL'  else i.sprint_id end as sprintId,
    e.name as testByName,
    o.name as orgName,
    e1.name as managerName,
    i.reporter as testBy,
    'reporterName' as item,
     e.name as itemValue,
     po.name as positionName,
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
    from (select @rownum:=0,
    i1.*
    from
    t_issue  i1 where i1.type ='bug'
and  i1.proj_id = #{data.projId}  and
case when isnull(#{data.sprintId}) or #{data.sprintId}=''  then 1=1    else  i1.sprint_id = #{data.sprintId} end) i
     left join t_project p on i.proj_id = p.id
    left join t_employee e on i.reporter = e.id
     left join t_position po on e.position_id=po.id
    left join t_org o on e.org_id =o.id
    left join t_employee e1 on  o.manager_id = e1.id
    GROUP BY i.reporter
    order by orgName,unComplete desc,testByName
)aa
union  all
    select
    CAST(9999999  as DECIMAL) as rowNum,
case when isnull(#{data.projId}) or #{data.projId}='' then '' else i.proj_id end as projId,
case when isnull(#{data.projId}) or #{data.projId}='' then '' else p.name end  as projectName,
case when isnull(#{data.sprintId}) or #{data.sprintId}=''  then '$ALL'  else i.sprint_id end as sprintId,
     '合计' as testByName,
	 '' as orgName,
	 '' as managerName,
	 '$ALL' as testBy,
	 'eq_reporter' as item,
	 '$ALL' as itemValue,
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
    sum(case when i.status in ('open','reopen','workin','resolve' ,'test' ,'close') then 1 else 0 end) as total,
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
    (select i1.*
    from
    t_issue  i1  left join t_employee e on i1.reporter = e.id where i1.type ='bug'
    and  i1.proj_id = #{data.projId}  and
    case when isnull(#{data.sprintId}) or #{data.sprintId}=''  then 1=1    else  i1.sprint_id = #{data.sprintId} end) i
    left join  t_project p on i.proj_id = p.id

) ab
where total is not null and total <> 0
)abc