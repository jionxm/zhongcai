INSERT INTO `t_test` 
(
`title`, 
`type`, 
`group_id`,
`version`,
`message`,
`state`,
 `create_time`, 
 `create_by`, 
 `update_time`,
 `update_by`  )
 VALUES ( #{data.ctlTitle}, #{data.ctlType},  #{data.ctlGroupId}, #{data.ctlVersion}+1.0, #{data.ctlMessage}, "stop", 
 #{data.createTime}, #{data.createBy}, #{data.updateTime},#{data.updateBy});