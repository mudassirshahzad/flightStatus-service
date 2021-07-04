package com.emirates.parentproject.flightstatus.bean;

/*
 * flightStatusService
 */

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.Date;

/**
 * The type FlightStatus bean.
 */
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Document(expiryExpression = "${service.repository.document.ttl}")
public final class FlightStatusDAO {

    @Id
    private String id;

    @Version
    private long version;

    @CreatedDate
    private Date creationDate;

    @LastModifiedDate
    private Date lastModificationDate;

    @Field
    private Date flightDate;

    @Field
    private String departureAirport;

    @Field
    private String arrival;

}
