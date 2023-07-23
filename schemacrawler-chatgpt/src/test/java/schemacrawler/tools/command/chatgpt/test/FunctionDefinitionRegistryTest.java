package schemacrawler.tools.command.chatgpt.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import schemacrawler.tools.command.chatgpt.FunctionDefinition;
import schemacrawler.tools.command.chatgpt.functions.DatabaseObjectDescriptionFunctionDefinition;
import schemacrawler.tools.command.chatgpt.functions.DatabaseObjectListFunctionDefinition;
import schemacrawler.tools.command.chatgpt.functions.ExitFunctionDefinition;
import schemacrawler.tools.command.chatgpt.functions.FunctionDefinitionRegistry;
import schemacrawler.tools.command.chatgpt.functions.LintFunctionDefinition;
import schemacrawler.tools.command.chatgpt.functions.TableDecriptionFunctionDefinition;
import schemacrawler.tools.command.chatgpt.functions.TableReferencesFunctionDefinition;
import schemacrawler.tools.command.chatgpt.systemfunctions.SchemaFunctionDefinition;

public class FunctionDefinitionRegistryTest {

  public static <T> Collection<T> convertIterableToCollection(final Iterable<T> iterable) {
    final Collection<T> collection = new ArrayList<>();
    for (final T element : iterable) {
      collection.add(element);
    }
    return collection;
  }

  @Test
  public void testCommandPlugin() throws Exception {
    final FunctionDefinitionRegistry registry =
        FunctionDefinitionRegistry.getFunctionDefinitionRegistry();
    final Collection<FunctionDefinition> functions = convertIterableToCollection(registry);
    assertThat(functions, hasSize(7));
    assertThat(
        functions,
        containsInAnyOrder(
            new DatabaseObjectListFunctionDefinition(),
            new TableDecriptionFunctionDefinition(),
            new TableReferencesFunctionDefinition(),
            new DatabaseObjectDescriptionFunctionDefinition(),
            new LintFunctionDefinition(),
            new ExitFunctionDefinition(),
            new SchemaFunctionDefinition()));
  }
}
