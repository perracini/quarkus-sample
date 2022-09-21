package org.acme.quarkus.sample;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ResourceBundle;

import org.acme.quarkus.exception.ErrorResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(AlunoResource.class)
public class AlunoResourceTest {

	@Test
	public void getAll() {
		given()
		.when()
		.get()
		.then()
		.statusCode(200);
	}

	@Test
	public void getById() {
		Aluno aluno = createAluno();
		Aluno saved = given().contentType(ContentType.JSON).body(aluno)
				.post()
				.then()
				.statusCode(201).extract().as(Aluno.class);
		Aluno got = given()
				.when()
				.get("/{alunoId}", saved.getAlunoId())
				.then()
				.statusCode(200).extract().as(Aluno.class);
		assertThat(saved).isEqualTo(got);
	}

	@Test
	public void getByIdNotFound() {
		given().when().get("/{alunorId}", 987654321).then().statusCode(404);
	}

	@Test
	public void post() {
		Aluno aluno = createAluno();
		Aluno saved = given().contentType(ContentType.JSON).body(aluno).post().then().statusCode(201).extract()
				.as(Aluno.class);
		assertThat(saved.getAlunoId()).isNotNull();
	}

	@Test
	public void postFailNoName() {
		Aluno aluno = createAluno();
		aluno.setName(null);
		ErrorResponse errorResponse = given().contentType(ContentType.JSON).body(aluno).post().then().statusCode(400)
				.extract().as(ErrorResponse.class);
		assertThat(errorResponse.getErrorId()).isNull();
		assertThat(errorResponse.getErrors()).isNotNull().hasSize(1)
				.contains(new ErrorResponse.ErrorMessage("post.aluno.name", getErrorMessage("Aluno.name.required")));
	}

	@Test
	public void put() {
		Aluno aluno = createAluno();
		Aluno saved = given().contentType(ContentType.JSON).body(aluno).post().then().statusCode(201).extract()
				.as(Aluno.class);
		saved.setName("Updated");
		given().contentType(ContentType.JSON).body(saved).put("/{alunoId}", saved.getAlunoId()).then().statusCode(204);
	}

	private Aluno createAluno() {
		Aluno aluno = new Aluno();
		aluno.setName(RandomStringUtils.randomAlphabetic(10));
		return aluno;
	}

	private String getErrorMessage(String key) {
		return ResourceBundle.getBundle("ValidationMessages").getString(key);
	}
}