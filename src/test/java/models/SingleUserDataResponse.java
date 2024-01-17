package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SingleUserDataResponse {
            int id;
            String email;
            @JsonProperty("first_name")
            String firstName;
            @JsonProperty("last_name")
            String lastName;
            String avatar;

}
