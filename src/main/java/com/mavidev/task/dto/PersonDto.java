package com.mavidev.task.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

//A dto for information transfer between client and server.
//The dto will be immutable and stateless.

@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
@Getter
@AllArgsConstructor
public final class PersonDto {

    UUID id;

    String name;
    String surname;
    String email;

    // Decided to use OffsetDateTime rather than Timestamp in View, because it is more detailed, thus more suitable for showing the dates.
    OffsetDateTime creationTime ;
    OffsetDateTime updateTime ;

}
