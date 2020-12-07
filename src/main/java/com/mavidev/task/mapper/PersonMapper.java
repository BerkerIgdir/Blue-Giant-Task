package com.mavidev.task.mapper;

import com.mavidev.task.dto.PersonDto;
import com.mavidev.task.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;


import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel="spring")
public interface PersonMapper {

    @Mappings({ @Mapping(source = "creationDate" ,target = "creationTime", qualifiedByName = "TimestamptoOffsetDateTime"),
            @Mapping(source = "lastModifiedDate" ,target = "updateTime", qualifiedByName = "TimestamptoOffsetDateTime")})
    PersonDto personToDto(Person person);

    @Mappings({ @Mapping(source = "creationTime" ,target = "creationDate", qualifiedByName = "OffsetDateTimetoTimestamp"),
            @Mapping(source = "updateTime" ,target = "lastModifiedDate", qualifiedByName = "OffsetDateTimetoTimestamp")})
    Person personDtoToPerson(PersonDto personDto);

    //Custom mapping method
    @Named("TimestamptoOffsetDateTime")
     static OffsetDateTime asOffsetDateTime(Timestamp ts){
        if (ts != null){
            return OffsetDateTime.of(ts.toLocalDateTime().getYear(), ts.toLocalDateTime().getMonthValue(),
                    ts.toLocalDateTime().getDayOfMonth(), ts.toLocalDateTime().getHour(), ts.toLocalDateTime().getMinute(),
                    ts.toLocalDateTime().getSecond(), ts.toLocalDateTime().getNano(), ZoneOffset.UTC);
        } else {
            return null;
        }
    }

    @Named("OffsetDateTimetoTimestamp")
     static Timestamp asTimestamp(OffsetDateTime offsetDateTime){
        if(offsetDateTime != null) {
            return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        } else {
            return null;
        }
    }

}
