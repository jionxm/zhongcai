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
 VALUES ( #{data.projectId}, #{data.QRCode},  #{data.proGroupId},"0", #{data.testerId},
 now(),#{data.empId},now(),#{data.empId});