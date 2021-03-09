package nextstep.subway.line;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.line.dto.LineResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineSteps {

  public static ExtractableResponse<Response> 지하철_노선_생성요청(Map<String, Object> params) {

    return RestAssured.given().log().all()
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/lines")
            .then()
            .log().all().extract();
  }

  public static void 지하철_노선_요청에대한_응답_확인(ExtractableResponse<Response> response, LineResponse line) {
    LineResponse lineResponse = response.as(LineResponse.class);
    assertAll(
            () -> assertEquals(line.getName(), lineResponse.getName()),
            () -> assertEquals(line.getColor(), lineResponse.getColor())
    );
  }

  public static ExtractableResponse<Response> 지하철_노선_목록_조회_요청() {
    return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get("/lines")
            .then()
            .log().all().extract();
  }

  public static void 지하철_노선_목록_응답_확인(ExtractableResponse<Response> response, List<LineResponse> line) {
    List<LineResponse> lineResults = response.body().jsonPath().getList(".", LineResponse.class);
    assertThat(lineResults).isEqualTo(line);
  }

  public static void 지하철_노선_응답_확인(int statusCode, HttpStatus status) {
    assertThat(statusCode).isEqualTo(status.value());
  }

  public static ExtractableResponse<Response> 지하철_노선_조회_요청(LineResponse line) {
    return RestAssured.given().log().all()
            .when()
            .get("/lines/{id}", line.getId())
            .then().log().all()
            .extract();
  }

  public static ExtractableResponse<Response> 지하철_노선_수정_요청(LineResponse line) {
    return RestAssured.given().log().all()
            .body(line)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .put("/lines/{id}", line.getId())
            .then().log().all()
            .extract();
  }

  public static ExtractableResponse<Response> 지하철_노선_제거_요청(LineResponse line) {
    return RestAssured.given().log().all()
            .when()
            .delete("/lines/{id}", line.getId())
            .then().log().all()
            .extract();
  }
}
