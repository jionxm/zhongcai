select 
	t.id,
	t.qr_code as qrCode,
    t.project_id
 from t_qr t where t.project_id = #{data.project_Id}