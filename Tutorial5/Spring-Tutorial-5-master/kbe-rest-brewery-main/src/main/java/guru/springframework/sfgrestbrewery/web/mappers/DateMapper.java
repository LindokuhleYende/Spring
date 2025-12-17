package guru.springframework.sfgrestbrewery.web.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Converts between Timestamp and OffsetDateTime.
 * MapStruct uses this helper for date conversions.
 */
@Component
public class DateMapper {

    // Convert SQL Timestamp → OffsetDateTime
    public OffsetDateTime asOffsetDateTime(Timestamp ts){
        if (ts != null){
            return OffsetDateTime.of(
                    ts.toLocalDateTime().getYear(),
                    ts.toLocalDateTime().getMonthValue(),
                    ts.toLocalDateTime().getDayOfMonth(),
                    ts.toLocalDateTime().getHour(),
                    ts.toLocalDateTime().getMinute(),
                    ts.toLocalDateTime().getSecond(),
                    ts.toLocalDateTime().getNano(),
                    ZoneOffset.UTC
            );
        } else {
            return null;
        }
    }

    // Convert OffsetDateTime → SQL Timestamp
    public Timestamp asTimestamp(OffsetDateTime offsetDateTime){
        if(offsetDateTime != null) {
            return Timestamp.valueOf(
                    offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()
            );
        } else {
            return null;
        }
    }
}