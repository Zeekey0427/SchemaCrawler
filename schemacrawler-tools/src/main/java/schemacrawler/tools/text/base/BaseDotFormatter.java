/*
 * SchemaCrawler
 * http://sourceforge.net/projects/schemacrawler
 * Copyright (c) 2000-2014, Sualeh Fatehi.
 * This library is free software; you can redistribute it and/or modify it under
 * the terms
 * of the GNU Lesser General Public License as published by the Free Software
 * Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package schemacrawler.tools.text.base;


import static sf.util.Utility.NEWLINE;
import static sf.util.Utility.readResourceFully;

import java.util.logging.Logger;

import schemacrawler.schema.DatabaseInfo;
import schemacrawler.schema.JdbcDriverInfo;
import schemacrawler.schema.SchemaCrawlerInfo;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.tools.options.OutputOptions;

/**
 * Text formatting of schema.
 * 
 * @author Sualeh Fatehi
 */
public abstract class BaseDotFormatter<O extends BaseTextOptions>
  extends BaseFormatter<O>
{

  protected static final Logger LOGGER = Logger
    .getLogger(BaseDotFormatter.class.getName());

  protected BaseDotFormatter(final O options,
                             final boolean printVerboseDatabaseInfo,
                             final OutputOptions outputOptions)
    throws SchemaCrawlerException
  {
    super(options, printVerboseDatabaseInfo, outputOptions);
  }

  @Override
  public void begin()
  {
    final String text = readResourceFully("/dot.header.txt");
    out.println(text);
  }

  @Override
  public void end()
  {
    out.println("}");
    out.flush();
    //
    out.close();
  }

  @Override
  public void handle(final DatabaseInfo dbInfo)
  {
    if (options.isNoInfo() || dbInfo == null)
    {
      return;
    }

    out.append("        <tr>").append(NEWLINE);
    out.append("          <td align=\"right\">Database:</td>").append(NEWLINE);
    out.append("          <td align=\"left\">").append(dbInfo.getProductName())
      .append("  ").append(dbInfo.getProductVersion()).append("</td>")
      .append(NEWLINE);
    out.append("        </tr>").append(NEWLINE);

  }

  @Override
  public void handle(final JdbcDriverInfo driverInfo)
  {
    if (options.isNoInfo() || driverInfo == null)
    {
      return;
    }

    out.append("        <tr>").append(NEWLINE);
    out.append("          <td align=\"right\">JDBC Connection:</td>")
      .append(NEWLINE);
    out.append("          <td align=\"left\">")
      .append(driverInfo.getConnectionUrl()).append("</td>").append(NEWLINE);
    out.append("        </tr>").append(NEWLINE);

    out.append("        <tr>").append(NEWLINE);
    out.append("          <td align=\"right\">JDBC Driver:</td>")
      .append(NEWLINE);
    out.append("          <td align=\"left\">")
      .append(driverInfo.getDriverName()).append("  ")
      .append(driverInfo.getDriverVersion()).append("</td>").append(NEWLINE);
    out.append("        </tr>").append(NEWLINE);

  }

  @Override
  public void handle(final SchemaCrawlerInfo schemaCrawlerInfo)
  {
    if (options.isNoInfo() || schemaCrawlerInfo == null)
    {
      return;
    }

    out.append("        <tr>").append(NEWLINE);
    out.append("          <td align=\"right\">Generated by:</td>")
      .append(NEWLINE);
    out.append("          <td align=\"left\">")
      .append(schemaCrawlerInfo.getSchemaCrawlerProductName()).append(" ")
      .append(schemaCrawlerInfo.getSchemaCrawlerVersion()).append("</td>")
      .append(NEWLINE);
    out.append("        </tr>").append(NEWLINE);
  }

  @Override
  public void handleInfoEnd()
    throws SchemaCrawlerException
  {
    if (options.isNoInfo())
    {
      return;
    }

    out.append("      </table>    >").append(NEWLINE).append("    labeljust=r")
      .append(NEWLINE).append("    labelloc=b").append(NEWLINE).append("  ];")
      .append(NEWLINE).append(NEWLINE);
  }

  @Override
  public void handleInfoStart()
    throws SchemaCrawlerException
  {
    if (options.isNoInfo())
    {
      return;
    }
    out
      .append("  graph [fontcolor=\"#555555\", ")
      .append(NEWLINE)
      .append("    label=<")
      .append(NEWLINE)
      .append("<table color=\"#555555\" border=\"1\" cellborder=\"0\" cellspacing=\"0\">")
      .append(NEWLINE);
  }
}
