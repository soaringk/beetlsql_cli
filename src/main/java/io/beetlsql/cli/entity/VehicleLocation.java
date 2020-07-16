package io.beetlsql.cli.entity;

import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.beetl.sql.core.annotatoin.Table;


/*
 *
 * gen by beetlsql 2020-08-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "gps_traffic.vehicle_location")
public class VehicleLocation {

    // alias
    public static final String ALIAS_id = "id";
    public static final String ALIAS_alarm = "alarm";
    public static final String ALIAS_state = "state";
    public static final String ALIAS_alarm_info = "alarm_info";
    public static final String ALIAS_state_info = "state_info";

    private Long id;
    private Integer alarm;
    private Integer state;
    private String alarmInfo;
    private String stateInfo;

    /**
     * id
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getAlarm() {
        return alarm;
    }

    /**
     *
     * @param alarm
     */
    public void setAlarm(Integer alarm) {
        this.alarm = alarm;
    }

    /**
     *
     * @return
     */
    public Integer getState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     *
     * @return
     */
    public String getAlarmInfo() {
        return alarmInfo;
    }

    /**
     *
     * @param alarmInfo
     */
    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    /**
     *
     * @return
     */
    public String getStateInfo() {
        return stateInfo;
    }

    /**
     *
     * @param stateInfo
     */
    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}
