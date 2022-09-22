package org.my.basic.model;

import javax.persistence.*;

@Entity
@Table(name = "dorm_infos")
public class DormInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "build_num", nullable = false)
    private Integer buildNum;
    @Column(name = "is_empty", nullable = false)
    private boolean isEmpty;
    @Column(name = "owner_name")
    private String ownerName;

    public Integer getId() {
        return id;
    }

    public Integer getBuildNum() {
        return buildNum;
    }

    public void setBuildNum(Integer buildNum) {
        this.buildNum = buildNum;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}