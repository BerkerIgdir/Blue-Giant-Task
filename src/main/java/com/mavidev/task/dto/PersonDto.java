package com.mavidev.task.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.OffsetDateTime;

//A dto for information transfer between client and server.
//The dto will be immutable and stateless.

@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
@Getter
@AllArgsConstructor
public final class PersonDto {

    long id;

    String name;
    String surname;
    String email;

    // Decided to use OffsetDateTime rather than Timestamp in View, because it is more detailed, thus more suitable for showing the dates.
    OffsetDateTime creationTime ;
    OffsetDateTime updateTime ;

}
