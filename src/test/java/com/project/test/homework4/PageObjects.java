package com.project.test.homework4;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PageObjects {

    public ElementsCollection taskList = $$("#todo-list li");
    public SelenideElement clearCompleted = $("#clear-completed");
    public SelenideElement itemsLeft = $("#todo-count>strong");

    public void addTask(String task) {
        $("#new-todo").setValue(task).pressEnter();
    }

    public void filterAll() {
        $("[href='#/']").click();
    }

    public void filterActive(){
        $("[href='#/active']").click();
    }

    public void filterCompleted(){
        $("[href='#/completed']").click();
    }

    public void toggle(String task) {
        taskList.find(text(task)).find(".toggle").click();
    }

    public void deleteTask(String task) {
        taskList.findBy(text(task)).hover().find(".destroy").click();
    }

    public void editTask(String oldTask, String newTask) {
        taskList.findBy(text(oldTask)).find("label").doubleClick();
        taskList.findBy(cssClass("editing")).find(".edit").setValue(newTask).pressEnter();
    }

    public void clearCompleted(){
        clearCompleted.click();
        clearCompleted.should(disappear);
    }

    public void toggleAll() {
        $("#toggle-all").click();
    }

    public void checkItemsLeftCounter(int number){
        $("#todo-count>strong").shouldHave(exactText(Integer.toString(number)));
    }

/*    public void checkCompletedCounter(int number) {
        $("#clear-completed").shouldHave(exactText(Integer.toString(number)));
        /*
        что если тут написать exactText вместо
        $("#clear-completed").shouldHave(text("(" + Integer.toString(number) + ")"));
        я не понимаю как кавычки спасают положение
         */
    }

