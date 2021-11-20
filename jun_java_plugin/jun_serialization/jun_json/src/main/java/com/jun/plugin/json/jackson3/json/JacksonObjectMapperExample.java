package com.jun.plugin.json.jackson3.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jun.plugin.json.jackson3.model.Address;
import com.jun.plugin.json.jackson3.model.Employee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by dzy on 2018/8/31
 */
public class JacksonObjectMapperExample {
  public static void main(String[] args) throws IOException, URISyntaxException {
    baseOpera();
//    readSpecJsonKey();
//    writeJson();
//    streamRead();
//    streamWrite();
  }

  private static void baseOpera() throws IOException, URISyntaxException {
    byte[] jsonData = getJsonBytes("/employee1.txt");
    ObjectMapper objectMapper = new ObjectMapper();
    // For instance, the JSON field named brand matches the Java getter and setter methods called getBrand() and setBrand()
    Employee emp = objectMapper.readValue(jsonData,Employee.class);
    System.out.println("Employee Object:\n"+emp);

    Employee emp1 = createEmployee();
    objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);
    StringWriter stringEmp = new StringWriter();
    objectMapper.writeValue(stringEmp,emp1);
    System.out.println("Employee JSON is \n"+stringEmp);

    //conv json to Map
    byte[] mapData = getJsonBytes("data.txt");
    HashMap myMap = objectMapper.readValue(mapData, HashMap.class);
    System.out.println("Map is : "+myMap);

    // another way
    myMap = objectMapper.readValue(mapData,new TypeReference<HashMap<String,String>>(){});
    System.out.println("Map using TypeReference : "+myMap);
  }

  /**
   * Edit JSON Document
   * @throws IOException
   * @throws URISyntaxException
   */
  private static  void writeJson() throws IOException, URISyntaxException {
    byte[] jsonData = getJsonBytes("employee.txt");
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode = objectMapper.readTree(jsonData);

    ((ObjectNode)rootNode).put("id",500);
    ((ObjectNode)rootNode).put("test","test value");
    ((ObjectNode)rootNode).remove("role");
    ((ObjectNode)rootNode).remove("properties");

    objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);
    objectMapper.writeValue(new File("updated_emp.txt"),rootNode);
  }

  private static void streamWrite() throws URISyntaxException, IOException {
    Employee emp = createEmployee();
    JsonGenerator jsonGenerator = new JsonFactory().createGenerator(new FileOutputStream("stream_emp.txt"));

    jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
    jsonGenerator.writeStartObject();
    jsonGenerator.writeNumberField("id",emp.getId());
    jsonGenerator.writeStringField("name", emp.getName());
    jsonGenerator.writeBooleanField("permanent", emp.isPermanent());

    jsonGenerator.writeObjectFieldStart("address"); //start address object
    jsonGenerator.writeStringField("street", emp.getAddress().getStreet());
    jsonGenerator.writeStringField("city", emp.getAddress().getCity());
    jsonGenerator.writeNumberField("zipcode", emp.getAddress().getZipcode());
    jsonGenerator.writeEndObject(); //end address object

    jsonGenerator.writeArrayFieldStart("phoneNumbers");
    for(long num : emp.getPhoneNumbers()) {
      jsonGenerator.writeNumber(num);
    }
    jsonGenerator.writeEndArray();

    jsonGenerator.writeStringField("role", emp.getRole());

    jsonGenerator.writeArrayFieldStart("cities"); //start cities array
    for(String city : emp.getCities()) {
      jsonGenerator.writeString(city);
    }
    jsonGenerator.writeEndArray(); //closing cities array

    jsonGenerator.writeObjectFieldStart("properties");
    Set<String> keySet = emp.getProperties().keySet();
    for(String key : keySet){
      String value = emp.getProperties().get(key);
      jsonGenerator.writeStringField(key, value);
    }
    jsonGenerator.writeEndObject(); //closing properties
    jsonGenerator.writeEndObject(); //closing root object

    jsonGenerator.flush();
    jsonGenerator.close();
    System.out.println("streamWrite to file: stream_emp.txt");
  }

  private static void streamRead() throws URISyntaxException, IOException {
    URI resUri = JacksonObjectMapperExample.class.getResource("employee.txt").toURI();
    JsonParser jsonParser = new JsonFactory().createParser(new File(resUri));

    Employee emp = new Employee();
    Address address = new Address();
    emp.setAddress(address);
    emp.setCities(new ArrayList<>());
    emp.setProperties(new HashMap<>());
    List<Long> phoneNums = new ArrayList<>();
    boolean insidePropertiesObj = false;
    parseJSON(jsonParser,emp,phoneNums,insidePropertiesObj);

    long[] nums = new long[phoneNums.size()];
    int index = 0;
    for(Long l :phoneNums){
      nums[index++] = l;
    }
    emp.setPhoneNumbers(nums);

    jsonParser.close();
    //print employee object
    System.out.println("Employee Object\n\n"+emp);
  }

  private static void parseJSON(JsonParser jsonParser, Employee emp, List<Long> phoneNums, boolean insidePropertiesObj) throws IOException {
    //loop through the JsonTokens
    while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
      String name = jsonParser.getCurrentName();
      if ("id".equals(name)) {
        jsonParser.nextToken();
        emp.setId(jsonParser.getIntValue());
      } else if ("name".equals(name)) {
        jsonParser.nextToken();
        emp.setName(jsonParser.getText());
      } else if ("permanent".equals(name)) {
        jsonParser.nextToken();
        emp.setPermanent(jsonParser.getBooleanValue());
      } else if ("address".equals(name)) {
        jsonParser.nextToken();
        parseJSON(jsonParser, emp, phoneNums, insidePropertiesObj);
      } else if ("street".equals(name)) {
        jsonParser.nextToken();
        emp.getAddress().setStreet(jsonParser.getText());
      } else if ("city".equals(name)) {
        jsonParser.nextToken();
        emp.getAddress().setCity(jsonParser.getText());
      } else if ("zipcode".equals(name)) {
        jsonParser.nextToken();
        emp.getAddress().setZipcode(jsonParser.getIntValue());
      } else if ("phoneNumbers".equals(name)) {
        jsonParser.nextToken();
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
          phoneNums.add(jsonParser.getLongValue());
        }
      } else if ("role".equals(name)) {
        jsonParser.nextToken();
        emp.setRole(jsonParser.getText());
      } else if ("cities".equals(name)) {
        jsonParser.nextToken();
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
          emp.getCities().add(jsonParser.getText());
        }
      } else if ("properties".equals(name)) {
        jsonParser.nextToken();
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
          String key = jsonParser.getCurrentName();
          jsonParser.nextToken();
          String value = jsonParser.getText();
          emp.getProperties().put(key, value);
        }
      }
    }
  }

  private static  void readSpecJsonKey() throws IOException, URISyntaxException {
    byte[] jsonData = getJsonBytes("employee.txt");

    ObjectMapper objectMapper = new ObjectMapper();
    //read JOSN like DOM Parser
    JsonNode rootNode = objectMapper.readTree(jsonData);
    JsonNode idNode = rootNode.path("id");
    System.out.println("id= "+idNode.asInt());

    JsonNode phoneNosNode = rootNode.path("phoneNumbers");
    Iterator<JsonNode> elmts = phoneNosNode.elements();
    while (elmts.hasNext()){
      JsonNode elmt = elmts.next();
      System.out.println("phone no = "+elmt.asLong());
    }
  }

  private static byte[] getJsonBytes(String coreFileName) throws URISyntaxException, IOException {
//    URI resUri = System.class.getResource(coreFileName).toURI();
    URI resUri = JacksonObjectMapperExample.class.getResource(coreFileName).toURI();
    return Files.readAllBytes(Paths.get(resUri));
  }

  public static Employee createEmployee(){
    Employee emp = new Employee();
    emp.setId(100);
    emp.setName("David");
    emp.setPermanent(false);
    emp.setPhoneNumbers(new long[] { 123456, 987654 });
    emp.setRole("Manager");

    Address add = new Address();
    add.setCity("Bangalore");
    add.setStreet("BTM 1st Stage");
    add.setZipcode(560100);
    emp.setAddress(add);

    List<String> cities = new ArrayList<String>();
    cities.add("Los Angeles");
    cities.add("New York");
    emp.setCities(cities);

    Map<String, String> props = new HashMap<String, String>();
    props.put("salary", "1000 Rs");
    props.put("age", "28 years");
    emp.setProperties(props);

    return emp;
  }
}
