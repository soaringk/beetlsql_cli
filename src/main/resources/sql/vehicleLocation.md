getBatchState
===
* 按页查询，仅查询 state

    SELECT `id`, `state`
    FROM `vehicle_location`
    WHERE `state` <> 0 AND `state_info` IS NULL LIMIT #limit#
    FOR UPDATE

getBatchAlarm
===
* 按页查询，仅查询 alarm

    SELECT `id`, `alarm`
    FROM `vehicle_location`
    WHERE `alarm` <> 0 AND `alarm_info` IS NULL LIMIT #limit#
    FOR UPDATE


updateBatchInfo
===
* 直接更新

	UPDATE `vehicle_location`
	SET #use("infoCols")#
	where `id`=#id#

infoCols
===
* 指定需要更新的列。 trim 函数剔除结尾的 ',' 符号，避免语法错误

    @trim(){
        @if(!isEmpty(stateInfo)){
            `state_info`=#stateInfo#,
        @} if(!isEmpty(alarmInfo)){
            `alarm_info`=#alarmInfo#,
        @}
    @}


insertUpdateState
===
* SQL 技巧（比直接 update 速度快）。NOTE: Mysql 5.7 下并发执行该类 SQL 会造成死锁

	INSERT INTO `vehicle_location`
	(`id`, `state_info`) VALUES
    @trim(){
        @for(vl in vls){
            (#vl.id#, #vl.stateInfo#),
        @}
    @}	
	ON DUPLICATE KEY UPDATE `state_info`=values(`state_info`)


insertUpdateAlarm
===
	INSERT INTO `vehicle_location`
	(`id`, `alarm_info`) VALUES
    @trim(){
        @for(vl in vls){
            (#vl.id#, #vl.alarmInfo#),
        @}
    @}	
	ON DUPLICATE KEY UPDATE `alarm_info`=values(`alarm_info`)


countRemain
===
* 返回未完全设置 info 的数据总数

    (SELECT `alarm`
     FROM `vehicle_location`
     WHERE `alarm` <> 0 AND `alarm_info` IS NULL
    )
    UNION
    (SELECT `state`
     FROM `vehicle_location`
     WHERE `state` <> 0 AND `state_info` IS NULL
    )


find
===

    SELECT #use("cols")# 
    FROM `vehicle_location`
    WHERE #use("condition")#

cols
===

    @trim(){
	    `id`,
	    `state`,
	    `alarm`,
	    `state_info`,
	    `alarm_info`,
    @}


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
