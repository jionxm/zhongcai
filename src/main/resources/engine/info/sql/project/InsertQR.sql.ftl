INSERT INTO `t_qr` 
(
`project_id`, 
`QR_code`, 
`proj_group_id`,
`state`,
`tester_id`,
 `create_time`, 
 `create_by`, 
 `update_time`,
 `update_by`  )
 VALUES ( #{data.projectId}, #{data.QRCode},  #{data.proGroupId},"1", #{data.testerId},
 now(),#{data.empId},now(),#{data.empId});