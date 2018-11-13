INSERT INTO `t_participants` 
(
`recommend_id`, 
`group_id`, 
`number`
  )
 VALUES ( #{data.recommendId}, #{data.groupId},  #{data.number});