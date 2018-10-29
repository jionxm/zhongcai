select 
	t.id,
    t.project_id
 from t_qr t where t.project_id = #{data.project_Id}