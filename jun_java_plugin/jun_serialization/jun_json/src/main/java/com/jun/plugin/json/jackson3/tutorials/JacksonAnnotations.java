package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by dzy on 2018/9/1
 * Ref: http://tutorials.jenkov.com/java-json/jackson-annotations.html
 */
public class JacksonAnnotations {

  private static void testIgnore() {
    PersonIgnore personIgnore = new PersonIgnore();
    personIgnore.personId = 1;
    personIgnore.name = "dzy";
    ObjectMapper objectMapper = new ObjectMapper();
    StringWriter sw = new StringWriter();
    try {
      objectMapper.writeValue(sw, personIgnore);
      sw.close();
      System.out.println("-------- testIgnore out -----------\n" + sw.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void testAutoDetect() {
    PersonAutoDetect personAutoDetect = new PersonAutoDetect();
    personAutoDetect.name = "dzy";
    System.out.println("-------- testAutoDetect out -----------\n" + convObjToJsonStr(personAutoDetect));
  }

  private static void testJsonSetter() {
    PersonJsonSetter personJsonSetter = new PersonJsonSetter();
    personJsonSetter.setName("dzy");
    personJsonSetter.setPersonId(1);

    String sJson = convObjToJsonStr(personJsonSetter);
    System.out.println("-------- testJsonSetter out -----------\n" + sJson);

    PersonJsonSetter personJsonSetter2 = (PersonJsonSetter) convJsonStrToObj(sJson, personJsonSetter.getClass());
    System.out.println("-------- testJsonSetter out2 -----------\n" + personJsonSetter2);
  }

  private static void testIgnoreType() {
    PersonIgnoreType personIgnoreType = new PersonIgnoreType();
    personIgnoreType.personId = 1;
    personIgnoreType.name = "dzy";
    PersonIgnoreType.Address addr = new PersonIgnoreType.Address();
    addr.city = "beijing";
    personIgnoreType.address = addr;
    System.out.println("-------- testIgnoreType out -----------\n" + convObjToJsonStr(personIgnoreType));
  }

  private static String convObjToJsonStr(Object obj) {
    ObjectMapper objectMapper = new ObjectMapper();
    StringWriter sw = new StringWriter();
    try {
      objectMapper.writeValue(sw, obj);
      sw.close();
      return sw.toString();
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }
  }

  private static Object convJsonStrToObj(String sJson, Class cls) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(sJson, cls);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static void testIgnoreProps() {
    PersonIgnoreProperties personIgnoreProperties = new PersonIgnoreProperties();
    personIgnoreProperties.personId = 1;
    personIgnoreProperties.firstName = "deng";
    personIgnoreProperties.lastName = "gary";
    System.out.println("-------- testIgnoreProps out -----------\n" + convObjToJsonStr(personIgnoreProperties));
  }

  private static void testJsonAnySetter() {
    String sJson = "{\"id\"   : 1234, \"name\" : \"John\" }";
    System.out.println(sJson);
    Bag bag = (Bag) convJsonStrToObj(sJson, Bag.class);
    System.out.println("bag = " + bag);
  }


  private static void testJsonCreator() {
    String sJson = "{\"id\"   : 1234, \"name\" : \"John\" }";
    System.out.println(sJson);
    PersonImmutable inst = (PersonImmutable) convJsonStrToObj(sJson, PersonImmutable.class);
    System.out.println("PersonImmutable = " + inst);
  }

  private static void testJsonInject() {
    String sJson = "{\"id\"   : 1234, \"name\" : \"John\" }";
    InjectableValues inject = new InjectableValues.Std().addValue(String.class, "jenkov.com");
    try {
      PersonInject inst = new ObjectMapper().reader(inject)
        .forType(PersonInject.class)
        .readValue(sJson);
      System.out.println("PersonInject = " + inst);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void testPersonDeserialize() throws IOException {
    String sJson = "{\"id\"   : 1234, \"name\" : \"John\" ,\"enabled\":0}";
    System.out.println(sJson);
    PersonDeserialize inst = (PersonDeserialize) convJsonStrToObj(sJson, PersonDeserialize.class);
    System.out.println("testPersonDeserialize.inst = " + inst);

    ObjectMapper objectMapper = new ObjectMapper();
    PersonDeserialize person = objectMapper
      .readerFor(PersonDeserialize.class)
      .readValue(sJson);
    System.out.println("testPersonDeserialize.inst2 = " + person);
  }

  private static void testPersonGetter() {
    String sJson = "{\"id\"   : 1234}";
    PersonGetter inst = (PersonGetter) convJsonStrToObj(sJson, PersonGetter.class);
    System.out.println("testPersonGetter.inst = " + inst);
  }

  private static void testPersonInclude() {
    PersonInclude inst = new PersonInclude();
    inst.name= "dzy";
    inst.personId = 1;

    System.out.println("testPersonInclude = " + convObjToJsonStr(inst));
  }

  private static void testPersonRawValue(){
    PersonRawValue inst = new PersonRawValue();
    System.out.println("PersonRawValue.jsonstr  = " + convObjToJsonStr(inst));
  }

  private static void testPersonValue (){
    PersonValue  inst = new PersonValue ();
    inst.name="dzy";
    inst.personId = 10;
    System.out.println("PersonValue .jsonstr  = " + convObjToJsonStr(inst));
  }

  private static void testPersonAnyGetter() {
    PersonAnyGetter inst = new PersonAnyGetter();
    inst.propties().put("Key1", 1);
    inst.propties().put("Key2", "dzy");
    inst.propties().put("Key3", "drc");

    System.out.println("PersonAnyGetter.JsonStr = " + convObjToJsonStr(inst));
  }

  public static void main(String[] args) {
    try {
      testIgnore();
      testIgnoreProps();
      testIgnoreType();
      testAutoDetect();
      testJsonSetter();
      testJsonAnySetter();
      testJsonCreator();
      testJsonInject();
      testPersonDeserialize();
      testPersonInclude();
      testPersonGetter();
      testPersonAnyGetter();
      testPersonRawValue();
      testPersonValue();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
