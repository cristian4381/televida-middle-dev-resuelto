/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.televida.reclutamiento.model.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 *
 * @author Genaro W. <Genaro.W>
 */
@Getter
@Setter
@SuperBuilder
public abstract class AbstractEntity{

    @Column(name = "status", nullable = false)
    private Integer status;
    
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDate creationDate;
    
    @Column(name = "change_date", nullable = false)
    private LocalDate changeDate;

    @Column(name = "created_by", nullable = false, length = 255, updatable = false)
    private String createdBy;

    @Column(name = "changed_by", nullable = false, length = 255)
    private String changedBy;

    @Version
    @Column(name = "version", nullable = false)
    private short version;

    public AbstractEntity() {
        this.status = 1;
        this.creationDate = LocalDate.now();
        this.changeDate = LocalDate.now();
        this.createdBy = "unknow";
        this.changedBy = "unknow";
        this.version = 1;
    }

    @Override
    public String toString() {
        return "AbstractEntity{"
                + "status=" + status
                + ", creationDate=" + creationDate
                + ", changeDate=" + changeDate
                + ", createdBy=" + createdBy
                + ", changedBy=" + changedBy
                + ", version=" + version
                + '}';
    }

}
