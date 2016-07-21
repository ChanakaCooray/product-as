/*
 * Copyright 2011-2012 WSO2, Inc. (http://wso2.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.wso2.appserver.sample.carbondatasource.servlet;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Lookup data sources and write data source information to output
 */
public class CarbonDSLookupServlet extends HttpServlet {

    private static final Log log = LogFactory.getLog(CarbonDSLookupServlet.class);

    private static final long serialVersionUID = -8063538898432820125L;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) {

        Context initCtx;
        try {

            Hashtable environment = new Hashtable();
            environment.put("java.naming.factory.initial",
                            "org.wso2.carbon.tomcat.jndi.CarbonJavaURLContextFactory");
            initCtx = new InitialContext(environment);
            DataSource carbonDataSource = (DataSource) initCtx.lookup("jdbc/WSO2CarbonDB");

            PrintWriter writer;

            writer = response.getWriter();
            writer.println("<TABLE BORDER=1>");
            writer.println("<TR>");
            writer.println("<TH COLSPAN=\"2\">Data Source Info</TH>");
            writer.println("</TR>");
            writer.println("<TR>");
            writer.println("<TD>Data Source Name</TD><TD>" + carbonDataSource.getName() + "</TD>");
            writer.println("</TR>");
            writer.println("<TR>");
            writer.println("<TD>Driver Class Name</TD><TD>" + carbonDataSource.getDriverClassName() + "</TD>");
            writer.println("</TR>");
            writer.println("<TR>");
            writer.println("<TD>MaxActive value</TD><TD>" + carbonDataSource.getMaxActive() + "</TD>");
            writer.println("</TR>");
            writer.println("<TR>");
            writer.println("<TD>URL</TD><TD>" + carbonDataSource.getUrl() + "</TD>");
            writer.println("</TR>");
            writer.println("</TABLE>");

        } catch (NamingException e) {
            log.error("Error occurred while looking up database", e);
        } catch (IOException e) {
            log.error("Error occurred while writing to output", e);
        }
    }
}
