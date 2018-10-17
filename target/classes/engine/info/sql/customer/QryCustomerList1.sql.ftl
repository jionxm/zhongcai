select * from (
 select DISTINCT(b.id) , 
 		b.customerName ,
 		b.type ,b.typeName ,
 		b.marks ,b.createTime ,
 		b.createByName ,
 		b.updateTime ,
 		b.updateByName ,
 		b.status ,
 		b.contactNum,
 		b.clueNum,
 		b.statusName
 		
  from(
		  select t.id as id,
			t.name as customerName,
			t.type as type,
			d.name as typeName,
			t.marks as marks,
			t.create_time as createTime,
			e1.name as createByName,
			t.update_time as updateTime,
			e2.name as updateByName,
			t.status as status,
			
			(SELECT COUNT(c.id) FROM t_contacts c where  c.customer_id = t.id)as contactNum, 
  			(SELECT COUNT(sc.id) FROM t_sales_clues sc where sc.customer_id = t.id) as clueNum,
			
			c.tel as telp,
			case when (t.status=1)     then '禁用'
					 when (t.status=0)    then '启用'  end as statusName 
			from t_customer t 
			left join t_employee e1 ON e1.id = t.create_by
			left join t_employee e2 ON e2.id = t.update_by
			left join t_dict d on t.type=d.code and d.cata_code='t_customer.type' 
			left join t_contacts c on t.id=c.customer_id 
	) b
 ) a