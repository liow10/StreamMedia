package com.micro.iotclouds.communication.video.core.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author longyubo 2020年1月2日 下午3:36:29
 **/
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
public class StreamName implements Serializable {
	private String app;
	private String name;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StreamName other = (StreamName) obj;
		if (app == null) {
			if (other.app != null)
				return false;
		} else if (!app.equals(other.app))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((app == null) ? 0 : app.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "StreamName{" +
				"app='" + app + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
