/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2019, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/
package schemacrawler.test.commandline.parser;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import schemacrawler.schemacrawler.Config;
import schemacrawler.tools.commandline.command.ConnectionOptions;
import schemacrawler.tools.commandline.command.DatabaseConnectable;
import schemacrawler.tools.databaseconnector.DatabaseConnectionSource;

public class ConnectionOptionsTest
{

  @Test
  public void noArgs()
  {
    final String[] args = new String[0];

    final ConnectionOptions optionsParser = new ConnectionOptions();
    new CommandLine(optionsParser).parse(args);
    assertThrows(CommandLine.ParameterException.class,
                 () -> optionsParser.getDatabaseConnectable());
  }

  @Test
  public void noValidArgs()
  {
    final String[] args = { "--some-option" };

    final ConnectionOptions optionsParser = new ConnectionOptions();
    new CommandLine(optionsParser).parse(args);
    assertThrows(CommandLine.ParameterException.class,
                 () -> optionsParser.getDatabaseConnectable());
  }

  @Test
  public void ConnectionOptionsNoValue()
  {
    final String[] args = { "--url" };

    final ConnectionOptions optionsParser = new ConnectionOptions();
    assertThrows(CommandLine.ParameterException.class,
                 () -> new CommandLine(optionsParser).parse(args));
  }

  @Test
  public void blankConnectionOptions()
  {
    final String[] args = {
      "--url", " " };

    final ConnectionOptions optionsParser = new ConnectionOptions();
    new CommandLine(optionsParser).parse(args);
    assertThrows(IllegalArgumentException.class,
                 () -> optionsParser.getDatabaseConnectable()
                   .toDatabaseConnectionSource(new Config()));
  }

  @Test
  public void url()
  {
    final String[] args = {
      "--url", "jdbc:database_url", "additional", "--extra" };

    final ConnectionOptions optionsParser = new ConnectionOptions();
    new CommandLine(optionsParser).parse(args);

    final DatabaseConnectable databaseConnectable = optionsParser
      .getDatabaseConnectable();
    final DatabaseConnectionSource databaseConnectionSource = databaseConnectable
      .toDatabaseConnectionSource(new Config());

    assertThat(databaseConnectionSource.toString().replaceAll("\r", ""),
               is("driver=<unknown>\nurl=jdbc:database_url\n"));

    final String[] remainder = optionsParser.getRemainder();
    assertThat(remainder, is(new String[] {
      "additional", "--extra" }));
  }

  @Test
  public void hostPort()
  {
    final String[] args = {
      "--server",
      "newdb",
      "--host",
      "somehost",
      "--port",
      "1234",
      "--database",
      "adatabase",
      "additional",
      "--extra" };

    final Config config = new Config();
    config.put("url", "jdbc:newdb://${host}:${port}/${database}");

    final ConnectionOptions optionsParser = new ConnectionOptions();
    new CommandLine(optionsParser).parse(args);

    final DatabaseConnectable databaseConnectable = optionsParser
      .getDatabaseConnectable();
    final DatabaseConnectionSource databaseConnectionSource = databaseConnectable
      .toDatabaseConnectionSource(config);

    assertThat(databaseConnectionSource.toString().replaceAll("\r", ""),
               is("driver=<unknown>\nurl=jdbc:newdb://somehost:1234/adatabase\n"));

    final String[] remainder = optionsParser.getRemainder();
    assertThat(remainder, is(new String[] {
      "additional", "--extra" }));
  }

  @Test
  public void allArgs()
  {
    final String[] args = {
      "--url",
      "jdbc:newdb://somehost:1234/adatabase",
      "--server",
      "newdb",
      "--host",
      "somehost",
      "--port",
      "1234",
      "--database",
      "adatabase",
      "additional",
      "--extra" };

    final ConnectionOptions optionsParser = new ConnectionOptions();

    assertThrows(CommandLine.MutuallyExclusiveArgsException.class,
                 () -> new CommandLine(optionsParser).parse(args));
  }

}
