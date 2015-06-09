package com.project.test.homework4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;

public class TodosTest {

    TodoMVCPage pageObject = new TodoMVCPage();

//    public TodosTest() {
//       pageObject = new TodoMVCPage();
//    }

    @BeforeClass
    public static void openMvc() {
        open("http://todomvc.com/examples/troopjs_require/#/");
    }

    @Before
    public void clearData() {
        open("http://google.com/");
        open("http://todomvc.com/examples/troopjs_require/#/");
    }

    @After
    public void clearCache() {
        executeJavaScript("localStorage.clear()");
    }

    @Test
    public void testAtAllFilterTest() {
        //test preconditions
        pageObject.addTask("do1");
        pageObject.addTask("do2");
        pageObject.addTask("do3");
        pageObject.addTask("do4");
        pageObject.addTask("do5");

        //checking condition after adding tasks
        pageObject.filterAll();
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do1", "do2", "do3", "do4", "do5"));
        pageObject.checkItemsLeftCounter(5);
        pageObject.clearCompleted.shouldBe(hidden);
        pageObject.itemsLeft.shouldBe(visible);

        //marking task 1 and 2 as completed and checking that total number of tasks on filter All is 5
        pageObject.toggle("do1");
        pageObject.toggle("do2");
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do1", "do2", "do3", "do4", "do5"));
        pageObject.checkItemsLeftCounter(3);
        pageObject.clearCompleted.shouldBe(visible);

        //toggle do3
        pageObject.toggle("do3");
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do1", "do2", "do3", "do4", "do5"));
        pageObject.checkItemsLeftCounter(2);
        pageObject.clearCompleted.shouldBe(visible);

        //toggling all
        pageObject.toggleAll();
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do1", "do2", "do3", "do4", "do5"));
        pageObject.checkItemsLeftCounter(0);
        pageObject.clearCompleted.shouldBe(visible);
        pageObject.toggleAll();

        //edit task
        pageObject.editTask("do3", "do3 edited");
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do1", "do2", "do3 edited", "do4", "do5"));

        //delete task
        pageObject.deleteTask("do5");
        pageObject.deleteTask("do4");
        pageObject.checkItemsLeftCounter(3);
        pageObject.clearCompleted.shouldBe(hidden);

        //clear completed
        pageObject.toggleAll();
        pageObject.clearCompleted();
        pageObject.itemsLeft.shouldBe(hidden);
        pageObject.clearCompleted.shouldBe(hidden);

    }

    @Test
    public void testAtActiveFilterTest() {
        //test preconditions
        pageObject.addTask("do1");
        pageObject.addTask("do2");
        pageObject.addTask("do3");
        pageObject.addTask("do4");
        pageObject.addTask("do5");

        //checking active tasks on Active filter
        pageObject.filterActive();
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do1", "do2", "do3", "do4", "do5"));
        pageObject.checkItemsLeftCounter(5);
        pageObject.clearCompleted.shouldBe(hidden);

        //marking task 1 and 2 as completed and checking that total number of active tasks on filter is 3
        pageObject.toggle("do1");
        pageObject.toggle("do2");
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do3", "do4", "do5"));
        pageObject.checkItemsLeftCounter(3);
        pageObject.clearCompleted.shouldBe(visible);
        pageObject.filterAll();
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do1", "do2", "do3", "do4", "do5"));
        pageObject.filterActive();

        //toggle do3
        pageObject.toggle("do3");
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do4", "do5"));
        pageObject.checkItemsLeftCounter(2);
        pageObject.clearCompleted.shouldBe(visible);

        //adding tasks on Active filter page
        pageObject.addTask("active1");
        pageObject.addTask("active2");
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do4", "do5", "active1", "active2"));
        pageObject.checkItemsLeftCounter(4);
        pageObject.clearCompleted.shouldBe(visible);

        //edit task
        pageObject.editTask("active1", "active1 edited");
        pageObject.clearCompleted.shouldBe(visible);
        pageObject.checkItemsLeftCounter(4);

        //delete task
        pageObject.deleteTask("active1 edited");
        pageObject.clearCompleted.shouldBe(visible);
        pageObject.checkItemsLeftCounter(3);
        pageObject.filterAll();
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do1", "do2", "do3", "do4", "do5", "active2"));
        pageObject.filterActive();

        //toggle all
        pageObject.toggleAll();
        pageObject.taskList.filter(visible).shouldBe(empty);
        pageObject.checkItemsLeftCounter(0);
        pageObject.clearCompleted.shouldBe(visible);

        //clear all
        pageObject.clearCompleted();
        pageObject.taskList.filter(visible).shouldBe(empty);

    }

    @Test
    public void testAtCompletedFilterTest() {
        //test preconditions
        pageObject.addTask("do1");
        pageObject.addTask("do2");
        pageObject.addTask("do3");
        pageObject.addTask("do4");
        pageObject.addTask("do5");
        pageObject.toggle("do1");
        pageObject.toggle("do2");

        //checking completed tasks on Completed filter
        pageObject.filterCompleted();
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do1", "do2"));
        pageObject.checkItemsLeftCounter(3);
        pageObject.clearCompleted.shouldBe(visible);

        //marking do3 as complete
        pageObject.filterAll();
        pageObject.toggle("do3");
        pageObject.filterCompleted();
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do1", "do2", "do3"));
        pageObject.checkItemsLeftCounter(2);
        pageObject.clearCompleted.shouldBe(visible);

        //unmarking do3
        pageObject.toggle("do3");
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do1", "do2"));
        pageObject.clearCompleted.shouldBe(visible);
        pageObject.checkItemsLeftCounter(3);

        //edit task
        pageObject.editTask("do2", "do2 edited");
        pageObject.taskList.filter(visible).shouldHave(exactTexts("do1", "do2 edited"));
        pageObject.clearCompleted.shouldBe(visible);
        pageObject.checkItemsLeftCounter(3);

        //delete task
        pageObject.deleteTask("do2 edited");
        pageObject.clearCompleted.shouldBe(visible);
        pageObject.checkItemsLeftCounter(3);

        //clear completed
        pageObject.filterActive();
        pageObject.toggleAll();
        pageObject.checkItemsLeftCounter(0);
        pageObject.clearCompleted.shouldBe(visible);
        pageObject.filterCompleted();
        pageObject.clearCompleted();
        pageObject.clearCompleted.shouldBe(hidden);
        pageObject.itemsLeft.shouldBe(hidden);
    }
}
