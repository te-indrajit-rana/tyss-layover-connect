package com.tyss.layover.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = "hotel")
public class Image implements Serializable {

	private static final long serialVersionUID = -5621936311631113905L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id", unique = true, nullable = false, precision = 10)
	private int imageId;
	@Column(name = "image_name", length = 255)
	private String imageName;
	@Column(name = "image_path", length = 255)
	private String imagePath;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	@PreRemove
	public void name() {
		this.hotel=null;
	}
}
