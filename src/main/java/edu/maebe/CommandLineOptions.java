package edu.maebe;

import com.beust.jcommander.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Optional;
import java.util.Properties;


class CommandLineOptions {

    @Parameter(names = "--debug")
    boolean debug = false;

    @Parameter(names = {"--service-port"})
    Integer servicePort = 5050;

    @Parameter(names = {"--database"})
    String database = "maebe";

    @Parameter(names = {"--db-host"})
    String dbHost = "localhost";

    @Parameter(names = {"--db-username"})
    String dbUsername = "maebe";

    @Parameter(names = {"--db-password"})
    String dbPassword = "password";

    @Parameter(names = {"--db-port"})
    Integer dbPort = 5432;


}