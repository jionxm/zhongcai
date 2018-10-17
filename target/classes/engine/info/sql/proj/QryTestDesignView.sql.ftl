select * from (select * from   (
select * from  (
    select
    case  when i.test_design_by is null or i.test_design_by ='' then CAST(99999998  as DECIMAL) else CAST(@rownum:=@rownum+1  as DECIMAL) end as rowNum,
    case when isnull(#{data.projId}) or #{data.projId}=''  then '$ALL'  else i.proj_id end as projId,
    case when isnull(#{data.projId}) or #{data.projId}=''  then ''   else p.name end as projectName,
    case when isnull(#{data.sprintId}) or #{data.sprintId}=''  then '$ALL'  else i.sprint_id end as sprintId,
    case  when i.test_design_by is null or i.test_design_by ='' then '未指派测试设计人' else e.name end as  empName,
    i.test_design_by  as designBy,
   o.name as orgName,
   e1.name as managerName,
    'eq_testDesignBy' as item,
    po.name as positionName,
    case  when i.test_design_by is null or i.test_design_by ='' then '$NULL' else i.test_design_by end as itemValue,
    count(i.id) as issueName,
    sum(case when c.y>0 then 1 else 0 end) as hasTestCase,
    sum(case when c.n>0 then 1 else 0 end) as noTestCase,
    sum(c.y) as allTestCase,
    case when sum(case when c.y>0 then 1 else 0 end)=0 then convert(0,decimal(10,2))
    else
    convert(sum(c.y)/sum(case when c.y>0 then 1 else 0 end),decimal(10,2)) end as avgTestCase
    from (select @rownum:=0,i1.* from  t_issue i1
    where i1.type in ('feature','improvement')
    and   i1.proj_id = #{data.projId}  and
    case when isnull(#{data.sprintId}) or #{data.sprintId}=''  then 1=1    else  i1.sprint_id = #{data.sprintId} end
) i
   inner join (
        select   i2.id ,sum(case when  t.id is not null or t.id <> '' then 1 else 0 end)  as y ,
        sum(case when  t.id is null or t.id = '' then 1 else 0 end)  as n
        from t_issue  i2   Left join t_testcase t on i2.id = t.issue_id
		WHERE i2.`status`<>'cancel'
        group by i2.id ) c on i.id= c.id
    left join t_project p on i.proj_id = p.id
    left join t_employee e on i.test_design_by= e.id
    left join t_position po on e.position_id=po.id
    left join t_org o on e.org_id =o.id
    left join t_employee e1 on  o.manager_id = e1.id
    GROUP BY i.test_design_by
    order by orgName,empName
)aa
union all
        select
        CAST(99999999  as DECIMAL) as rowNum,
case when isnull(#{data.projId}) or #{data.projId}=''  then '$ALL'  else i.proj_id end as projId,
case when isnull(#{data.projId}) or #{data.projId}=''  then ''   else p.name end as projectName,
case when isnull(#{data.sprintId}) or #{data.sprintId}=''  then '$ALL'  else i.sprint_id end as sprintId,
'合计' as  empName,
   '' as orgName,
  '' as managerName,
        ''  as designBy,
        'eq_testDesignBy' as item,
        '' as positionName,
        '$ALL' as itemValue,
        count(i.id) as issueName,
        sum(case when c.y>0 then 1 else 0 end) as hasTestCase,
        sum(case when c.n>0 then 1 else 0 end) as noTestCase,
        sum(c.y) as allTestCase,
        case when sum(case when c.y>0 then 1 else 0 end)=0 then convert(0,decimal(10,2))
        else
        convert(sum(c.y)/sum(case when c.y>0 then 1 else 0 end),decimal(10,2)) end as avgTestCase
        from (select @rownum:=0,i1.* from  t_issue i1
        where i1.type in ('feature','improvement')
        and  i1.proj_id = #{data.projId}  and
        case when isnull(#{data.sprintId}) or #{data.sprintId}=''  then 1=1    else  i1.sprint_id = #{data.sprintId} end
        ) i
        inner join (
        select   i2.id ,sum(case when  t.id is not null or t.id <> '' then 1 else 0 end)  as y ,
        sum(case when  t.id is null or t.id = '' then 1 else 0 end)  as n
        from t_issue  i2   Left join t_testcase t on i2.id = t.issue_id
        WHERE i2.`status`<>'cancel'
        group by i2.id ) c on i.id= c.id
        left join t_project p on i.proj_id = p.id
) a
where hasTestCase is not null
) b