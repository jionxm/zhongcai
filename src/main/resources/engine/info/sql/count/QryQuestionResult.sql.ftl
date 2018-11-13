SET @sql = NULL;
SELECT
  GROUP_CONCAT(DISTINCT
    CONCAT(
      'MAX(CASE q.question when ''',
      q.question ,
      ''' THEN t.result ELSE 0 END)  ''',
      q.question, ''''
    )
  ) INTO @sql
from t_project_group p
left join t_questions q on q.test_id = p.test_id
where p.project_id = #{data.projId };

SET @sql = CONCAT('SELECT 
	d.name,',@sql,
' FROM t_questions q
LEFT JOIN t_result t on t.question_id = q.id
LEFT JOIN t_qr qr on qr.id = t.QR_id
LEFT JOIN t_tester tr on tr.id = qr.tester_id
LEFT JOIN t_dict d on d.`code` = tr.name and d.cata_code="t_employee.identity"
WHERE t.QR_id in (SELECT id FROM t_qr WHERE project_id = ',#{data.projId },')
GROUP BY d.name');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;