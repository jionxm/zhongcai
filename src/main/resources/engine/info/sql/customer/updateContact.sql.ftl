update t_contacts set name=#{data.name} where customer_id=#{data.customerId} and tel=#{data.tel}
