select * from (
SELECT mf.id as id, mf.meeting_id as meetId, fi.fileName as fileName,mf.file_id as fileId,
mf.remark as remark from t_meeting_file mf
LEFT JOIN t_file_index fi ON fi.id=mf.file_id
 ) a 