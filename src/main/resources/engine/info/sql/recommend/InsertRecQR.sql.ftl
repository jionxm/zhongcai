INSERT INTO `t_recommend_qr` 
(
`recommend_id`, 
`qr_code`, 
`participants_id`,
`state`,
 `create_time`, 
 `create_by`, 
 `update_time`,
 `update_by`  )
 VALUES ( #{data.recId}, #{data.qrCode},  #{data.partId},"1", 
 now(),#{data.empId},now(),#{data.empId});