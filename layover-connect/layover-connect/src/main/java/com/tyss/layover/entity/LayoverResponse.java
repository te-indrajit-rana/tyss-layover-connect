package com.tyss.layover.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tyss.layover.converter.StringListConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = Include.NON_DEFAULT)
public class LayoverResponse implements Serializable {

   
	private static final long serialVersionUID = 9053241128885937996L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="layover_response_id", unique=true, nullable=false, precision=10)
    private int layoverResponseId;
    @Column(name="accepted_count", precision=10)
    private int acceptedCount;
    @Column(name="accepted_single_rooms", precision=10)
    private int acceptedSingleRooms;
    @Column(name="accepted_double_rooms", precision=10)
    private int acceptedDoubleRooms;
    @Column(name="accepted_accessible_rooms", precision=10)
    private int acceptedAccessibleRooms;
    @Convert(converter = StringListConverter.class)
    @Column(name="accepted_meals", length=255)
    private List<String> acceptedMeals;
    @Column(length=45)
    private String status; 	
    @Column(name="response_note", length=255)
    private String responseNote;
	private BigDecimal totalAmount;
    @OneToOne(optional=false,cascade = CascadeType.ALL)
    @JoinColumn(name="layover_request_id", nullable=false)
    private LayoverRequest layoverRequest;

    

}
