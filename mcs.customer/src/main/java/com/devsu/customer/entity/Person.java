package com.devsu.customer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Persona")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    @JsonProperty("customer_id")
    private Integer customerId;

    @NotEmpty(message = "El campo nombre es obligatorio por favor introduzca")
    @Column(name = "nombre")
    private String name;

    @NotEmpty(message = "El campo genero es obligatorio escoja entre MASCULINO, FEMENINO o NO_DEFINE ")
    @Column(name = "genero")
    private String gender;

    @Min(18)
    @Max(100)
    @Column(name = "edad")
    private byte age;

    @NotEmpty(message = "El campo identificacion es obligatorio por favor introduzca")
    @Column(name = "identificacion", unique = true)
    @Pattern(regexp = "^[0-9]{10,13}$",
            message = "Numero de identificación incorrecto, la identificación debe contener entre 10 y 13 digitos")
    private String id;

    @NotEmpty(message = "El campo dirección es obligatorio, por favor intorduzca")
    @Column(name = "direccion")
    private String address;

    @NotEmpty(message = "El campo telefono es obligatorio, por favor intorduzca")
    @Pattern(regexp = "^[0-9]{9,13}$",
            message = "Numero de telefono incorrecto, el telefono debe contener entre 10 y 13 digitos")
    @Column(name = "telefono")
    private String phone;
}
