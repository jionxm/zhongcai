INSERT INTO t_file_index
(
`uuid`, 
`filename`, 
`file_type`,
`length`,
`storage_type`,
 `access_type`, 
 `source`, 
 `path`,
 `create_by` ,
`create_time`
 )
 VALUES ( 
#{data.uuid}, #{data.filename},  #{data.file_type},#{data.length}, #{data.storage_type},
#{data.access_type},#{data.source},#{data.path},#{data.create_by},#{data.create_time}
);