package com.ninjaone.backendinterviewproject.model;

import com.ninjaone.backendinterviewproject.domain.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Device", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueDeviceNameAndType", columnNames = {"systemName", "type"})
})
public class Device {

    @Id
    private Long id;

    private String systemName;

    private DeviceType type;
}
