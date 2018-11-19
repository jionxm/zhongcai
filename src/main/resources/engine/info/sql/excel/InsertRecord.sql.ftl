INSERT INTO `t_statistics_record` 
(
`path`, 
`project_id`, 
`type`,
`create_by`,
`create_time`
)
 VALUES ( #{data.path}, #{data.projectId},  #{data.type}, #{data.createBy},
 now());