package models;

import lombok.Data;

@Data
public class CreateAndUpdateUserRequest {
    private String name, job;
}
