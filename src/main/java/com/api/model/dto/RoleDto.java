package com.api.model.dto;

import com.api.model.UserDao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class RoleDto {
    private String roleName;
}
