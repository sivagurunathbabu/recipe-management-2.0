package com.org.recipe.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.org.recipe.Application;

@SpringBootTest(classes = Application.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.2.10")
public class AbstractTest extends AbstractTestNGSpringContextTests {

}