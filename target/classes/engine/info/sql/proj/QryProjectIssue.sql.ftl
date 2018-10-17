select * from (select * from (
 select * from(  select
                        case  when i.sprint_id is null or i.sprint_id = '' then CAST(99999998  as DECIMAL)  else CAST(@rownum:=@rownum+1  as DECIMAL) end rowNum,
                        case when isnull(#{data.projId}) or #{data.projId}='' then '$ALL' else i.proj_id end as projId,
                        case when isnull(#{data.projId}) or #{data.projId}='' then '' else  p.name end as projName,
                        DATE_FORMAT(s.start_time,'%Y-%m-%d') as startTime,
                        DATE_FORMAT(s.test_end_time,'%Y-%m-%d')  as testEndTime,
                        case  when i.sprint_id is null or i.sprint_id = '' then '待办工作项' else s.name end as sprintName,
                        case  when i.sprint_id is null or i.sprint_id = '' then '$NULL' else s.id end as sprintId,
                        case  when i.sprint_id is null or i.sprint_id = '' then '$NULL' else d.name end as sprintStatus,

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
                        sum(case when i.status in  ('open','reopen','workin' ,'resolve' ,'test' ,'close') then 1 else 0 end) as total,
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
            from (
               select @rownum:=0,i1.*
                    from
                    t_issue  i1     where case when isnull(#{data.projId}) or #{data.projId}=''  then 1=1   else  i1.proj_id = #{data.projId} end )i
            Left join t_sprint s  on s.id = i.sprint_id
            inner join t_project p on i.proj_id  =p.id
            left join t_dict d on s.status = d.code
            where cata_code = 't_sprint.status'
            GROUP BY s.start_time,i.sprint_id
            order by i.sprint_id desc
           ) a
union all
select
    CAST(99999999  as DECIMAL) as rowNum,
case when isnull(#{data.projId}) or #{data.projId}='' then '$ALL' else i.proj_id end as projId,
case when isnull(#{data.projId}) or #{data.projId}='' then '' else p.name end as projName,
 '' as startTime,
'' as testEndTime,
    '合计' as sprintName,
    '$ALL'  as sprintId,
   ''  as sprintStatus,

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
    sum(case when i.status in ('open','reopen' ,'workin','resolve','test','close') then 1 else 0 end) as total,
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
from (
   select @rownum:=0,i1.*
        from
        t_issue  i1     where case when isnull(#{data.projId}) or #{data.projId}=''  then 1=1   else  i1.proj_id = #{data.projId} end ) i
Left join t_sprint s  on s.id = i.sprint_id
inner join t_project  p on i.proj_id = p.id
left join t_dict d on s.status = d.code
where cata_code = 't_sprint.status'
)  a
where total is not null and total <> 0
)projectIssue