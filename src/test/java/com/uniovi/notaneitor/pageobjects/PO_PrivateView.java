package com.uniovi.notaneitor.pageobjects;

import com.uniovi.notaneitor.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PO_PrivateView extends PO_NavView{

    static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep)
    {
//Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
        SeleniumUtils.waitSeconds(driver, 5);
//Seleccionamos el alumnos userOrder
        new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
//Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);
        WebElement score = driver.findElement(By.name("score"));
        score.click();
        score.clear();
        score.sendKeys(scorep);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    static public void logIn(WebDriver driver, String dni, String password){
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillForm(driver, dni, password);
    }

    static public void logOut(WebDriver driver){
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }

    static public void goToMarkMenu(WebDriver driver){
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
        elements.get(0).click();
    }

    static public void addMark(WebDriver driver, int userOrder,String desc, String nota){
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'mark/add')]");
//Pinchamos en agregar Nota.
        elements.get(0).click();
//Ahora vamos a rellenar la nota. //option[contains(@value, '4')]
        PO_PrivateView.fillFormAddMark(driver, userOrder, desc, nota);
    }

    static public void goToLastPage(WebDriver driver){
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
//Nos vamos a la última página
        elements.get(3).click();
    }

}
