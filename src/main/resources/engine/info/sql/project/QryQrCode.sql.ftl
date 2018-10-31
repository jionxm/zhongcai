select * from (
	SELECT
		qr.project_id as projectId,
		qr.QR_code as QRCode,
		qr.id as QRId,
		qr.tester_id as ztqzId,
		qr.proj_group_id as projectGroupId,
		g.test_id as testId,
		t.type as type,
		t.title as title
	from t_project_group g
		LEFT JOIN t_qr qr  ON g.id= qr.proj_group_id
		LEFT JOIN t_test t  ON t.id= g.test_id
		where qr.QR_code=#{data.id }
 ) a 