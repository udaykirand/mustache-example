package in.ukd.mustache.example;

import com.github.mustachejava.Mustache;
import in.ukd.mustache.example.bean.ToDo;
import in.ukd.mustache.example.bean.Util;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by udadh on 5/22/2017.
 */
public class MustacheTest {

    private String executeTemplate(Mustache m, Map<String, Object> context) throws IOException {
        StringWriter writer = new StringWriter();
        m.execute(writer, context).flush();
        return writer.toString();
    }

    private String executeTemplate(Mustache m, ToDo todo) throws IOException {
        StringWriter writer = new StringWriter();
        m.execute(writer, todo).flush();
        String ret = writer.toString();
        System.out.println("*********************"+ret);
        return ret;
    }

    @Test
    public void givenTodoObject_whenGetHtml_thenSuccess() throws IOException {
        ToDo todo = new ToDo("Todo 1", "Todo description");
        Mustache m = Util.getMustacheFactory().compile("todo.mustache");
        Map<String, Object> context = new HashMap<>();
        context.put("todo", todo);
        System.out.println(executeTemplate(m, todo));
        String expected = "Todo";
        assertThat(executeTemplate(m, todo)).contains(expected);
    }

    @Test
    public void givenNullTodoObject_whenGetHtml_thenEmptyHtml() throws IOException {
        Mustache m = Util.getMustacheFactory().compile("todo-section.mustache");
        Map<String, Object> context = new HashMap<>();
        assertThat(executeTemplate(m, context)).isEmpty();
    }

    @Test
    public void givenTodoList_whenGetHtml_thenSuccess() throws IOException {
        Mustache m = Util.getMustacheFactory().compile("todo-list.mustache");

        List<ToDo> todos = Arrays.asList(
                new ToDo("Todo 1", "Todo description"),
                new ToDo("Todo 2", "Todo description another"),
                new ToDo("Todo 3", "Todo description another")
        );
        Map<String, Object> context = new HashMap<>();
        context.put("todos", todos);

        assertThat(executeTemplate(m, context))
                .contains("<h2>Todo 1</h2>")
                .contains("<h2>Todo 2</h2>")
                .contains("<h2>Todo 3</h2>");
    }

    @Test
    public void givenEmptyList_whenGetHtml_thenEmptyHtml() throws IOException {
        Mustache m = Util.getMustacheFactory().compile("todo-list.mustache");

        Map<String, Object> context = new HashMap<>();
        assertThat(executeTemplate(m, context)).isEmpty();
        ;
    }

    @Test
    public void givenEmptyList_whenGetHtmlUsingInvertedSection_thenHtml() throws IOException {
        Mustache m = Util.getMustacheFactory().compile("todo-inverted.mustache");

        Map<String, Object> context = new HashMap<>();
        assertThat(executeTemplate(m, context).trim()).isEqualTo("<p>No todos!</p>");
    }

    @Test
    public void givenTodoList_whenGetHtmlUsingLamdba_thenHtml() throws IOException {
        Mustache m = Util.getMustacheFactory().compile("todo-lambda-sample.mustache");
        List<ToDo> todos = Arrays.asList(
                new ToDo("Todo 1", "Todo description"),
                new ToDo("Todo 2", "Todo description another"),
                new ToDo("Todo 3", "Todo description another")
        );
        todos.get(2).setDone(true);
        todos.get(2).setCompletedOn(Date.from(Instant.now().plusSeconds(300)));

        Map<String, Object> context = new HashMap<>();
        context.put("todos", todos);
        assertThat(executeTemplate(m, context).trim()).contains("Done 5 minutes ago");
    }
}
