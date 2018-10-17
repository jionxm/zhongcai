select * from (
	select 
	cata.id as id,
	cata.name as cataName,
    doc.title as title,
    cata.pid as pid,
    doc.keyword as keyword,
    doc.status as status,
    doc.create_time as createTime,
    doc.update_time as updateTime
    from t_doc_cata cata LEFT JOIN t_document doc 
    on cata.id = doc.cata_id 
) a 


