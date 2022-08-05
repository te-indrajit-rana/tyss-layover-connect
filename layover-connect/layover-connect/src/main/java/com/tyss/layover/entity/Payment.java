package com.tyss.layover.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

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
public class Payment implements Serializable {

	private static final long serialVersionUID = 4391805927829285699L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="payment_id", unique=true, nullable=false, precision=10)
    private int paymentId;
    @Column(name="airline_status", length=255)
    private String airlineStatus;
    @Column(name="hotel_acknowledgement", length=255)
    private String hotelAcknowledgement;
    @Column(name="hotel_status", length=255)
    private String hotelStatus;
    @Column(precision=19, scale=2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss",timezone = "Asia/Kolkata")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;
    private String invoicePath;
    @OneToOne(optional=false,cascade = CascadeType.ALL)
    @JoinColumn(name="layover_response_id", nullable=false)
    private LayoverResponse layoverResponse;
   

    

}
