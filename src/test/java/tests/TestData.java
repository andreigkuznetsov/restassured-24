package tests;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public class TestData {
    static String NAME = "Dima";
    static String JOB_TITLE = "QA";
    static String NEW_JOB_TITLE = "AQA";
    static String EMAIL = "eve.holt@reqres.in";
    static String PASSWORD = "pistol";
    static int ID = 4;
    static String TOKEN = "QpwL5tke4Pnpja7X4";
    static String ERROR_MESSAGE = "Missing password";
    static int SINGLE_USER_ID = 2;
    static String SINGLE_USER_EMAIL = "janet.weaver@reqres.in";
    static String FIRST_NAME = "Janet";
    static String LAST_NAME = "Weaver";
    static String AVATAR = "https://reqres.in/img/faces/2-image.jpg";
    static String URL = "https://reqres.in/#support-heading";
    static String TEXT = "To keep ReqRes free, contributions towards server costs are appreciated!";
}
