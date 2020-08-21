import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import io.restassured.response.Response;

import io.restassured.specification.RequestSpecification;
import org.apache.tools.ant.taskdefs.Delete;
import org.testng.Assert;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;

import static io.restassured.RestAssured.*;
import static javax.management.Query.and;
import static org.hamcrest.Matchers.equalTo;

//создаем класс автотеста
 class Static_Test {
     public static void testtxt(){
            //тестирование TXT файла
           String TXT = given()
                    .baseUri("http://localhost:80")
                    .basePath("/static/index.txt")
                    .accept(ContentType.HTML)
                    .when().get()
                    .then().extract().response().asString();
            when().get("http://localhost:80/static/index.txt")
                    .then().log().body();

        }

    public static void testhtml(){
            //тестирование html страницы
        given()
                .baseUri("http://localhost:80")
                .basePath("/static/index.html")
                .accept(ContentType.HTML)
                .get().body().asString();
        when().get("http://localhost:80/static/index.html")
                .then().log().body();
        }

    public static void testpng(){
            //тестирование png
        given()
                .baseUri("http://localhost:80")
                .basePath("/static/index.png")
                .accept(ContentType.ANY)
                .when().get()
                .then().extract().response();

        System.out.println("PNG is ok");
        }

    public static void testcreate_code() {
        int StatusCreate = given()
                .baseUri("http://localhost:80")
                .basePath("/mock/create_employee")
                .body("{\"fio\" : \"Kenobi\", \"position\" : \"JediMaster\", \"number\" : \"66\"}")
                .when().post()
                .then().extract().statusCode();
        Assert.assertEquals(StatusCreate,201);
        System.out.println("Create_employee status is ok");
    }

        // запуск тестов
    public static void main(String[] args) {
         Static_Test test = new Static_Test();
         test.testtxt();
         test.testhtml();
        test.testpng();
    }
}

class Delete_json_test {
     //проверка полей в запросе /delete_employee
        public static void main(String[] args) throws JsonProcessingException {
       String json = given()
                .baseUri("http://localhost:80")
                .basePath("/mock/delete_employee")
                .accept(ContentType.JSON)
                .body("{\"id\" : \"1024\"}")
                .when().delete()
                .then().extract().response().asString();
       System.out.println(json);

       //проверка полей запроса

       ObjectMapper objectMapper = new ObjectMapper();
       JsonNode jsonNode = objectMapper.readTree(json);
       Assert.assertEquals(jsonNode.get("id").asText(), "1024");
       Assert.assertEquals(jsonNode.get("status").asText(), "DELETED" );

            Delete_json_test deleteJsonTest = new Delete_json_test();
            deleteJsonTest.testdelete_code();

    }
    public static void testdelete_code() {
        //тестироваине Statuscode /delete_employee
        int StatusDelete = given()
                .baseUri("http://localhost:80")
                .basePath("/mock/delete_employee")
                .body("{\"id\" : \"1024\"}")
                .when().delete()
                .then().extract().statusCode();
        Assert.assertEquals(StatusDelete,200);
        System.out.println("Delete_employee status is ok");

    }
}

class Get_json_test {
    //проверка полей в запросе /get_employee

    public static void testget_code() {
        //тестироваине Statuscode /Get_employee

        int StatusGet = given()
                .baseUri("http://localhost:80")
                .basePath("/mock/get_employee")
                .when().get()
                .then().extract().statusCode();
        Assert.assertEquals(StatusGet,200);
        System.out.println("Get_employee status is ok");

    }

    public static void main(String[] args) throws JsonProcessingException {
        String json = given()
                .baseUri("http://localhost:80")
                .basePath("/mock/get_employee")
                .accept(ContentType.JSON)
                .when().get()
                .then().extract().response().asString();
        System.out.println(json);



        //проверка полей запроса

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        Assert.assertEquals(jsonNode.get("id").asText(), "227");
        Assert.assertEquals(jsonNode.get("fio").asText(), "Фоломкин Антон Андреевич" );
        Assert.assertEquals(jsonNode.get("position").asText(), "Cпециалист по тестированию");
        Assert.assertEquals(jsonNode.get("number").asText(), "2");

        Get_json_test deleteJsonTest = new Get_json_test();
        deleteJsonTest.testget_code();

    }

}

class Post_json_test {
    //проверка полей в запросе /create_employee
    public static void main(String[] args) throws JsonProcessingException {
        String json = given()
                .baseUri("http://localhost:80")
                .basePath("/mock/create_employee")
                .accept(ContentType.JSON)
                .body("{\"fio\" : \"Kenobi\", \"position\" : \"JediMaster\", \"number\" : \"66\"}")
                .when().post()
                .then().extract().response().asString();
        System.out.println(json);



        //проверка полей запроса

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        Assert.assertEquals(jsonNode.get("fio").asText(), "Kenobi");
        Assert.assertEquals(jsonNode.get("position").asText(), "JediMaster" );
        Assert.assertEquals(jsonNode.get("number").asText(), "66");

        Post_json_test deleteJsonTest = new Post_json_test();
        deleteJsonTest.testcreate_code();

    }

    public static void testcreate_code() {
        int StatusCreate = given()
                .baseUri("http://localhost:80")
                .basePath("/mock/create_employee")
                .body("{\"fio\" : \"Kenobi\", \"position\" : \"JediMaster\", \"number\" : \"66\"}")
                .when().post()
                .then().extract().statusCode();
        Assert.assertEquals(StatusCreate,201);
        System.out.println("Create_employee status is ok");
    }


}