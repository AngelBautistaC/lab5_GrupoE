package com.example.laboratorio5_grupoe.entity;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Embeddable
public class JobHistoryId implements Serializable {
    private static final long serialVersionUID = -3803247907429881923L;
    @NotNull
    @Column(name = "job_history_id", nullable = false)
    private Integer jobHistoryId;

    @NotNull
    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    public Integer getJobHistoryId() {
        return jobHistoryId;
    }

    public void setJobHistoryId(Integer jobHistoryId) {
        this.jobHistoryId = jobHistoryId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JobHistoryId entity = (JobHistoryId) o;
        return Objects.equals(this.jobHistoryId, entity.jobHistoryId) &&
                Objects.equals(this.employeeId, entity.employeeId) &&
                Objects.equals(this.startDate, entity.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobHistoryId, employeeId, startDate);
    }

}