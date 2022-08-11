package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class APISpecs {

    public static RequestSpecification logRequestSpec = with()
            .log().uri()
            .log().body();

    public static RequestSpecification contentTypeJSONRequestSpec = with()
            .contentType(ContentType.JSON);

    public static ResponseSpecification logResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.BODY)
            .build();

    public static ResponseSpecification status200ResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification status404ResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .build();

    public static ResponseSpecification status201ResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();

    public static ResponseSpecification status204ResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification status400ResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .build();
}
