find
===

    SELECT #use("cols")# 
    FROM `vehicle_location`
    WHERE #use("condition")#

cols
===
* 查询限定的 column

    @trim(){
	    `id`,
	    `state`,
	    `alarm`,
        `state_info`,
        `alarm_info`,
    @}

pageInfo
===
* 按页查询

    SELECT
    @pageTag(){
     #use("cols")#
    @} 
    FROM `vehicle_location`
    WHERE #use("remainCond")#

remainCond
===
* 如果 alarm 不为 0 且 alarm_info 为空（未更新），或者 state 不为 0 且 state_info 为空（未更新），为真。

    (`alarm` <> 0 AND `alarm_info` IS NULL)
    OR (`state` <> 0 AND `state_info` IS NULL)

updateBatchInfo
===
* 
	
	UPDATE `vehicle_location`
	SET #use("infoCols")#
	where `id`=#id#

infoCols
===
* 需要更新的列。 trim 函数剔除结尾的 ',' 符号，避免语法错误

    @trim(){
        `state_info`=#stateInfo#,
        `alarm_info`=#alarmInfo#,
    @}

countRemain
===
* 返回未完全设置 info 的数据总数

    SELECT COUNT(1)
    FROM `vehicle_location`
    WHERE #use("remainCond")#

condition
===

	1 = 1  
	@if(!isEmpty(id)){
	 and id=#id#
	@}
	@if(!isEmpty(encrypt)){
	 and encrypt=#encrypt#
	@}
	@if(!isEmpty(date)){
	 and date=#date#
	@}
	@if(!isEmpty(time)){
	 and time=#time#
	@}
	@if(!isEmpty(lon)){
	 and lon=#lon#
	@}
	@if(!isEmpty(lat)){
	 and lat=#lat#
	@}
	@if(!isEmpty(vec1)){
	 and vec1=#vec1#
	@}
	@if(!isEmpty(vec2)){
	 and vec2=#vec2#
	@}
	@if(!isEmpty(vec3)){
	 and vec3=#vec3#
	@}
	@if(!isEmpty(direction)){
	 and direction=#direction#
	@}
	@if(!isEmpty(altitude)){
	 and altitude=#altitude#
	@}
	@if(!isEmpty(state)){
	 and state=#state#
	@}
	@if(!isEmpty(alarm)){
	 and alarm=#alarm#
	@}
	@if(!isEmpty(deviceid)){
	 and deviceId=#deviceid#
	@}
	@if(!isEmpty(vehiclecolor)){
	 and vehicleColor=#vehiclecolor#
	@}
	@if(!isEmpty(stateInfo)){
	 and state_info=#stateInfo#
	@}
	@if(!isEmpty(alarmInfo)){
	 and alarm_info=#alarmInfo#
	@}
