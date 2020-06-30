package ru.lidzhiev.restapplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.lidzhiev.restapplication.entity.Employee;
import ru.lidzhiev.restapplication.repository.EmployeeRepository;
import ru.lidzhiev.restapplication.response.EmployeeResponse;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class EmployeeControllerTest {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private MockMvc mvc;

    private JSONParser parser = new JSONParser();

    @BeforeEach
    @Sql(scripts = "classpath:/clean-up.sql")
    @Sql(scripts = "classpath:/data.sql")
    void setup() {

    }

    @Test
    void all() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/employees")).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void newEmployee() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(input("json/Employee.json"))).andReturn();
        org.junit.Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getByName() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/findByName")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("Bilbo")).andReturn();
        org.junit.Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getByRole() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/findByRole")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("burglar")).andReturn();
        org.junit.Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getByNameAndRole() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/findByNameAndRole")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(input("json/FindByNameAndRoleRq.json"))).andReturn();
        org.junit.Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getByNameAndRole1() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/findByNameAndRole1")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(input("json/FindByNameAndRoleRq.json"))).andReturn();
        org.junit.Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getByNameAndRole2() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/findByNameAndRole2")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(input("json/FindByNameAndRoleRq.json"))).andReturn();
        org.junit.Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void one() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/employee/1")).andReturn();
        org.junit.Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void replaceEmployee() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/employees/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(input("json/Employee.json"))).andReturn();
        org.junit.Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void delete() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/delete/3")).andReturn();
        org.junit.Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getByNameError() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/findByName")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("fur")).andReturn();
        EmployeeResponse response = responseFromString(mvcResult.getResponse().getContentAsString());
        org.junit.Assert.assertEquals(1, response.getCode());
    }

    @Test
    void getByRoleError() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/findByRole")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("fur")).andReturn();
        EmployeeResponse response = responseFromString(mvcResult.getResponse().getContentAsString());
        org.junit.Assert.assertEquals(1, response.getCode());
    }

    @Test
    void getByNameAndRoleError() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/findByNameAndRole")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(input("json/FindByNameAndRoleRqError.json"))).andReturn();
        EmployeeResponse response = responseFromString(mvcResult.getResponse().getContentAsString());
        org.junit.Assert.assertEquals(1, response.getCode());
    }

    @Test
    void oneError() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/employee/12")).andReturn();
        EmployeeResponse response = responseFromString(mvcResult.getResponse().getContentAsString());
        Assert.assertEquals(1, response.getCode());
    }

    @Test
    void testDeleteError() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/delete/300")).andReturn();
        EmployeeResponse response = responseFromString(mvcResult.getResponse().getContentAsString());
        Assert.assertEquals(1, response.getCode());
    }

    @Test
    void testDeleteByNameError() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/delete")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("Bilboa")).andReturn();
        EmployeeResponse response = responseFromString(mvcResult.getResponse().getContentAsString());
        Assert.assertEquals(1, response.getCode());
    }

    @Test
    void setAddress() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/employees/set-adresses/1&1")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("Bilbo")).andReturn();
        org.junit.Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void testError405() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/add")).andReturn();
        Assert.assertEquals(405, mvcResult.getResponse().getStatus());
    }

    @Test
    void testError404() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/employeesasd/10")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(input("json/Employee.json"))).andReturn();
        Assert.assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    void testError400() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/delete")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("")).andReturn();
        org.junit.Assert.assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    void testErrorGetByName() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/findByName")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("Bilboa")).andReturn();
        log.info(mvcResult.getResponse().getErrorMessage());
        org.junit.Assert.assertEquals(404, mvcResult.getResponse().getStatus());
    }



    private String input(String fileName) throws IOException, ParseException {
        URL url = getClass().getClassLoader().getResource(fileName);
        Object obj = parser.parse(new FileReader(url.getPath()));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private EmployeeResponse responseFromString(String jsonString) throws IOException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, EmployeeResponse.class);
    }

    private void testGetSuccess(String uri) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        org.junit.Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    private void testPostSuccess(String uri, String fileName) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(input(fileName))).andReturn();
        org.junit.Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    private void testDeleteSuccess(String uri) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        org.junit.Assert.assertEquals(200, mvcResult.getResponse().getStatus());

    }
}