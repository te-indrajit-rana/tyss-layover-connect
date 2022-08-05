package com.tyss.layover.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Admin implements Serializable {

	private static final long serialVersionUID = 1337329548889272140L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id", unique = true, nullable = false, precision = 10)
	private Integer adminId;

	@Column(length = 255)
	private String email;

	@Column(length = 255)
	private String name;

}
