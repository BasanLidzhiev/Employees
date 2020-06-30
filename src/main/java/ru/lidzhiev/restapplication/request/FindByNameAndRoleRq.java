package ru.lidzhiev.restapplication.request;

import lombok.Data;

@Data
public class FindByNameAndRoleRq {
    String name;
    String role;

}
