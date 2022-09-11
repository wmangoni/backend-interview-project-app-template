package com.ninjaone.backendinterviewproject.model;

import com.ninjaone.backendinterviewproject.domain.DeviceType;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueDeviceNameAndType", columnNames = {"systemName", "type"})
})
public class Device {
    @Id
    private Long id;

    private String systemName;
    private DeviceType type;

    public Device() {
    }

    public Device(Long id, String systemName, DeviceType deviceType) {
        this.id = id;
        this.systemName = systemName;
        this.type = deviceType;
    }

    public Long getId() {
        return id;
    }

    public String getSystemName() {
        return systemName;
    }

    public DeviceType getType() {
        return type;
    }
}
