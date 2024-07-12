package com.app.telegrambot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private int id;

    private String address;

    private String name;

    @Override
    public String toString() {
        return name == null ? address : address + " - " + name;
    }
}
