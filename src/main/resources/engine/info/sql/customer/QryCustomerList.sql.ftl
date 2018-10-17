select * from (
 select DISTINCT(b.id) , 
 		b.customerName ,
 		b.shortName,
 		b.type ,b.typeName ,
 		b.marks ,b.createTime ,
 		b.createByName ,
 		b.updateTime ,
 		b.updateByName ,
 		b.status ,
 		b.contactNum,
 		b.clueNum,
 		b.statusName,
 		b.area,
		b.scaleName,b.scale,
		b.presalesName,b.presales,
		b.sellerName,b.seller,
		b.natureName,b.nature,
		b.coopStatus
 		
  from(
		  select t.id as id,
			t.name as customerName,
			t.short_name as shortName,
			t.type as type,
			d.name as typeName,
			t.marks as marks,
			t.create_time as createTime,
			e1.name as createByName,
			t.update_time as updateTime,
			e2.name as updateByName,
			e3.name as presalesName,
			e4.name as sellerName,
			t.status as status,
			t.area as area,
			t.scale as scale,
			t.presales as presales,
			t.seller as seller,
			t.nature as nature,
			t.coop_status as coopStatus,
			case when (t.nature=1)     then '私企'
				 when (t.nature=0)    then '国企'  end as natureName,
			case when (t.scale=2)     then '大'
				 when (t.scale=1)     then '中'
				 when (t.scale=0)    then '小'  end as scaleName,
			(SELECT COUNT(c.id) FROM t_contacts c where  c.customer_id = t.id and c.status=0)as contactNum, 
  			(SELECT COUNT(sc.id) FROM t_sales_clues sc where sc.customer_id = t.id and sc.status<>'notbuilding') as clueNum,
			
			case when (t.status=1)     then '禁用'
					 when (t.status=0)    then '启用'  end as statusName 
			from t_customer t 
			left join t_employee e1 ON e1.id = t.create_by
			left join t_employee e2 ON e2.id = t.update_by
			left join t_employee e3 ON e3.id = t.presales
			left join t_employee e4 ON e4.id = t.seller
			left join t_dict d on t.type=d.code and d.cata_code='t_customer.type' 
	) b
 ) a