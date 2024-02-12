package edu.school21.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
@OrmEntity(table = "simple_user")
public class User {

    @OrmColumnId
    private Long id;

    @OrmColumn(name = "first_name", length = 10)
    private String firstName;

    @OrmColumn(name = "last_name", length = 10)
    private String lastName;

    @OrmColumn(name = "age")
    private Integer age;
}

