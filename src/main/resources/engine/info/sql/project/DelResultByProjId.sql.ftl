delete from t_result where QR_id in (select id from t_qr where project_id=#{data.id })