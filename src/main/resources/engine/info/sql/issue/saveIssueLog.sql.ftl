insert into t_issue_log (issue_id,type,log_content,update_status,create_time,create_by,update_time,update_by)    
values (
	#{data.id},#{data.type},#{data.logContent},#{data.updateStatus},
	#{data.createTime},#{data.createBy},#{data.updateTime},#{data.updateBy}
);